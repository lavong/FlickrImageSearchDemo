package com.ingloriousmind.android.flickrimagesearchdemo.photowall.flickr

import com.ingloriousmind.android.flickrimagesearchdemo.BuildConfig
import com.ingloriousmind.android.flickrimagesearchdemo.photowall.flickr.model.PhotosResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrApi {

    @GET("services/rest/?method=flickr.photos.search&api_key=${BuildConfig.FLICKR_API_KEY}&format=json&nojsoncallback=1")
    fun fetchPhotos(
            @Query("text") query: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int
    ): Observable<PhotosResult>

}