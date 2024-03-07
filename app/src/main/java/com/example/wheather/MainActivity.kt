package com.example.wheather

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import com.example.wheather.presentation.WeatherFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.rootFragmentContainer, WeatherFragment(), WeatherFragment::class.java.name)
            .commit()
    }
    /**
     * Hide Keyboard if has when click outside
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val viewFocus = currentFocus
            if (viewFocus is EditText) {
                val outRect = Rect()
                viewFocus.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    showSoftKeyboard(false)
                    viewFocus.apply {
                        isFocusableInTouchMode = false
                        clearFocus()
                        isFocusableInTouchMode = true
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


    fun Context.showSoftKeyboard(show: Boolean) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        when (show) {
            true -> imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            else -> when (this) {
                is Activity -> imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
                is Fragment -> imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
            }
        }
    }
}