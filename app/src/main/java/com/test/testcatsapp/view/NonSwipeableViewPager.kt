package com.test.testcatsapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipeableViewPager
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(
    context,
    attrs
) {
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean = false

    override fun onTouchEvent(event: MotionEvent): Boolean = false
}