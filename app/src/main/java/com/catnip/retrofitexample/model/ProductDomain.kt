package com.catnip.retrofitexample.model


data class ProductsDomain(
    val products: List<ProductItemDomain>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class ProductItemDomain(
    val id: Long,
    val title: String,
    val desc: String,
    val price: Long,
    val images: List<String>
)

