package com.example.core.common

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.loadImageFromUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

