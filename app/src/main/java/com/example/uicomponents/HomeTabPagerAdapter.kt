package com.example.uicomponents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomeTabPagerAdapter(fm: FragmentManager, context: MainActivity) : FragmentStatePagerAdapter(fm) {

    private val fragmentList = ArrayList<Fragment>()
    private val tabTitleList = ArrayList<String>()
    private val context = context

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addTab(fragment: Fragment, tabTitle: String) {
        fragmentList.add(fragment)
        tabTitleList.add(tabTitle)
    }

    fun getCustomTabView(position: Int, titleColor: Int, backgroundColor: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tab_header, null)
        val tvTabHeader = view.findViewById<TextView>(R.id.tvTabHeader)
        tvTabHeader.apply {
            text = tabTitleList[position]
            setTextColor(ContextCompat.getColor(context, titleColor))
            background = ContextCompat.getDrawable(context, backgroundColor)
        }
        return view
    }
}