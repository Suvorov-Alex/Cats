package com.test.testcatsapp.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class AutoFitGridRecyclerView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(
    context,
    attrs,
    defStyleAttr
) {

    private var manager: GridLayoutManager? = null
    private var columnWidth = -1

    init {
        if (attrs != null) {
            val attrsArray = intArrayOf(android.R.attr.columnWidth)
            val array = context.obtainStyledAttributes(attrs, attrsArray)
            columnWidth = array.getDimensionPixelSize(0, -1)
            array.recycle()
        }

        manager = GridLayoutManager(context, 1)
        layoutManager = manager
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)

        if (columnWidth > 0) {
            manager?.spanCount = max(1, measuredWidth / columnWidth)
        }
    }
}
