package com.crypto.inzynierka

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OverlayLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        for (i in itemCount - 1 downTo 0) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            layoutDecoratedWithMargins(
                view,
                0,
                0,
                getDecoratedMeasuredWidth(view),
                getDecoratedMeasuredHeight(view)
            )
        }
    }

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }
}
