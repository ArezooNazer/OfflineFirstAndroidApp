package com.arezoo.offline.presentation.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.arezoo.offline.R
import com.arezoo.offline.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}