package com.ingloriousmind.android.flickrimagesearchdemo.photowall

import com.ingloriousmind.android.flickrimagesearchdemo.photowall.model.PhotoItem
import com.ingloriousmind.android.flickrimagesearchdemo.presentation.BaseContract

interface PhotowallContract {

    interface View : BaseContract.View {
        fun showPhotoItems(photoItems: List<PhotoItem>)
        fun updateSearchQuery(query: String)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onPhotoItemClicked(photoItem: PhotoItem)
        fun onSearchQuery(query: String)
        fun onScrolledToEnd(currentIndex: Int)
    }

}