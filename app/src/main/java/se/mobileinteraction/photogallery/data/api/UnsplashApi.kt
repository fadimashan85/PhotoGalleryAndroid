package se.mobileinteraction.photogallery.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import se.mobileinteraction.photogallery.entities.SearchResultData
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.entities.UnsplashUser


interface UnsplashApi {
    @Headers("Accept-Version: v1")

    @GET("photos")
    fun getPhotos(
        @Query("client_id") accessKey: String,
        @Query("page") page: Int = 2,
        @Query("per_page") limit: Int = 11,
        @Query("order_by") order: String = "popular"
    ): Single<List<UnsplashPhoto>>

    @GET("search/photos")
    fun searchPhotos(
        @Query("client_id") accessKey: String,
        @Query("query") criteria: String,
        @Query("page") page: Int = 2,
        @Query("per_page") pageSize: Int = 11
    ): Single<SearchResultData>

    @GET("users/{username}")
    fun getPublicProfile(
        @Path("username") username: String,
        @Query("client_id") accessKey: String
    ): Single<UnsplashUser>

    @GET("users/{username}/photos")
    fun getUserPhotos(
        @Path("username") username: String,
        @Query("client_id") accessKey: String,
        @Query("page") page: Int = 2,
        @Query("per_page") limit: Int = 9,
        @Query("order_by") order: String = "popular"
    ): Single<List<UnsplashPhoto>>

    @GET("photo/{id}")
    fun getPhoto(
        @Path("id") id: String,
        @Query("client_id") accessKey: String
    ): Single<UnsplashPhoto>

}
