package com.mauliffa.suitmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mauliffa.suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val IMAGE_REQUEST_CODE = 100
        var photo: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddPicture.setOnClickListener { pickPictureFromGallery() }
        binding.btnMenu.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isEmpty() || name.contains("   ")){
                val toastName = getString(R.string.text_input_name)
                Toast.makeText(this, toastName, Toast.LENGTH_SHORT).show()
            } else {
                val palindrome = getString(R.string.palindrome)
                val notPalindrome = getString(R.string.not_palindrome)
                    if (palindromeSentence(name)) {
                        Toast.makeText(this, "$name $palindrome", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "$name $notPalindrome", Toast.LENGTH_LONG).show()
                    }
                val moveIntent = Intent(this@MainActivity, MenuActivity::class.java)
                moveIntent.putExtra(MenuActivity.EXTRA_NAME, name)
                moveIntent.putExtra(MenuActivity.EXTRA_PICTURE, photo)
                startActivity(moveIntent)
            }
        }
    }

    private fun pickPictureFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null) {
                val dataPhoto = data.data
                photo = dataPhoto.toString()
                binding.btnAddPicture.setImageURI(dataPhoto)
            }
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