package com.example.core.presentation

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout

abstract class BasePopupWindow(
    private val context: Context,
    private val viewAnchor: View
) : PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT) {

    abstract var backgroundView: Drawable?

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun initView(view: View)

    var minusLeftPosition: Int? = null
    private var rootView: View? = null
    open var widthEqualAnchorView: Boolean = false

    init {
        isOutsideTouchable = true
        isFocusable = false
    }

    open var gravity: Int = Gravity.TOP or Gravity.LEFT
    fun showPopup() {
        setUpView()
        if (!isShowing) {
            viewAnchor.doOnLayout {
                val location = viewAnchor.locateView() ?: return@doOnLayout
                contentView.measure(0, 0)
                showAtLocation(
                    viewAnchor,
                    gravity,
                    location.left,
                    location.bottom + 5
                )
                if (widthEqualAnchorView) {
                    showAtLocation(
                        viewAnchor,
                        gravity,
                        location.left,
                        location.bottom + 5
                    )
                } else {
                    val translateX = (contentView.measuredWidth - viewAnchor.width) / 2
                    showAtLocation(
                        viewAnchor,
                        gravity,
                        location.left - translateX + (minusLeftPosition ?: 0),
                        location.bottom + 5
                    )
                }
                dimBehind()
            }
        }
    }

    private fun setUpView() {
        if (rootView == null) {
            rootView = LayoutInflater.from(context).inflate(layoutId, null) ?: return
            contentView = rootView
            this.setBackgroundDrawable(backgroundView)
            if (widthEqualAnchorView) {
                super.setWidth(viewAnchor.width)
            }
            initView(rootView!!)
        }
    }

}

fun PopupWindow.dimBehind() {
    val container = contentView.rootView
    val context = contentView.context
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val p = container.layoutParams as WindowManager.LayoutParams
    p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
    p.dimAmount = 0.3f
    wm.updateViewLayout(container, p)
}

