package com.ingloriousmind.android.flickrimagesearchdemo.photowall.flickr.model

import com.google.gson.annotations.SerializedName

data class PhotosResult(
        @SerializedName("photos") var photos: Photos,
        @SerializedName("stat") var status: String
)

data class Photos(
        @SerializedName("page") var page: Int = 0,
        @SerializedName("pages") var pages: Int = 0,
        @SerializedName("perpage") var perPage: Int = 0,
        @SerializedName("total") var total: String = "",
        @SerializedName("photo") var photos: List<Photo> = emptyList()
)

data class Photo(
        @SerializedName("id") var id: String,
        @SerializedName("owner") var owner: String,
        @SerializedName("secret") var secret: String,
        @SerializedName("server") var server: String,
        @SerializedName("farm") var farmId: Int,
        @SerializedName("title") var title: String,
        @SerializedName("ispublic") var isPublic: Int,
        @SerializedName("isfriend") var isFriend: Int,
        @SerializedName("isfamily") var isFamily: Int
)