package com.catnip.retrofitexample.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep //agar tidak terkena obfuscated
data class ProductsResponse(
    @SerializedName("products")
    val products: List<ProductItemResponse>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("limit")
    val limit: Int
){
    fun toDomain() = ProductsDomain(
        products = this.products.map { it.toDomain() },
        total = this.total,
        skip = this.skip,
        limit = this.limit,
    )
}

@Keep
data class ProductItemResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val desc: String,
    @SerializedName("price")
    val price: Long,
    @SerializedName("images")
    val images: List<String>
){
    fun toDomain() = ProductItemDomain(
        id = this.id,
        title = this.title,
        desc = this.desc,
        price = this.price,
        images = this.images,
    )
}

