package com.mauliffa.suitmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

            if (palindromeSentence(name)){
                Toast.makeText(this, "$name is Palindrome", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "$name not Palindrome", Toast.LENGTH_LONG).show()
            }

            val moveIntent = Intent(this@MainActivity, MenuActivity::class.java)
            moveIntent.putExtra(MenuActivity.EXTRA_NAME, name)
            startActivity(moveIntent)
        }
    }

    private fun palindromeSentence(input: String): Boolean{
        val reverse = StringBuilder(input).reverse().toString()
        return input.equals(reverse, ignoreCase = true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}