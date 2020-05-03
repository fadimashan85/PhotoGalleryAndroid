package se.mobileinteraction.photogallery.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import se.mobileinteraction.photogallery.entities.UnsplashPhoto


class PhotoDataFactory(val context: Context) : DataSource.Factory<Int, UnsplashPhoto>() {

    private val _photosDataSource: MutableLiveData<PhotosDataSource> = MutableLiveData()


    private lateinit var photoDataSource: PhotosDataSource

    override fun create(): DataSource<Int, UnsplashPhoto> {
        photoDataSource = PhotosDataSource(context)
        _photosDataSource.postValue(photoDataSource)
        return photoDataSource
    }
}