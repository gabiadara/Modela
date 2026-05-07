package com.modela.app.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.R
import com.modela.app.data.model.Review
import com.modela.app.databinding.ItemCharacteristicBinding
import com.modela.app.databinding.ItemPhotoGalleryBinding
import com.modela.app.databinding.ItemReviewBinding
import com.modela.app.util.loadImage

class PhotoGalleryAdapter(private val photos: List<String>) :
    RecyclerView.Adapter<PhotoGalleryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPhotoGalleryBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPhotoGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ivPhoto.loadImage(photos[position])
    }
    override fun getItemCount() = photos.size
}

class ReviewsAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviews[position]
        holder.binding.tvReviewerName.text = review.authorName
        holder.binding.tvReviewText.text = review.comment
        holder.binding.starsContainer.removeAllViews()
        repeat(5) { i ->
            val star = ImageView(holder.binding.root.context).apply {
                layoutParams = LinearLayout.LayoutParams(32, 32).apply { setMargins(0, 0, 4, 0) }
                setImageResource(R.drawable.ic_star)
                setColorFilter(ContextCompat.getColor(context,
                    if (i < review.rating.toInt()) R.color.match_gold else R.color.match_text_hint))
            }
            holder.binding.starsContainer.addView(star)
        }
    }
    override fun getItemCount() = reviews.size
}

class CharacteristicsAdapter(private val items: List<Pair<String, String>>) :
    RecyclerView.Adapter<CharacteristicsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCharacteristicBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCharacteristicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (label, value) = items[position]
        holder.binding.tvLabel.text = label
        holder.binding.tvValue.text = value
    }
    override fun getItemCount() = items.size
}
