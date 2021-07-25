package com.mauliffa.suitmedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mauliffa.suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMenu.setOnClickListener {
            val name = binding.etName.text.toString()
            val moveIntent = Intent(this@MainActivity, MenuActivity::class.java)
            moveIntent.putExtra(MenuActivity.EXTRA_NAME, name)
            startActivity(moveIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}