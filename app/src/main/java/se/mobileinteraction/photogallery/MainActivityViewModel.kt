package se.mobileinteraction.photogallery

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.UnsplashPhoto

class MainActivityViewModel (val repo :PhotosRepository): ViewModel() {

    private val _showSearch: MutableLiveData<Int> = MutableLiveData()
    val showSearchButton: LiveData<Int> = _showSearch

    private val _searchVisiblity: MutableLiveData<Int> = MutableLiveData()
    val searchVisiblity: LiveData<Int> = _searchVisiblity

    private val _navBottomBar: MutableLiveData<Int> = MutableLiveData()
    val navBottomBar: LiveData<Int> = _navBottomBar

    private val _markAsChecked: MutableLiveData<Boolean> = MutableLiveData()
    val markeAsChecked: LiveData<Boolean> = _markAsChecked


    fun shouldShowOpenSearchButton(current_id: Int) {
        _showSearch.postValue(if (current_id == R.id.searchFragment || current_id == R.id.searchResultFragment ||current_id == R.id.detailsPageFragment || current_id == R.id.mapsFragment) View.GONE else View.VISIBLE)
    }


    fun showSearchBar(current_id: Int) {
        _searchVisiblity.postValue(if (current_id == R.id.photosListFragment ) View.VISIBLE else View.GONE)
    }


    fun showNavBootomBar(current_id: Int) {
        _navBottomBar.postValue(if (current_id == R.id.photosListFragment  || current_id == R.id.aboutFragment || current_id == R.id.favouriteFragment) View.VISIBLE else View.GONE)
    }

    fun  savePhotoAsFav(photo: UnsplashPhoto) {
        repo.saveAsFav(photo)
    }

    fun deletePhotoFromFav(photo: UnsplashPhoto) {
        repo.deleteFav(photo)
    }

}
