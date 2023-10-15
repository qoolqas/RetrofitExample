package com.catnip.retrofitexample.presentation.main

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.catnip.retrofitexample.R
import com.catnip.retrofitexample.data.network.api.ProductService
import com.catnip.retrofitexample.data.network.datasource.ProductDataSource
import com.catnip.retrofitexample.data.network.datasource.ProductDatabaseDataSource
import com.catnip.retrofitexample.data.network.repository.ProductRepository
import com.catnip.retrofitexample.data.network.repository.ProductRepositoryImpl
import com.catnip.retrofitexample.databinding.ActivityMainBinding
import com.catnip.retrofitexample.model.ProductItemUiModel
import com.catnip.retrofitexample.presentation.base.BaseViewModelActivity
import com.catnip.retrofitexample.presentation.detail.DetailActivity
import com.catnip.retrofitexample.util.GenericViewModelFactory
import com.catnip.retrofitexample.util.proceedWhen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : BaseViewModelActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels {
        val productService = ProductService.invoke()
        val productDataSource: ProductDataSource = ProductDatabaseDataSource(productService)
        val productRepository: ProductRepository = ProductRepositoryImpl(productDataSource)
        GenericViewModelFactory.create(MainViewModel(productRepository))
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val adapter: HomeListAdapter by lazy {
        HomeListAdapter {
            navigateToDetail(it)
        }
    }


    override fun setupViews() {
        viewModel.getProducts()
    }

    override fun setupObservers(): Unit = with(binding) {
        lifecycleScope.launch {
            viewModel.resultProduct.collectLatest {result ->
                result?.proceedWhen(
                    doOnSuccess = {
                        rvCart.isVisible = true
                        cartState.root.isVisible = false
                        cartState.pbLoading.isVisible = false
                        cartState.tvError.isVisible = false
                        val span = 2
                        rvCart.layoutManager = GridLayoutManager(this@MainActivity, span)
                        rvCart.adapter = adapter
                        result.payload?.products?.let { data -> adapter.submitData(data) }
                    },
                    doOnError = { err ->
                        cartState.root.isVisible = true
                        cartState.tvError.isVisible = true
                        cartState.tvError.text = err.exception?.message.orEmpty()
                        cartState.pbLoading.isVisible = false
                    },
                    doOnLoading = {
                        cartState.root.isVisible = true
                        cartState.tvError.isVisible = false
                        cartState.pbLoading.isVisible = true
                        rvCart.isVisible = false
                    },
                    doOnEmpty = {
                        cartState.root.isVisible = true
                        cartState.tvError.isVisible = true
                        cartState.tvError.text = getString(R.string.label_empty_state)
                        cartState.pbLoading.isVisible = false
                    }

                )
                if(result?.payload?.products?.isEmpty() == true){
                    cartState.root.isVisible = true
                    cartState.tvError.isVisible = true
                    cartState.tvError.text = getString(R.string.label_empty_state)
                    cartState.pbLoading.isVisible = false
                }
            }
        }

    }
    private fun navigateToDetail(productItemUiModel: ProductItemUiModel) {
        DetailActivity.navigate(this, productItemUiModel)
    }
}