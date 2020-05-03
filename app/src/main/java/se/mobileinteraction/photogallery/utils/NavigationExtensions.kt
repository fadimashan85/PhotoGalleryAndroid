package se.mobileinteraction.photogallery.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import se.mobileinteraction.photogallery.R

interface Reselectable {
    fun onReselected()
}

object NavExtensions {
    fun AppCompatActivity.setUpBottomNavigation(
        bottomNavigationView: BottomNavigationView,
        navController: NavController
    ) {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (navController.currentDestination?.id != it.itemId) {
                navController.navigate(it.itemId)
                true
            } else {
                false
            }

        }

        bottomNavigationView.setOnNavigationItemReselectedListener {
            val navHost = supportFragmentManager.findFragmentById(R.id.navigation_host)
            val fragment = navHost?.childFragmentManager?.fragments?.get(0)

            if (fragment is Reselectable) {
                fragment.onReselected()
            }
        }
    }


}
