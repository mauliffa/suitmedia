package com.mauliffa.suitmedia.guest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.mauliffa.suitmedia.databinding.ActivityGuestBinding
import com.mauliffa.suitmedia.guest.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestActivity : AppCompatActivity() {
    private var _binding: ActivityGuestBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_GUEST = "extra_guest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun getData(){
        val client = ApiConfig.getApiService().getGuestData()
        client.enqueue(object : Callback<ArrayList<GuestResponseItem>> {
            override fun onResponse(call: Call<ArrayList<GuestResponseItem>>, response: Response<ArrayList<GuestResponseItem>>) {
                Log.d("test", "onResponse, ${response.body()?.get(0)?.name}")
                val listGuest = response.body() as List<GuestResponseItem>
                showData(listGuest)
            }

            override fun onFailure(call: Call<ArrayList<GuestResponseItem>>, t: Throwable) {
                Log.d("test", "onFailure, $t")
            }
        })
    }

    private fun showData(guest: List<GuestResponseItem>){
        val gridAdapter = GuestAdapter(guest)
        binding.rvGuest.layoutManager = GridLayoutManager(this, 2)
        binding.rvGuest.adapter = gridAdapter
        binding.progressBar.visibility = View.GONE

        gridAdapter.setOnItemClickCallback(object: GuestAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GuestResponseItem) {
                val birthdate = data.birthdate
                val date = "${birthdate[8]}${birthdate[9]}"
                val month = "${birthdate[5]}${birthdate[6]}"

                checkPhone(date.toInt())
                if(checkPrime(month.toInt())) {
                    Toast.makeText (this@GuestActivity, "Month is a prime number", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText (this@GuestActivity, "Month isn't a prime number", Toast.LENGTH_SHORT).show()
                }

                sendDataToMenuActivity(data)
            }
        })
    }

    private fun checkPhone(date: Int){
        val number = 1..31
        val blackberry = number.filter { it % 2 == 0 }
        val android = number.filter { it % 3 == 0 }
        val iphone = number.filter { it % 6 == 0 }
        //val blackberry = arrayOf("02", "04", "06", "08","10","12", "14", "16", "18","20","22", "24", "26", "28","30",)
        //val android = arrayOf("03","06", "09","12", "15","18","21","24", "27","30")
        //val Iphone = arrayOf("06","12","18","24","30",)

        val phone = when (date) {
            in iphone -> "IPhone"
            in blackberry -> "Blackberry"
            in android -> "Android"
            else -> "Phone"
        }

        Toast.makeText (this@GuestActivity, phone, Toast.LENGTH_SHORT).show()
    }

    private fun checkPrime(month: Int): Boolean{
        if(month<2) return false
        for (x in 2..month/2){
            if(month % x == 0){
                return false
            }
        }
        return true
    }

    private fun sendDataToMenuActivity(data: GuestResponseItem) {
        val moveIntent = Intent()
        moveIntent.putExtra(EXTRA_GUEST, data)
        setResult(Activity.RESULT_OK, moveIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
