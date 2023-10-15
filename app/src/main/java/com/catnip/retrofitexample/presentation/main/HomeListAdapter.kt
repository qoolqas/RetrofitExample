package com.catnip.retrofitexample.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.catnip.retrofitexample.R
import com.catnip.retrofitexample.databinding.ItemProductBinding
import com.catnip.retrofitexample.model.ProductItemUiModel
import com.catnip.retrofitexample.util.toUsCurrency

class HomeListAdapter(
    private val onItemClick: (ProductItemUiModel) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ProductItemUiModel>() {
        override fun areItemsTheSame(oldItem: ProductItemUiModel, newItem: ProductItemUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItemUiModel, newItem: ProductItemUiModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    fun submitData(data: List<ProductItemUiModel>) {
        differ.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return HomeGridViewHolder(
            binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is HomeGridViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}


class HomeGridViewHolder(
    private val binding: ItemProductBinding,
    private val onItemClick: (ProductItemUiModel) -> Unit
) : ViewHolder(binding.root) {
    fun bind(item: ProductItemUiModel) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuPicture.load(item.images[0]) {
            crossfade(true)
            error(R.drawable.img_error)
        }
        binding.tvMenuTitle.text = item.title
        binding.tvMenuPrice.text = item.price.toUsCurrency()
    }
}