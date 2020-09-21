package com.example.uicomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.uicomponents.adapter.HomeTabPagerAdapter
import com.example.uicomponents.listener.DrawerItemClickListener
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment(), ViewPager.OnPageChangeListener,
    DrawerItemClickListener {

    private val BACKGROUND_DEFAULT = R.drawable.background_default
    private val BACKGROUND_SELECTED = R.drawable.background_selected
    private val COLOR_DEFAULT = R.color.colorGrey
    private val COLOR_SELECTED = R.color.colorWhite
    private var currentPosition = 0

    private lateinit var homeTabPagerAdapter: HomeTabPagerAdapter
    private lateinit var vpHome: ViewPager
    private lateinit var tabLayoutHome: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        vpHome = view.findViewById(R.id.vpHome)
        tabLayoutHome = view.findViewById(R.id.tabLayoutHome)

        setUpTabToViewPager()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = activity as MainActivity
        mainActivity.setUpDrawerItemClickListener(this)
    }

    private fun setUpTabToViewPager() {
        homeTabPagerAdapter =
            HomeTabPagerAdapter(
                childFragmentManager,
                tabLayoutHome
            )
        homeTabPagerAdapter.apply {
            addTab(LeftFragment(), "Tab left")
            addTab(MiddleFragment(), "Tab middle")
            addTab(RightFragment(), "Tab right")
            addTab(ColorFragment(), "Tab color")
            addTab(SlowMotionAddingFragment(), "Tab slow motion")
        }
        vpHome.adapter = homeTabPagerAdapter
        vpHome.addOnPageChangeListener(this)
        tabLayoutHome.setupWithViewPager(vpHome)
        highlightCurrentTabHeader(currentPosition)
    }

    private fun highlightCurrentTabHeader(position: Int) {
        var i = 0

        while (i < tabLayoutHome.tabCount) {
            val tab = tabLayoutHome.getTabAt(i)
            tab?.apply {
                customView = null
                customView = homeTabPagerAdapter.getCustomTabView(i, COLOR_DEFAULT, BACKGROUND_DEFAULT)
            }
            i++
        }
        val tab = tabLayoutHome.getTabAt(position)
        tab?.apply {
            customView = null
            customView = homeTabPagerAdapter.getCustomTabView(position, COLOR_SELECTED, BACKGROUND_SELECTED)
        }
    }

    companion object {}

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        currentPosition = position
        highlightCurrentTabHeader(currentPosition)
    }

    override fun setOnDrawerItemClickListener(position: Int) {
        when(position) {
            0 -> vpHome.currentItem = 0
            1 -> vpHome.currentItem = 1
            2 -> vpHome.currentItem = 2
        }
    }
}
