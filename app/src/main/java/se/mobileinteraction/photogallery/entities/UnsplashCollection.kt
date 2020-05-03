package se.mobileinteraction.photogallery.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashCollection(

    val id: String,
    val title: String,
    val published_at: String,
    val updated_at: String,
    val user: UnsplashUser

) : Comparable<UnsplashCollection>, Parcelable {
    override fun compareTo(other: UnsplashCollection): Int = id.compareTo(other.id)

    override fun equals(other: Any?): Boolean {
        return (other as? UnsplashCollection)?.id == id
    }
}
