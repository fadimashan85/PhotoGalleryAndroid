package se.mobileinteraction.photogallery.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import se.mobileinteraction.photogallery.entities.UnsplashPhoto

class SearchDataFactory(val context: Context, val criteria: String) :
    DataSource.Factory<Int, UnsplashPhoto>() {

    private val _searchDataSource: MutableLiveData<SearchDataSource> = MutableLiveData()
    private lateinit var searchDataSource: SearchDataSource

    override fun create(): DataSource<Int, UnsplashPhoto> {
        searchDataSource = SearchDataSource(context, criteria)
        _searchDataSource.postValue(searchDataSource)
        return searchDataSource
    }
}
