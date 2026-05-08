package com.modela.app.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.modela.app.R
import com.modela.app.data.model.JobTag
import com.modela.app.data.model.JobTagType
import java.util.concurrent.TimeUnit

object JobTagHelper {

    fun colorsForType(type: JobTagType): Pair<String, String> = when (type) {
        JobTagType.URGENT -> Pair("#22D64B5F", "#D64B5F")
        JobTagType.SEASON -> Pair("#225B7FFF", "#5B7FFF")
        JobTagType.EXCLUSIVE -> Pair("#22A7A7A7", "#5E5E5E")
        JobTagType.NEW -> Pair("#224F8A6B", "#4F8A6B")
        JobTagType.CATEGORY -> Pair("#FFF7F7F7", "#5E5E5E")
    }

    fun addTagsTo(container: LinearLayout, tags: List<JobTag>) {
        container.removeAllViews()
        val inflater = LayoutInflater.from(container.context)
        val radius = 6f * container.resources.displayMetrics.density

        tags.forEach { tag ->
            val tagView = inflater.inflate(R.layout.item_job_tag, container, false) as TextView
            val (bgHex, textHex) = colorsForType(tag.type)
            tagView.text = tag.label
            tagView.background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = radius
                setColor(Color.parseColor(bgHex))
                setStroke(1, Color.parseColor("#14000000"))
            }
            tagView.setTextColor(Color.parseColor(textHex))
            container.addView(tagView)
        }
    }

    fun formatPostedTime(timestamp: Long): String {
        val diff = System.currentTimeMillis() - timestamp
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        return when {
            minutes < 1 -> "agora"
            minutes < 60 -> "ha ${minutes}min"
            hours < 24 -> "ha ${hours}h"
            days == 1L -> "ontem"
            else -> "ha ${days}d"
        }
    }
}
