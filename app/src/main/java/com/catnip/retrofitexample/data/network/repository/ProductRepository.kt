package com.catnip.retrofitexample.data.network.repository

import com.catnip.retrofitexample.data.network.datasource.ProductDataSource
import com.catnip.retrofitexample.model.ProductsDomain
import com.catnip.retrofitexample.util.ResultWrapper
import com.catnip.retrofitexample.util.proceedFlow
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    suspend fun getProducts(): Flow<ResultWrapper<ProductsDomain>>
}

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource
) : ProductRepository {

    override suspend fun getProducts(): Flow<ResultWrapper<ProductsDomain>> {
        return proceedFlow { productDataSource.getAllProducts().toDomain() }
    }

//    override suspend fun getProducts(): Flow<ResultWrapper<ProductsDomain>> {
//        return productDataSource.getAllProducts().map {
//            proceed { it.toDomain() }
//        }.onStart {
//            emit(ResultWrapper.Loading())
//            delay(1000)
//        }
//    }
}