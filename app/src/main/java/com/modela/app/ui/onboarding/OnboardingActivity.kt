package com.modela.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.modela.app.R
import com.modela.app.databinding.ActivityOnboardingBinding
import com.modela.app.ui.auth.AuthActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter
    private val slides = listOf(
        OnboardingSlide(R.string.onboarding_title_1, R.string.onboarding_desc_1),
        OnboardingSlide(R.string.onboarding_title_2, R.string.onboarding_desc_2),
        OnboardingSlide(R.string.onboarding_title_3, R.string.onboarding_desc_3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = OnboardingAdapter(slides)
        binding.viewPager.adapter = adapter

        setupDots(0)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setupDots(position)
                binding.btnNext.text = if (position == slides.size - 1) {
                    getString(R.string.onboarding_get_started)
                } else {
                    getString(R.string.onboarding_next)
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < slides.size - 1) {
                binding.viewPager.currentItem += 1
            } else {
                navigateToAuth()
            }
        }

        binding.tvSkip.setOnClickListener { navigateToAuth() }
    }

    private fun setupDots(currentPosition: Int) {
        binding.dotsContainer.removeAllViews()
        slides.forEachIndexed { index, _ ->
            val dot = ImageView(this).apply {
                setImageDrawable(ContextCompat.getDrawable(
                    this@OnboardingActivity,
                    if (index == currentPosition) R.drawable.dot_active else R.drawable.dot_inactive
                ))
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(8, 0, 8, 0) }
                layoutParams = params
            }
            binding.dotsContainer.addView(dot)
        }
    }

    private fun navigateToAuth() {
        startActivity(Intent(this, AuthActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}

data class OnboardingSlide(val titleRes: Int, val descriptionRes: Int)
