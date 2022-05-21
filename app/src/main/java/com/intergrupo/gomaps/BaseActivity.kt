package com.intergrupo.gomaps

import android.content.Intent
import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.intergrupo.gomaps.ui.view.HomeActivity
import com.intergrupo.gomaps.ui.view.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun barNavigationColor() {

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(baseContext, R.color.backgroundview)
        window.navigationBarColor = ContextCompat.getColor(baseContext, R.color.backgroundview)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun barNavigationColorHome() {

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(baseContext, R.color.colorBotton)
        window.navigationBarColor = ContextCompat.getColor(baseContext, R.color.colorBotton)

    }


    fun navigateToSplahs() {

        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()

    }

}