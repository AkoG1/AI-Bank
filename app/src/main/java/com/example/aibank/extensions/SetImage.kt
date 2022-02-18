package com.example.aibank.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.aibank.R

fun ImageView.setImage(url: String?) {

    if (!url.isNullOrEmpty()) {
        Glide.with(this).load("$url").placeholder(R.mipmap.ic_launcher)
            .into(this)
    } else {
        setImageResource(R.mipmap.ic_launcher)
    }
}