package com.example.core.presentation

import android.graphics.Rect
import android.view.View

fun View.locateView(): Rect? {
    val locInt = IntArray(2)
    try {
        this.getLocationOnScreen(locInt)
    } catch (npe: NullPointerException) {
        return null
    }
    val location = Rect()
    location.left = locInt[0]
    location.top = locInt[1]
    location.right = location.left + this.width
    location.bottom = location.top + this.height
    return location
}