package com.example.moviesshow.ui

import android.os.Bundle
import android.os.PersistableBundle
import com.example.moviesshow.arch.BaseActivity
import com.example.moviesshow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

}