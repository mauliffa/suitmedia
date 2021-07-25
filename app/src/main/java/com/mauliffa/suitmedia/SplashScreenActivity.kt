package com.mauliffa.suitmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mauliffa.suitmedia.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoSS.alpha = 0f
        binding.logoSS.animate().setDuration(3000).alpha(1f).withEndAction {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}