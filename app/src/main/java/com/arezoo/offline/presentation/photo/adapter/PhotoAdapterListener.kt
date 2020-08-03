package com.arezoo.offline.presentation.photo.adapter

import com.arezoo.offline.data.model.Photo

interface PhotoAdapterListener {
    fun onPhotoItemClick(photo: Photo)
}