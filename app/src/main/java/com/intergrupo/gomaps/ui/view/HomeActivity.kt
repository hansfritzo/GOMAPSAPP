package com.intergrupo.gomaps.ui.view

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.intergrupo.gomaps.BaseActivity
import com.intergrupo.gomaps.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        barNavigationColorHome()
    }

    override fun onBackPressed() {

        val finalHost = NavHostFragment.create(R.navigation.nav_home)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host, finalHost)
            .setPrimaryNavigationFragment(finalHost)
            .commit()

    }
}