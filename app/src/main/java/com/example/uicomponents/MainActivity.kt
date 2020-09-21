package com.example.uicomponents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.uicomponents.listener.DrawerItemClickListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_progressbar.*
import kotlinx.android.synthetic.main.item_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener, NavController.OnDestinationChangedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var listener: DrawerItemClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        launchMainActivity()
        setUpBottomNavigationBar()
        setViewOnClickListener()
        setDataToDrawerLayoutHeader()
    }

    private fun setUpBottomNavigationBar() {
        val navigationController = this.findNavController(R.id.fragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navigationController)
        navigationController.addOnDestinationChangedListener(this)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        addBadge(R.id.notificationFragment)
        addBadge(R.id.homeFragment)
    }

    private fun setViewOnClickListener() {
        ibDrawerLayout.setOnClickListener(this)
        drawerNavigation.setNavigationItemSelectedListener(this)
    }

    private fun setDataToDrawerLayoutHeader() {
        val ivDrawerLayoutHeader = drawerNavigation.getHeaderView(0).findViewById<ImageView>(R.id.ivDrawerLayoutHeader)
        Glide.with(this)
            .load("https://scontent.fsgn2-2.fna.fbcdn.net/v/t1.0-9/107108715_1350144558513227_4942331082410882474_o.jpg?_nc_cat=103&_nc_sid=e3f864&_nc_ohc=8dvXnUAyQsEAX8vejPi&_nc_ht=scontent.fsgn2-2.fna&oh=e472555f92b90e99412ae18489062cb7&oe=5F7AE957")
            .placeholder(R.mipmap.ic_test)
            .into(ivDrawerLayoutHeader)
    }

    private fun addBadge(id: Int) {
        var badge = bottomNavigationView.getOrCreateBadge(id)
        badge.isVisible = true
        badge.number = 17
    }

    private fun clearBadgeNumber(id: Int) {
        if(id == R.id.homeFragment || id == R.id.notificationFragment || id == R.id.moreFragment) {
            val badgeDrawable = bottomNavigationView.getBadge(id)
            if(badgeDrawable != null) {
                badgeDrawable.isVisible = false
                badgeDrawable.clearNumber()
            }
        }
    }

    private fun switchTab(position: Int) {
        drawerLayout.closeDrawers()
        listener.setOnDrawerItemClickListener(position)
    }

    private fun launchMainActivity() {
        pbLaunching.show()
        bottomNavigationView.visibility = View.GONE
        lifecycleScope.launch(Dispatchers.IO) {
            delay(2000)
            Log.d("###", "Launching")
            withContext(Dispatchers.Main) {
                pbLaunching.hide()
                layoutLaunching.visibility = View.GONE
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    fun setUpDrawerItemClickListener(drawerItemClickListener: DrawerItemClickListener) {
        this.listener = drawerItemClickListener
    }

    override fun onClick(view: View?) {
        drawerLayout.openDrawer(Gravity.LEFT)
    }

    override fun onNavigationItemSelected(menuItemView: MenuItem): Boolean {
        when(menuItemView.itemId) {
            R.id.menuTabLeft -> switchTab(0)
            R.id.menuTabMiddle -> switchTab(1)
            R.id.menuTabRight -> switchTab(2)
            R.id.homeFragment -> this.findNavController(R.id.fragment).navigate(R.id.homeFragment)
            R.id.notificationFragment -> this.findNavController(R.id.fragment).navigate(R.id.notificationFragment)
            R.id.moreFragment -> this.findNavController(R.id.fragment).navigate(R.id.moreFragment)
        }
        clearBadgeNumber(menuItemView.itemId)
        return true
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        tvTitleFragment.text = destination.label
    }
}
