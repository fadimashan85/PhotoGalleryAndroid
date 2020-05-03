package se.mobileinteraction.photogallery

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import se.mobileinteraction.photogallery.ui.AboutFragment
import se.mobileinteraction.photogallery.ui.favourites.FavouritesListFragment
import se.mobileinteraction.photogallery.ui.photos.PhotosListFragment
import se.mobileinteraction.photogallery.utils.NavExtensions.setUpBottomNavigation
import se.mobileinteraction.photogallery.utils.ScreenUtils


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = findNavController(R.id.navigation_host)
        /**
         * here I tried to add animation ti transition fragments
         * **/
        setUpBottomNavigation(bottomNavigationView, navController)
        observeData()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            viewModel.showSearchBar(destination.id)
            viewModel.shouldShowOpenSearchButton(destination.id)
            viewModel.showNavBootomBar(destination.id)
            viewModel.shouldShowOpenSearchButton(destination.id)
        }

        search_bar.apply {
            (layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin += ScreenUtils.getStatusBarHeight()
            }
        }

        btn_open_search.apply {
            (layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin += ScreenUtils.getStatusBarHeight()
            }
        }

        btn_open_search.setOnClickListener {
            findNavController(R.id.navigation_host).navigate(R.id.searchFragment)
        }


        // Modify the fist child "the bottom bar" in container without the whole space
        (bottomNavigationView.children.first() as ViewGroup).apply {
            background = ContextCompat.getDrawable(context, R.drawable.bottom_navigation_background)
            elevation = ScreenUtils.dipsToPixel(8f)

            (layoutParams as ViewGroup.MarginLayoutParams).apply {
                val big = ScreenUtils.dipsToPixel(16f).toInt()
                val small = ScreenUtils.dipsToPixel(8f).toInt()
                setMargins(small, big, small, big)
            }
        }

    }

    /** We create a function to collect the the height size of the search bar with the system
    bar to use it of padding the layout below the search bar **/
    fun recyclerViewPadding() = search_bar.bottom + ScreenUtils.dipsToPixel(16f).toInt()

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navigation_host).navigateUp()

    private fun observeData() {

        viewModel.searchVisiblity.observe(this, Observer {
            search_bar.visibility = it
        })

        viewModel.navBottomBar.observe(this, Observer {
            bottomNavigationView.visibility = it

        })

        viewModel.showSearchButton.observe(this, Observer {
            btn_open_search.visibility = it
        })
    }

    private fun currentFragment() {
        when (NavHostFragment.findNavController(navigation_host).currentDestination!!.id) {
            R.id.photosListFragment -> bottomNavigationView.checkItem(R.id.photosListFragment)
            R.id.favouriteFragment -> bottomNavigationView.checkItem(R.id.favouriteFragment)
            else -> bottomNavigationView.checkItem(R.id.aboutFragment)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        currentFragment()
    }


    internal fun BottomNavigationView.checkItem(actionId: Int) {
        menu.findItem(actionId)?.isChecked = true
    }


}
