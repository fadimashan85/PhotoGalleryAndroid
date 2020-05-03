package se.mobileinteraction.photogallery.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import se.mobileinteraction.photogallery.data.PhotoDataFactory
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class PhotoListViewModel(val repo: PhotosRepository) : ViewModel() {

    private var executors: ExecutorService = Executors.newFixedThreadPool(5)
    var photosData: LiveData<PagedList<UnsplashPhoto>>


    fun savePhotoAsFav(photo: UnsplashPhoto) {
        repo.saveAsFav(photo)
    }

    fun deletePhotoFromFav(photo: UnsplashPhoto) {
        repo.deleteFav(photo)
    }

    init {
        val photoDataFactory = PhotoDataFactory(repo.application)

        val pagedListConfig: PagedList.Config =
            PagedList
                .Config
                .Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .setPrefetchDistance(2)
                .build()
        photosData = LivePagedListBuilder(photoDataFactory, pagedListConfig)
            .setFetchExecutor(executors)
            .build()
    }


    fun isFavourited(id: String) = repo.isFav(id)
}
