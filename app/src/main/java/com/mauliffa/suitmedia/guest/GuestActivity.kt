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
                if (data.id == 4 || data.id == 5){
                    Toast.makeText (this@GuestActivity, "IPhone", Toast.LENGTH_SHORT).show()
                } else if (data.id == 2){
                    Toast.makeText (this@GuestActivity, "Blackberry", Toast.LENGTH_SHORT).show()
                } else if (data.id == 3){
                    Toast.makeText (this@GuestActivity, "Android", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText (this@GuestActivity, "Phone", Toast.LENGTH_SHORT).show()
                }

                sendDataToMenuActivity(data)
            }
        })
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
