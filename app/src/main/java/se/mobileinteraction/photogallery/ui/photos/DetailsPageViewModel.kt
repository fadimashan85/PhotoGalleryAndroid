package se.mobileinteraction.photogallery.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxkotlin.subscribeBy
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.entities.UnsplashUser
import timber.log.Timber


class DetailsPageViewModel(val repo: PhotosRepository, unsplashPhoto: UnsplashPhoto) : ViewModel() {

    private val _publicProfile: MutableLiveData<UnsplashUser> = MutableLiveData()
    val publicProfile: LiveData<UnsplashUser> = _publicProfile

    private val _userPhotos: MutableLiveData<List<UnsplashPhoto>> = MutableLiveData()
    val userPhotos: LiveData<List<UnsplashPhoto>> = _userPhotos

    private val _photo: MutableLiveData<UnsplashPhoto> = MutableLiveData()
    val photo: LiveData<UnsplashPhoto> = _photo


    fun savePhotoAsFav(photo: UnsplashPhoto) {
        repo.saveAsFav(photo)
    }

    fun deletePhotoFromFav(photo: UnsplashPhoto) {
        repo.deleteFav(photo)
    }

    fun isFavourited(id: String) = repo.isFav(id)

    init {

        repo.getPublicProfile(unsplashPhoto.user!!.username)
            .subscribeBy(
                onSuccess = { _publicProfile.postValue(it) },
                onError = { Timber.e(it) }
            )

        repo.getUserPhotos(unsplashPhoto.user!!.username)
            .subscribeBy(
                onSuccess = { _userPhotos.postValue(it) },
                onError = { Timber.e(it) }
            )

        repo.getPhoto(unsplashPhoto.id)
            .subscribeBy(
                onSuccess = { _photo.postValue(it) },
                onError = { Timber.e(it) }
            )
    }
}
