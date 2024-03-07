package com.example.wheather.presentation.model

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class XAxisFormatter(
    @Volatile var referenceValue: Int = 0,
    var xAxisFormat: String
) : ValueFormatter() {

    private val mFormat = SimpleDateFormat(xAxisFormat, Locale.ENGLISH)

    override fun getFormattedValue(value: Float): String? {
        val millis = (value.toLong() + referenceValue) * 1000L
        return mFormat.format(Date(millis))
    }

}
