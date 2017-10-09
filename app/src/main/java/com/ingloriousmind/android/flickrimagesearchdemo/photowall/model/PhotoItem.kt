package com.ingloriousmind.android.flickrimagesearchdemo.photowall.model

import com.ingloriousmind.android.flickrimagesearchdemo.photowall.flickr.model.Photo


data class PhotoItem(
        val label: String = "",
        val imageUrl: String,
        val photo: Photo
)