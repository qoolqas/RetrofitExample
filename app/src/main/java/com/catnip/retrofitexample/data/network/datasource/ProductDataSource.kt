package com.catnip.retrofitexample.data.network.datasource

import com.catnip.retrofitexample.data.network.api.ProductService
import com.catnip.retrofitexample.model.ProductsResponse


interface ProductDataSource {
    suspend fun getAllProducts(): ProductsResponse
}

class ProductDatabaseDataSource(private val productService: ProductService) : ProductDataSource {
    override suspend fun getAllProducts(): ProductsResponse {
        return productService.getProducts()
    }

}