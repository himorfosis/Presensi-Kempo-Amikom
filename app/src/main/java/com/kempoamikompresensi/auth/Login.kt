package com.kempoamikompresensi.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kempoamikompresensi.R
import com.kempoamikompresensi.home.HomepageActivity
import com.kempoamikompresensi.util.preferences.AppPreferences
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class Login : AppCompatActivity() {

    lateinit var account: AppPreferences
    var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initUI()

    }

    private fun initUI() {

        login_btn.onClick {
            submitLogin()
        }

        close_login_img.onClick {
            finishAffinity()
        }

    }

    private fun submitLogin() {
        username = username_ed.text.toString()
        val password = password_ed.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            if (username.toLowerCase() == "admin" && password.toLowerCase() == "secret") {
                successLogin()
            } else if (username == "egar" && password == "secret") {
                successLogin()
            } else {
                toast("Username Atau Password Salah")
            }

        } else {
            toast("Harap Lengkapi Data")
        }

    }

    private fun successLogin() {
        account = AppPreferences(this, "account")
        account.saveString("officer", username)

        startActivity(intentFor<HomepageActivity>())
    }

    override fun onBackPressed() {
        finishAffinity()
    }

}
