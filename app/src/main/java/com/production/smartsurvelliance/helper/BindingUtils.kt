package com.production.smartsurvelliance.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import timber.log.Timber

@BindingAdapter("customImageUrl")
fun ImageView.setCustomImageUrl(url: String?) {
    Timber.tag("Binding").d(url.toString())
    loadImage(url)
}