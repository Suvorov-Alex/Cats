package com.test.testcatsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.testcatsapp.R
import com.test.testcatsapp.ui.cats.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val backStack = Stack<Int>()

    private val fragments = listOf(
        BaseFragment.newInstance(R.layout.content_cats_base, R.id.catsNavHost),
        BaseFragment.newInstance(R.layout.content_favorite_cats_base, R.id.favoriteCatsNavHost)
    )

    private val indexToPage = mapOf(0 to R.id.catsMenuItem, 1 to R.id.favoriteCatsMenuItem)

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
        override fun onPageSelected(page: Int) {
            val itemId = indexToPage[page] ?: R.id.home
            if (mainBottomNavigationView.selectedItemId != itemId) {
                mainBottomNavigationView.selectedItemId = itemId
            }
        }
    }

    private val onNavigationItemReselectedListener =
        BottomNavigationView.OnNavigationItemReselectedListener { item ->
            val position = indexToPage.values.indexOf(item.itemId)
            val fragment = fragments[position]
            fragment.popToRoot()
        }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val position = indexToPage.values.indexOf(item.itemId)
            if (mainViewPager.currentItem != position) {
                setItem(position)
            }
            true
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewPager.apply {

            addOnPageChangeListener(onPageChangeListener)
            adapter = ViewPagerAdapter()
        }

        mainBottomNavigationView.apply {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            setOnNavigationItemReselectedListener(onNavigationItemReselectedListener)
        }

        if (backStack.empty()) {
            backStack.push(0)
        }
    }

    override fun onBackPressed() {
        val fragment = fragments[mainViewPager.currentItem]
        val hasNestedFragments = fragment.popBackStack()
        if (!hasNestedFragments) {
            if (backStack.size > 1) {
                backStack.pop()
                mainViewPager.currentItem = backStack.peek()
                return
            }
            super.onBackPressed()
        }
    }

    private fun setItem(position: Int) {
        mainViewPager.currentItem = position
        backStack.push(position)
    }

    inner class ViewPagerAdapter : FragmentPagerAdapter(
        supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getItem(position: Int): Fragment = fragments[position]
        override fun getCount(): Int = fragments.size
    }
}
