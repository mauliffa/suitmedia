package com.mauliffa.suitmedia.event

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mauliffa.suitmedia.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity() {
    private var _binding: ActivityEventBinding? = null
    private val binding get() = _binding!!
    private val list: ArrayList<Event> = arrayListOf()

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEvent.setHasFixedSize(true)
        list.addAll(EventDataDummy.listData)

        val listAdapter = EventAdapter(list)
        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        binding.rvEvent.adapter = listAdapter
        binding.progressBar.visibility = View.GONE

        listAdapter.setOnItemClickCallback(object: EventAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Event) {
                sendDataToMenuActivity(data)
            }
        })

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun sendDataToMenuActivity(event: Event) {
        val moveIntent = Intent()
        moveIntent.putExtra(EXTRA_EVENT, event)
        setResult(Activity.RESULT_OK, moveIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}