package com.example.wheather.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wheather.presentation.databinding.ItemCityBinding
import com.example.wheather.presentation.model.FavoriteCityUiModel
import java.util.concurrent.Executors

class CityFavoriteAdapter :
    ListAdapter<FavoriteCityUiModel, CityFavoriteAdapter.CityFavoriteViewHolder>(
        AsyncDifferConfig.Builder(diffCallBack)
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
    ) {
    var onItemClick: ((FavoriteCityUiModel?) -> Unit)? = null

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<FavoriteCityUiModel>() {
            override fun areItemsTheSame(
                oldItem: FavoriteCityUiModel,
                newItem: FavoriteCityUiModel
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: FavoriteCityUiModel,
                newItem: FavoriteCityUiModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityFavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        return CityFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityFavoriteViewHolder, position: Int) {
        holder.bind(currentList.getOrNull(position))
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentList.getOrNull(position))
        }
    }

    class CityFavoriteViewHolder(private val itemCityBinding: ItemCityBinding) :
        RecyclerView.ViewHolder(itemCityBinding.root) {
        fun bind(item: FavoriteCityUiModel?) {
            itemCityBinding.tvCityName.text = item?.name.orEmpty()
        }
    }
}