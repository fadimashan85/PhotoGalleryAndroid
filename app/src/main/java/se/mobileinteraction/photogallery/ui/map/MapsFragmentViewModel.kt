package se.mobileinteraction.photogallery.ui.map


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxkotlin.subscribeBy
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.SearchResultData
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.entities.UnsplashUser


class MapsFragmentViewModel(val repo: PhotosRepository) : ViewModel() {

    private val _searchLocation: MutableLiveData<UnsplashUser> = MutableLiveData()
    val searchLocation: LiveData<UnsplashUser> = _searchLocation

    private val _searchPhoto: MutableLiveData<SearchResultData> = MutableLiveData()
    val searchPhoto: LiveData<SearchResultData> = _searchPhoto

    fun searchLocation(location: String) {
        repo.getPublicProfile(location).subscribeBy(
            onSuccess = { _searchLocation.postValue(it) },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun searchUnsplash(query: String) {
        repo.searchPhotos(query, 1)
            .subscribeBy(
                onSuccess = { _searchPhoto.postValue(it) },
                onError = {
                    it.printStackTrace()
                })
    }

    fun savePhotoAsFav(photo: UnsplashPhoto) {
        repo.saveAsFav(photo)
    }

    fun deletePhotoFromFav(photo: UnsplashPhoto) {
        repo.deleteFav(photo)
    }

    fun isFavourited(id: String) = repo.isFav(id)
}


