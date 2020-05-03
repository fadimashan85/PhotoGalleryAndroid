package se.mobileinteraction.photogallery.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashPhoto(

    val id: String = "1",
    val created_at: String = "1",
    val updated_at: String= "1",
    val width: Int= 1,
    val height: Int=1,
    val color: String? = "#000000",
    val downloads: Int=1,
    val likes: Int=1,
    val liked_by_user: Boolean = false,
    val description: String= "1",
    val urls: UnsplashUrls? = null,
    val links: UnsplashLinks? = null,
    val user: UnsplashUser? = null

) : Comparable<UnsplashPhoto>, Parcelable {
    override fun compareTo(other: UnsplashPhoto): Int = id.compareTo(other.id)

    override fun equals(other: Any?): Boolean {
        return (other as? UnsplashPhoto)?.id == id
    }
}
