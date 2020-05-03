package se.mobileinteraction.photogallery.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashLinks(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,
    val download_location: String? = null,
    val photos: String? = null,
    val likes: String? = null,
    val portfolio: String? = null
): Parcelable
