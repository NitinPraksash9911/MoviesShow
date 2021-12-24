package com.example.moviesshow.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectOnLifeCycleScope(flow: Flow<T>, collect: suspend (T) -> Unit) {

    this.lifecycleScope.launch {

        repeatOnLifecycle(Lifecycle.State.STARTED) {

            flow.collectLatest(collect)

        }

    }
}






