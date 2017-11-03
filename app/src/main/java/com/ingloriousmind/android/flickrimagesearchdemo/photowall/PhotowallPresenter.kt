package com.ingloriousmind.android.flickrimagesearchdemo.photowall

import com.ingloriousmind.android.flickrimagesearchdemo.BuildConfig
import com.ingloriousmind.android.flickrimagesearchdemo.photowall.flickr.FlickrApi
import com.ingloriousmind.android.flickrimagesearchdemo.photowall.flickr.model.Photo
import com.ingloriousmind.android.flickrimagesearchdemo.photowall.model.PhotoItem
import com.ingloriousmind.android.flickrimagesearchdemo.presentation.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


class PhotowallPresenter : BasePresenter<PhotowallContract.View>(), PhotowallContract.Presenter {

    companion object FLICKR {
        const val IMAGE_URL_FORMAT_THUMBNAIL = "http://farm%s.static.flickr.com/%s/%s_%s_t.jpg" // http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
        const val IMAGE_URL_FORMAT_LARGE = "http://farm%s.static.flickr.com/%s/%s_%s_b.jpg" // https://www.flickr.com/services/api/flickr.photos.getSizes.html
        const val PAGE_SIZE = 100
        const val DEFAULT_QUERY = "kittens"
    }

    val flickrApi: FlickrApi
    var cachedPhotoItems: MutableList<PhotoItem> = mutableListOf()
    var flickrDisposable: Disposable? = null
    var currentPage = 1
    var currentQuery = ""

    init {
        flickrApi = Retrofit.Builder()
                .baseUrl(BuildConfig.FLICKR_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlickrApi::class.java)
    }

    override fun bindView(view: PhotowallContract.View) {
        super.bindView(view)
        loadPhotos(FLICKR.DEFAULT_QUERY, 1)
    }

    override fun unbindView() {
        flickrDisposable?.dispose()
        super.unbindView()
    }

    override fun onSearchQuery(query: String) {
        Timber.d("onSearchQuery: $query")
        if (query != currentQuery) {
            cachedPhotoItems.clear()
            loadPhotos(query, 1)
        }
    }

    override fun onPhotoItemClicked(photoItem: PhotoItem) {
        Timber.d("clicked: $photoItem")
        // TODO do something
    }

    override fun onScrolledToEnd(currentIndex: Int) {
        if (currentIndex / FLICKR.PAGE_SIZE == currentPage) {
            loadPhotos(currentQuery, ++currentPage)
        }
    }

    fun toPhotoItem(photo: Photo): PhotoItem {
        val label = photo.title
        val urlThumbnail = String.format(FLICKR.IMAGE_URL_FORMAT_THUMBNAIL, photo.farmId, photo.server, photo.id, photo.secret)
        val urlLarge = String.format(FLICKR.IMAGE_URL_FORMAT_LARGE, photo.farmId, photo.server, photo.id, photo.secret)
        return PhotoItem(label, urlThumbnail, urlLarge, photo)
    }

    fun loadPhotos(query: String, page: Int) {
        Timber.v("loading: query=$query, page=$page")
        currentQuery = query
        currentPage = page
        flickrDisposable = flickrApi.fetchPhotos(query, page, FLICKR.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .map { (result) -> result.photos.map { photo: Photo -> toPhotoItem(photo) } }
                .doOnNext { cachedPhotoItems.addAll(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    this@PhotowallPresenter.view?.let {
                        it.showPhotoItems(cachedPhotoItems)
                        it.updateSearchQuery(query)
                    }
                }
    }

}