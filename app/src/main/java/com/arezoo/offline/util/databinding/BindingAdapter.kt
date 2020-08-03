package com.arezoo.offline.util.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.presentation.photo.adapter.PhotoItemAdapter

class BindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("app:photo_adapter")
        fun setPhotoAdapter(recyclerView: RecyclerView, items: List<Photo>?) {
            (recyclerView.adapter as PhotoItemAdapter?)?.swapData(items)
        }
    }
}