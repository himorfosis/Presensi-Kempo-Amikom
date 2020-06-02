package com.kempoamikompresensi.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kempoamikompresensi.R
import com.kempoamikompresensi.home.fragment.HomeFragmnet
import com.kempoamikompresensi.home.fragment.KenshiFragment
import com.kempoamikompresensi.home.fragment.AccountFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_title.*

class HomeActivity : AppCompatActivity() {

    companion object {
        var fragmentActive = ""
        val HOME = "HOME"
        val KENSHI = "KENSHI"
        val ACCOUNT = "ACCOUNT"
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                if (fragmentActive != HOME) {
                    homeFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.nav_kenshi -> {
                if (fragmentActive != KENSHI) {
                    kenshiFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.nav_account -> {
                if (fragmentActive != ACCOUNT) {
                    accountFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        window.navigationBarColor = resources.getColor(R.color.white)
        initUI()
        checkParseData()

    }

    private fun initUI() {
        titlebar_tv.text = "Presensi Kempo Amikom"
    }

    private fun checkParseData() {

        val getFrom = intent.getStringExtra("from")
        if (getFrom != null) {
            if (getFrom == HOME) {
                homeFragment()
            } else if (getFrom == KENSHI) {
                kenshiFragment()
            } else if (getFrom == ACCOUNT) {
                accountFragment()
            } else {
                homeFragment()
            }

        } else {
            homeFragment()
        }

    }

    private fun homeFragment() {
        fragmentActive = HOME
        val fragment = HomeFragmnet()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_home).isChecked = true
    }

    private fun kenshiFragment() {
        fragmentActive = KENSHI
        val fragment = KenshiFragment()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_kenshi).isChecked = true
    }

    private fun accountFragment() {
        fragmentActive = ACCOUNT
        val fragment = AccountFragment()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_account).isChecked = true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        if (fragmentActive == HOME) {
            finishAffinity()
        } else {
            fragmentActive = HOME
            homeFragment()
        }
    }


}
