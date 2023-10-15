package com.catnip.retrofitexample.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class ProductsUiModel(
    val products: List<ProductItemUiModel>,
    val total: Int,
    val skip: Int,
    val limit: Int
){
    companion object{
        fun parse(domain: ProductsDomain): ProductsUiModel {

            val products = domain.products.map {
                ProductItemUiModel.parse(it)
            }
            return ProductsUiModel(
                products,
                domain.total,
                domain.skip,
                domain.limit,
            )
        }
    }
    //For testing empty list
    fun getEmptyProductsUiModel(): ProductsUiModel {
        return ProductsUiModel(
            emptyList(), // Empty list of products
            0, // Total is 0
            0, // Skip is 0
            0 // Limit is 0
        )
    }
}



@Parcelize
data class ProductItemUiModel(
    val id: Long,
    val title: String,
    val desc: String,
    val price: Long,
    val images: List<String>
) : Parcelable {
    companion object{
        fun parse(domain: ProductItemDomain): ProductItemUiModel {
            return ProductItemUiModel(
                domain.id,
                domain.title,
                domain.desc,
                domain.price,
                domain.images,
            )
        }
    }
}

