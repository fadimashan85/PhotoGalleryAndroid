package se.mobileinteraction.photogallery.entities


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashUser(
    val id: String,
    val updated_at: String,
    val username: String,
    val name: String,
    val portfolio_url: String,
    val bio: String,
    val location: String,
    val total_likes: Int,
    val total_photos: Int,
    val total_collections: Int,
    val profile_image: profile_image,
    val links: UnsplashLinks

) : Parcelable
