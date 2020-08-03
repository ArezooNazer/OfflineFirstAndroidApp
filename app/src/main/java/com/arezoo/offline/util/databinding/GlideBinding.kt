package com.arezoo.offline.util.databinding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.arezoo.offline.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


private const val TAG = "GlideBinding"

class GlideBinding {
    companion object {
        @JvmStatic
        @BindingAdapter("app:glide_circle")
        fun setThumbnailResource(view: ImageView, imageUrl: String?) {
            Log.d(TAG, "setImageResource()  imageUrl = $imageUrl")
            imageUrl?.let {
                Glide.with(view.context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .placeholder(R.drawable.shape_circle_primary_background)
                    .into(view)
            }
        }
    }
}