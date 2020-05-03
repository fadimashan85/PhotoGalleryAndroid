package se.mobileinteraction.photogallery.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Single
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.data.api.UnsplashApi
import se.mobileinteraction.photogallery.entities.SearchResultData
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.entities.UnsplashUser
import se.mobileinteraction.photogallery.utils.applyNetworkSchedulers
import timber.log.Timber
import java.lang.reflect.Type


class PhotosRepository(
    val application: Context,
    private val api: UnsplashApi,
    private val sharedPref: SharedPreferences,
    private val moshi: Moshi
) {
    private val apiKey = application.getString(R.string.access_key)
    private val currentFavList = mutableListOf<String>()

    init {
        currentFavList.addAll(readFavPhotoListCache().map { it.id })
    }

    fun getPhotos(index: Int): Single<List<UnsplashPhoto>> {
        return api.getPhotos(apiKey, page = index)
            .applyNetworkSchedulers()
            .doOnSubscribe {
                if (index == 1) readPhotoListCache()
            }.doOnSuccess {
                if (index == 1) cachePhotoList(it)
            }
    }

    fun searchPhotos(string: String, index: Int): Single<SearchResultData> {
        return api.searchPhotos(apiKey, criteria = string, page = index)
            .applyNetworkSchedulers()

    }


    fun getPublicProfile(string: String): Single<UnsplashUser> {
        return api.getPublicProfile(username = string, accessKey = apiKey)
            .applyNetworkSchedulers()
    }

    fun getUserPhotos(string: String): Single<List<UnsplashPhoto>> {
        return api.getUserPhotos(username = string, accessKey = apiKey)
            .applyNetworkSchedulers()
    }

    fun getPhoto(string: String): Single<UnsplashPhoto> {
        return api.getPhoto(id = string, accessKey = apiKey)
            .applyNetworkSchedulers()
    }

    private fun cachePhotoList(list: List<UnsplashPhoto>) {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            UnsplashPhoto::class.java
        )
        val adapter: JsonAdapter<List<UnsplashPhoto>> = moshi.adapter(listMyData)
        val json = adapter.toJson(list)

        sharedPref.edit().putString("json_test", json).apply()
    }

    private fun readPhotoListCache(): List<UnsplashPhoto> {
        val data = sharedPref.getString("json_test", "")
        if (data.isNullOrEmpty()) return emptyList()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            UnsplashPhoto::class.java
        )
        val adapter: JsonAdapter<List<UnsplashPhoto>> = moshi.adapter(listMyData)
        val json = adapter.fromJson(data)
        return json ?: emptyList()

    }

    private fun readFavPhotoListCache(): MutableList<UnsplashPhoto> {
        val data = sharedPref.getString("favo_list", "")
        if (data.isNullOrEmpty()) return mutableListOf()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            UnsplashPhoto::class.java
        )
        val adapter: JsonAdapter<MutableList<UnsplashPhoto>> = moshi.adapter(listMyData)
        val json = adapter.fromJson(data)
        return json ?: mutableListOf()

    }

    private fun cacheFavPhotoList(list: List<UnsplashPhoto>) {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            UnsplashPhoto::class.java
        )
        val adapter: JsonAdapter<List<UnsplashPhoto>> = moshi.adapter(listMyData)
        val json = adapter.toJson(list)

        sharedPref.edit().putString("favo_list", json).apply()
        currentFavList.addAll(list.map { it.id })
    }

    fun isFav(id: String) = currentFavList.contains(id)

    fun saveAsFav(photo: UnsplashPhoto) {
        val list = readFavPhotoListCache()
        list.add(photo)
        cacheFavPhotoList(list)
        Timber.d("here we go! " + readFavPhotoListCache().size.toString())
    }

    fun deleteFav(photo: UnsplashPhoto) {
        val list = readFavPhotoListCache()
        list.remove(photo)
        cacheFavPhotoList(list)
    }

    fun clearAll(){
        val list= readFavPhotoListCache()
        list.clear()
        cacheFavPhotoList(list)
    }

    fun readFav(): List<UnsplashPhoto> {
        val data = sharedPref.getString("favo_list", "")
        if (data.isNullOrEmpty()) return emptyList()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            UnsplashPhoto::class.java
        )
        val adapter: JsonAdapter<List<UnsplashPhoto>> = moshi.adapter(listMyData)
        val json = adapter.fromJson(data)
        return json ?: emptyList()
    }
}
