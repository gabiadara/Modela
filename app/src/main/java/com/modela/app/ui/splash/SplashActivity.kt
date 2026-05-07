package com.modela.app.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.modela.app.databinding.ActivitySplashBinding
import com.modela.app.ui.onboarding.OnboardingActivity
import com.modela.app.ui.main.MainActivity
import com.modela.app.util.Constants

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateLogo()

        Handler(Looper.getMainLooper()).postDelayed({
            val prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
            val isLoggedIn = prefs.getBoolean(Constants.KEY_IS_LOGGED_IN, false)
            val intent = if (isLoggedIn) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, OnboardingActivity::class.java)
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, Constants.SPLASH_DELAY)
    }

    private fun animateLogo() {
        val container = binding.logoContainer
        val fadeIn = ObjectAnimator.ofFloat(container, "alpha", 0f, 1f).apply { duration = 1000 }
        val scaleX = ObjectAnimator.ofFloat(container, "scaleX", 0.8f, 1f).apply { duration = 1000 }
        val scaleY = ObjectAnimator.ofFloat(container, "scaleY", 0.8f, 1f).apply { duration = 1000 }
        val translateY = ObjectAnimator.ofFloat(container, "translationY", 50f, 0f).apply { duration = 1000 }

        AnimatorSet().apply {
            playTogether(fadeIn, scaleX, scaleY, translateY)
            interpolator = DecelerateInterpolator()
            startDelay = 300
            start()
        }
    }
}
