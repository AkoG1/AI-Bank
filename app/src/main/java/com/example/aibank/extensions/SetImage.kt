package com.example.cryptofragment.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.cryptofragment.R

fun ImageView.setImage(url: String?) {

    if (!url.isNullOrEmpty()) {
        Glide.with(this).load("$url").placeholder(R.mipmap.ic_launcher)
            .into(this)
    } else {
        setImageResource(R.mipmap.ic_launcher)
    }
}