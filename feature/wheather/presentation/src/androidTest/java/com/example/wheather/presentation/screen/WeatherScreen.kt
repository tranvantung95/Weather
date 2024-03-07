package com.example.wheather.presentation.screen

import com.example.wheather.presentation.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.dialog.KAlertDialog
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KTextView

object WeatherScreen : KScreen<WeatherScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val llRoot = KView {
        withId(R.id.llContent)
    }

    val edtCity = KEditText {
        withId(R.id.edtCity)
    }
    val tvTemperature = KTextView {
        withId(R.id.tvCurrentTemperature)
    }
    val tvCityName = KTextView {
        withId(R.id.tvCityName)
    }
    val tvHumidity = KTextView {
        withId(R.id.tvHumidity)
    }
    val tvWind = KTextView {
        withId(R.id.tvWind)
    }
    val ivFavorite = KImageView {
        withId(R.id.ivFavorite)
    }
    val chipGroup = KChipGroup {
        withId(R.id.chipGroup)
    }
    val dialog = KAlertDialog()
}