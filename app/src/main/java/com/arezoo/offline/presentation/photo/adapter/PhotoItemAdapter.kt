package com.arezoo.offline.presentation.photo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arezoo.offline.R
import com.arezoo.offline.data.model.Photo
import com.arezoo.offline.databinding.ItemPhotoBinding

private const val TAG = "PhotoItemAdapter"

class PhotoItemAdapter(
    private val listener: PhotoAdapterListener
) : RecyclerView.Adapter<PhotoItemAdapter.PhotoItemViewHolder>() {

    private var photos: ArrayList<Photo> = ArrayList()

    fun swapData(newItems: List<Photo>?) {
        Log.d(TAG, "swapData() called with: newLocationItems = $newItems")
        newItems?.apply {
            if (photos.takeLast(newItems.size) == newItems)
                return

            photos.addAll(newItems)
            notifyItemRangeChanged(photos.size, newItems.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        return PhotoItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_photo,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${photos.size}")
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun clearAdapterData() {
        photos.clear()
        notifyDataSetChanged()
    }

    inner class PhotoItemViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(index: Int) {
            binding.apply {
                Log.d(TAG, "onBind() called ${photos[index]}")
                photo = photos[index]
                itemPhotoContainer.setOnClickListener {
                    listener.onPhotoItemClick(
                        photos[index]
                    )
                }
            }
        }
    }
}