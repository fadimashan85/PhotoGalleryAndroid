package se.mobileinteraction.photogallery

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import se.mobileinteraction.photogallery.data.api.UnsplashApi
import se.mobileinteraction.photogallery.data.repository.PhotosRepository
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import java.util.*

@RunWith(AndroidJUnit4ClassRunner::class)
class FavTest {

    @MockK
    lateinit var mockApi: UnsplashApi
    lateinit var moshi: Moshi
    lateinit var repo: PhotosRepository
    lateinit var sharedPref: SharedPreferences


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        MockKAnnotations.init(this)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        moshi = Moshi.Builder()
            .build()

        repo = PhotosRepository(context, mockApi, sharedPref, moshi)
        repo.clearAll()
    }



    @Test
    fun sizeOfFavList() {
        val list = grnrtateTestUnsplash(1).first()

        repo.saveAsFav(list)
        assertThat(repo.readFav().size, equalTo(1))

        repo.deleteFav(list)
        assertThat(repo.readFav().size, equalTo(0))

        repo.clearAll()
        assertThat(repo.readFav().size, equalTo(0))
    }




    private fun grnrtateTestUnsplash(count: Int): List<UnsplashPhoto> =
        (1..count).map { UnsplashPhoto(id = UUID.randomUUID().toString()) }


}