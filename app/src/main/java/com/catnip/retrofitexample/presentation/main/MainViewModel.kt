package com.catnip.retrofitexample.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.retrofitexample.data.network.repository.ProductRepository
import com.catnip.retrofitexample.model.ProductsUiModel
import com.catnip.retrofitexample.util.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MainViewModel(private val repo: ProductRepository) : ViewModel() {

    private val _resultProduct by lazy {
        MutableStateFlow<ResultWrapper<ProductsUiModel>?>(null)
    }
    val resultProduct: StateFlow<ResultWrapper<ProductsUiModel>?> = _resultProduct


    fun getProducts() {
        viewModelScope.launch {
            repo.getProducts().collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val productsDomain = result.payload

                        productsDomain?.let {
                            val productsUiModel = ProductsUiModel.parse(it)
                            _resultProduct.emit(ResultWrapper.Success(productsUiModel))
                        }
                    }

                    is ResultWrapper.Error -> {
                        _resultProduct.emit(ResultWrapper.Error(result.exception))
                    }

                    is ResultWrapper.Loading -> {
                        _resultProduct.emit(ResultWrapper.Loading())
                    }

                    is ResultWrapper.Empty -> {
                        _resultProduct.emit(ResultWrapper.Empty())
                    }
                }
            }

        }
//            repo.getProducts().map { it.payload?.let { payload -> ProductsUiModel.parse(payload) } }
//                .let {
//                    it.map { _resultProduct.emit(it) }
//                }
//    }
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = service.getProducts()
//                responseLiveData.postValue(response)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }
}