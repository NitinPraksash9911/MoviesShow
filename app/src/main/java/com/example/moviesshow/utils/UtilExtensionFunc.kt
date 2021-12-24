package com.example.moviesshow.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesshow.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun ImageView.loadImageWithUrl(imgUrl: String?) {
    val brokenImage = R.drawable.ic_broken_image
    Glide.with(this.context)
        .load(imgUrl ?: brokenImage)
        .centerCrop()
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(brokenImage)
        )
        .into(this)
}

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })

    return query

}

fun String.snack(color: Int, view: View, duration: Int = Snackbar.LENGTH_LONG) {
    val snackbar = Snackbar.make(view, this, duration)
    snackbar.view.setBackgroundColor(color)
    snackbar.setTextColor(Color.WHITE)
    snackbar.show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}