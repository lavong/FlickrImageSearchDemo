package com.ingloriousmind.android.flickrimagesearchdemo.photowall

import com.ingloriousmind.android.flickrimagesearchdemo.photowall.flickr.model.Photo
import com.ingloriousmind.android.flickrimagesearchdemo.photowall.model.PhotoItem
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * dummy tests
 */
class PhotowallPresenterTest {

    private val photo = Photo(id = "id", title = "title", secret = "secret", server = "server", farmId = 1, isPublic = 1, owner = "owner", isFamily = 1, isFriend = 1)

    /**
     * unfailable
     */
    @Test
    fun shouldAssertTautology() = assertTrue(23 < 42)

    /**
     * you know...
     */
    @Test
    fun shouldConvertPhotoItem() {
        val presenter = PhotowallPresenter()

        val photoItem = presenter.toPhotoItem(photo)

        assertNotNull(photoItem)
    }

    /**
     * why?!
     */
    @Test
    fun shouldHandlePhotoItemClick() {
        PhotowallPresenter().onPhotoItemClicked(PhotoItem(imageUrlLarge = "L", imageUrlThumbnail = "T", photo = photo))

        assertNotNull("this is not null, is it?")
    }
}