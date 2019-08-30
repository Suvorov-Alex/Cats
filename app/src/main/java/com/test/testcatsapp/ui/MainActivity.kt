package com.test.testcatsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.test.testcatsapp.R
import com.test.testcatsapp.ui.cats.photo.CatPhotoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val navController by lazy { findNavController(R.id.catsNavHost) }

    private var skipItemSelectAction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBottomNavigationView.setupWithNavController(navController)
        mainBottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            if (!skipItemSelectAction) {
                when (navController.currentDestination?.id) {
                    R.id.catsFragment -> {
                        // Cond to prevent incorrect self navigation
                        if (menuItem.itemId == R.id.favoriteCatsMenuItem) {
                            navigateFromCatsToFavoriteCats()
                        }
                    }
                    R.id.favoriteCatsFragment -> {
                        // Cond to prevent incorrect self navigation
                        if (menuItem.itemId == R.id.catsMenuItem) {
                            navigateFromFavoriteCatsToCats()
                        }
                    }
                    R.id.catPhotoFragment -> navigateFromCatPhotoToFavoriteCats()
                    com.test.testcatsapp.R.id.favoriteCatPhotoFragment -> navigateFromFavoriteCatPhotoToCats()
                }
            } else {
                skipItemSelectAction = false
            }
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is CatPhotoFragment) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.catsFragment -> finish()
            R.id.favoriteCatsFragment -> {
                navController.popBackStack()
                // Skip OnNavigationItemSelectedListener call to prevent already completed navigation
                skipItemSelectAction = true
                mainBottomNavigationView.selectedItemId = R.id.catsMenuItem
            }
            else -> super.onBackPressed()
        }
    }

    private fun navigateFromCatsToFavoriteCats() =
        navController.navigate(R.id.action_from_cats_to_favorite_cats)

    private fun navigateFromFavoriteCatsToCats() =
        navController.navigate(R.id.action_from_favorite_cats_to_cats)

    private fun navigateFromCatPhotoToFavoriteCats() =
        navController.navigate(R.id.action_from_cat_photo_to_favorite_cats)

    private fun navigateFromFavoriteCatPhotoToCats() =
        navController.navigate(R.id.action_from_favorite_cat_photo_to_cats)
}
