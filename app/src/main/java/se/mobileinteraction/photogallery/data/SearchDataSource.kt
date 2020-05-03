package se.mobileinteraction.photogallery.data

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.paging.PageKeyedDataSource
import io.reactivex.rxkotlin.subscribeBy
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.utils.applyNetworkSchedulers
import se.mobileinteraction.photogallery.utils.koin

class SearchDataSource(val context: Context, val criteria: String) :
    PageKeyedDataSource<Int, UnsplashPhoto>() {

    val repo: PhotosRepository
        get() = context.koin().get()

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UnsplashPhoto>
    ) {
        repo.searchPhotos(criteria, 1).applyNetworkSchedulers()
            .subscribeBy(
                onSuccess = {
                    callback.onResult(it.results, null, 2)
                },
                onError = {
                    it.printStackTrace()
                })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, UnsplashPhoto>
    ) {

        repo.searchPhotos(criteria, params.key).applyNetworkSchedulers()
            .subscribeBy(
                onSuccess = {
                    callback.onResult(it.results, (params.key + 1))
                },
                onError = {
                    it.printStackTrace()
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UnsplashPhoto>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
