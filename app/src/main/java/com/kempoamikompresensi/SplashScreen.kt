package com.kempoamikompresensi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.kempoamikompresensi.auth.Login
import com.kempoamikompresensi.home.HomepageActivity
import com.kempoamikompresensi.util.preferences.AppPreferences
import org.jetbrains.anko.intentFor

class SplashScreen : AppCompatActivity() {

    lateinit var account: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        account = AppPreferences(this, "account")
        val officer = account.getString("officer")

        Handler().postDelayed(object : Thread() {
            override fun run() {
                if (officer == null || officer.isEmpty()) {
                    startActivity(intentFor<Login>())
                } else {
                    startActivity(intentFor<HomepageActivity>())
                }
            }
        }, 1000)
    }

    private fun isLog(msg: String) {
        Log.e("SplashScreen", msg)
    }

}
