package com.arezoo.offline.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewPaginationScrollListener(private val recyclerView: RecyclerView) :
    RecyclerView.OnScrollListener() {

    private val layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager
    private val LINEAR = 1
    private val GRID = 2

    private val layoutManagerType: Int by lazy { setLayoutManagerType() }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            if (layoutManager == null) return
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val firstVisibleItemPosition = getFirstItemPosition()

            val loading = isLoading
            val lastPage = isLastPage

            if (!loading && !lastPage) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                    loadMoreItems()
                }
            }
        }
    }

    private fun setLayoutManagerType(): Int {
        return when (layoutManager) {
            is GridLayoutManager -> GRID
            is LinearLayoutManager -> LINEAR
            else -> 0
        }
    }

    private fun getFirstItemPosition(): Int {
        return when (layoutManagerType) {
            GRID -> (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            LINEAR -> (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            else -> 0
        }
    }

    protected abstract fun loadMoreItems()
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean
}