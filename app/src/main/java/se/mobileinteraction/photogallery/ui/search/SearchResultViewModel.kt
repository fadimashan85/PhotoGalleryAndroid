package se.mobileinteraction.photogallery.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import se.mobileinteraction.photogallery.data.SearchDataFactory
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SearchResultViewModel(val repo: PhotosRepository, query: String) : ViewModel() {

    private var executors: ExecutorService = Executors.newFixedThreadPool(5)
    var searchData: LiveData<PagedList<UnsplashPhoto>>

    fun  savePhotoAsFav(photo: UnsplashPhoto) {
        repo.saveAsFav(photo)
    }

    fun deletePhotoFromFav(photo: UnsplashPhoto) {
        repo.deleteFav(photo)
    }

    fun isFavourited(id: String) = repo.isFav(id)

    init {
        val searchDataFactory = SearchDataFactory(repo.application, criteria = query)

        val pagedListConfig: PagedList.Config =
        PagedList
            .Config
            .Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setPrefetchDistance(2)
                .build()
        searchData = LivePagedListBuilder(searchDataFactory, pagedListConfig)
            .setFetchExecutor(executors)
            .build()
    }

}
