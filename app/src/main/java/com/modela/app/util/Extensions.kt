package com.modela.app.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.modela.app.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun View.visible() { visibility = View.VISIBLE }
fun View.gone() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) {
        setImageResource(R.drawable.bg_placeholder)
        return
    }
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.bg_placeholder)
        .error(R.drawable.bg_placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
        .into(this)
}

fun Long.toTimeAgo(): String {
    val diff = System.currentTimeMillis() - this
    return when {
        diff < 60_000 -> "Just now"
        diff < 3_600_000 -> "${diff / 60_000}m ago"
        diff < 86_400_000 -> "${diff / 3_600_000}h ago"
        diff < 604_800_000 -> "${diff / 86_400_000}d ago"
        else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(this))
    }
}

fun Long.toMessageTime(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(this))
}
