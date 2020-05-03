package se.mobileinteraction.photogallery.di

import androidx.preference.PreferenceManager
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import se.mobileinteraction.photogallery.BuildConfig
import se.mobileinteraction.photogallery.MainActivityViewModel
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.data.api.UnsplashApi
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.ui.favourites.FavouritesListViewModel
import se.mobileinteraction.photogallery.ui.map.MapsFragmentViewModel
import se.mobileinteraction.photogallery.ui.photos.DetailsPageViewModel
import se.mobileinteraction.photogallery.ui.photos.PhotoListViewModel
import se.mobileinteraction.photogallery.ui.search.SearchResultViewModel
import se.mobileinteraction.photogallery.ui.search.SearchViewModel

val appModule = module {
    factory<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }
    factory<Converter.Factory> { MoshiConverterFactory.create() }
    single {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        Retrofit.Builder()
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .baseUrl(androidApplication().getString(R.string.base_api))
            .client(clientBuilder.build())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(UnsplashApi::class.java)
    }

    single {
        PreferenceManager.getDefaultSharedPreferences(androidContext())
    }

    single { PhotosRepository(androidApplication(), get(), get(),get()) }

    single { Moshi.Builder().build() }

    viewModel { PhotoListViewModel(get()) }
    viewModel { FavouritesListViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { SearchViewModel() }
    viewModel { (query: String) -> SearchResultViewModel(get(), query) }
    viewModel { (unsplashPhoto: UnsplashPhoto) -> DetailsPageViewModel(get(), unsplashPhoto) }
    viewModel { MapsFragmentViewModel(get()) }

}
