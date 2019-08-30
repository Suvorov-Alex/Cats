package com.test.testcatsapp.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.test.testcatsapp.R
import kotlinx.android.synthetic.main.placeholder_view.view.*

class PlaceholderView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(
    context,
    attrs,
    defStyleAttr
) {
    init {
        inflate(getContext(), R.layout.placeholder_view, this)

        context.withStyledAttributes(attrs, R.styleable.PlaceholderView, defStyleAttr, 0) {
            val title = getResourceId(R.styleable.PlaceholderView_placeholderTitle, 0)
            if (title != 0) {
                placeholderViewTitle.isVisible = true
                placeholderViewTitle.setText(title)
            } else {
                placeholderViewTitle.isVisible = false
            }
        }
    }

    fun setTitle(@StringRes title: Int) {
        if (title != 0) {
            placeholderViewTitle.setText(title)
            placeholderViewTitle.isVisible = true
        } else {
            placeholderViewTitle.isVisible = false
        }
    }
}