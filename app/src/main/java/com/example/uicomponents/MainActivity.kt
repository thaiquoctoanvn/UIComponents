package com.example.uicomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_toolbar.*

class MainActivity : AppCompatActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener, NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setUpNavigation()
        setViewOnClickListener()
    }

    private fun setUpNavigation() {
        val navigationController = this.findNavController(R.id.fragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navigationController)
        navigationController.addOnDestinationChangedListener(this)
    }

    private fun setViewOnClickListener() {
        ibDrawerLayout.setOnClickListener(this)
        drawerNavigation.setNavigationItemSelectedListener(this)

    }

    private fun openTab() {

    }

    override fun onClick(p0: View?) {
        drawerLayout.openDrawer(Gravity.LEFT)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId) {
            R.id.menuTabLeft -> Toast.makeText(this, "Tab left", Toast.LENGTH_SHORT).show()
            R.id.menuTabMiddle -> openTab()
            R.id.menuTabRight -> openTab()
        }
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
