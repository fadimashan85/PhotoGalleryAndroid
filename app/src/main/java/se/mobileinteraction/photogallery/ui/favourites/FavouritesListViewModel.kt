package se.mobileinteraction.photogallery.ui.favourites

import androidx.lifecycle.ViewModel
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.UnsplashPhoto

class FavouritesListViewModel(val repo: PhotosRepository) : ViewModel() {

    fun savePhotoAsFav(photo: UnsplashPhoto) {
        repo.saveAsFav(photo)
    }

    fun deletePhotoFromFav(photo: UnsplashPhoto) {
        repo.deleteFav(photo)
    }

    fun readPhotoListCache() =
        repo.readFav()

    fun isFavourited(id: String) = repo.isFav(id)

    fun clearAll() = repo.clearAll()
}
