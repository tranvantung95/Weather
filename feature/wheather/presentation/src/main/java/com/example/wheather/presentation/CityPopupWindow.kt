package com.example.wheather.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.core.presentation.BasePopupWindow
import com.example.wheather.presentation.model.FavoriteCityUiModel

class CityPopupWindow(
    context: Context,
    viewAnchor: View,
    private val favoriteCityUiModel: List<FavoriteCityUiModel>,
    private var favoriteCityClick: ((FavoriteCityUiModel?) -> Unit)? = null
) : BasePopupWindow(context, viewAnchor) {
    private val adapter: CityFavoriteAdapter by lazy {
        CityFavoriteAdapter()
    }
    override var backgroundView: Drawable?
        get() = null
        set(value) {}
    override val layoutId: Int
        get() = R.layout.city_popup_window

    var recyclerCity: RecyclerView? = null

    override fun initView(view: View) {
        setOnDismissListener {
            favoriteCityClick = null

        }
        recyclerCity = view.findViewById(R.id.rcCityName)
        recyclerCity?.adapter = adapter
        adapter.submitList(favoriteCityUiModel)
        adapter.onItemClick = {
            favoriteCityClick?.invoke(it)
            dismiss()
        }
    }
}