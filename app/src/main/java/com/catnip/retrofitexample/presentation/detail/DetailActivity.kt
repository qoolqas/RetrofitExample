package com.catnip.retrofitexample.presentation.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import coil.load
import com.catnip.retrofitexample.R
import com.catnip.retrofitexample.databinding.ActivityDetailBinding
import com.catnip.retrofitexample.model.ProductItemUiModel
import com.catnip.retrofitexample.presentation.base.BaseViewModelActivity
import com.catnip.retrofitexample.util.toUsCurrency

class DetailActivity : BaseViewModelActivity<DetailViewModel, ActivityDetailBinding>() {
    companion object {
        private const val EXTRA_ITEM = "EXTRA_ITEM"
        fun navigate(context: Context, productItem: ProductItemUiModel) = with(context) {
            startActivity(
                Intent(
                    this,
                    DetailActivity::class.java
                ).putExtra(EXTRA_ITEM, productItem)
            )
        }
    }

    private val parcelableItem: ProductItemUiModel by lazy {
        intent.getParcelableExtra<ProductItemUiModel>(EXTRA_ITEM)
            ?: throw IllegalStateException("Parcelable extra not found or null")
    }
    override val viewModel: DetailViewModel by viewModels()

    override fun setupObservers() {

    }

    override val bindingInflater: (LayoutInflater) -> ActivityDetailBinding
        get() = ActivityDetailBinding::inflate

    override fun setupViews(): Unit = with(binding) {
        ivImageDetail.load(parcelableItem.images[0]) {
            crossfade(true)
            error(R.drawable.img_error)
        }
        tvTitleDetail.text = parcelableItem.title
        tvDescriptionDetail.text = parcelableItem.desc
        tvPriceDetail.text = parcelableItem.price.toUsCurrency()
        ivBackDetail.setOnClickListener { finish() }
    }
}