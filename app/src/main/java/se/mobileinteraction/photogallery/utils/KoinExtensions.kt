package se.mobileinteraction.photogallery.utils

import android.content.Context
import org.koin.android.ext.android.getKoin
import org.koin.core.Koin
import se.mobileinteraction.photogallery.MainApplication

fun Context.koin() : Koin {
    return (this.applicationContext as? MainApplication)?.getKoin() ?: error("Koin can only be fetched from an application context")
}
