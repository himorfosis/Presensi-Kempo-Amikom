package com.kempoamikompresensi.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.kempoamikompresensi.R
import com.kempoamikompresensi.auth.Login
import com.kempoamikompresensi.home.HomeActivity.Companion.HOME
import com.kempoamikompresensi.home.fragment.HomeFragmnet
import com.kempoamikompresensi.home.fragment.KenshiFragment
import com.kempoamikompresensi.util.preferences.AppPreferences
import org.jetbrains.anko.intentFor


class HomepageActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val HOME = "HOME"
        val KENSHI = "KENSHI"
    }

    var fragmentActive = ""

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        checkParseData()

    }

    private fun checkParseData() {

        val getFrom = intent.getStringExtra("from")
        if (getFrom != null) {
            if (getFrom == HomepageActivity.HOME) {
                homeFragment()
            } else if (getFrom == HomepageActivity.KENSHI) {
                kenshiFragment()
            } else {
                homeFragment()
            }

        } else {
            homeFragment()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.side_home -> {
                homeFragment()
                true
            }
            R.id.side_kenshi -> {
                kenshiFragment()
                true
            }
            R.id.side_logout -> {
                logout()
                true
            }
            else -> {
                homeFragment()
                true
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun homeFragment() {
        fragmentActive = HOME
        navView.menu.getItem(0).isChecked = true
        val fragment = HomeFragmnet()
        replaceFragment(fragment)
    }

    private fun kenshiFragment() {

        fragmentActive = KENSHI
        navView.menu.getItem(1).isChecked = true
        val fragment = KenshiFragment()
        replaceFragment(fragment)
    }

    private fun logout() {

        val account: AppPreferences = AppPreferences(this, "account")
        account.clear()

        startActivity(intentFor<Login>())

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, fragment, fragment.javaClass.simpleName)
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
