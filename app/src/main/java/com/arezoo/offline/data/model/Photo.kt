package com.arezoo.offline.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Photo(
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("albumId")
    var albumId: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null
)