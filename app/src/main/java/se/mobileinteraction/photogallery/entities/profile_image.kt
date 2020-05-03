package se.mobileinteraction.photogallery.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class profile_image(

    val small: String,
    val medium: String,
    val large: String
) : Parcelable
