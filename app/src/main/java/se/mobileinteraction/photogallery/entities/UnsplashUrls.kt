package se.mobileinteraction.photogallery.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashUrls(
    val raw: String?= "1",
    val full: String?= "1",
    val regular: String?= "1",
    val small: String?= "1",
    val thumb: String?= "1"
) : Parcelable
