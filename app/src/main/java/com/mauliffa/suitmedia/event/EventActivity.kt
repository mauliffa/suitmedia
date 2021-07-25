package com.mauliffa.suitmedia.event

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mauliffa.suitmedia.R
import com.mauliffa.suitmedia.databinding.ActivityEventBinding
import kotlin.collections.ArrayList

class EventActivity : AppCompatActivity() {
    private var _binding: ActivityEventBinding? = null
    private val binding get() = _binding!!
    private val eventList: ArrayList<Event> = arrayListOf()
    private val newEventList: ArrayList<Event> = arrayListOf()
    private lateinit var listAdapter: EventAdapter

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvEvent.setHasFixedSize(true)
        eventList.addAll(EventDataDummy.listData)

        listAdapter = EventAdapter(eventList)
        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        binding.rvEvent.adapter = listAdapter
        binding.progressBar.visibility = View.GONE

        listAdapter.setOnItemClickCallback(object : EventAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Event) {
                sendDataToMenuActivity(data)
            }
        })
    }

    private fun sendDataToMenuActivity(event: Event) {
        val moveIntent = Intent()
        moveIntent.putExtra(EXTRA_EVENT, event)
        setResult(Activity.RESULT_OK, moveIntent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_event, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.btn_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.text_search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String): Boolean {
                Toast.makeText(this@EventActivity, "Anda mencari event $newText", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_back_event -> {
                super.onBackPressed()
                return true
            }
            R.id.btn_map -> {
                Toast.makeText(this, "Anda memilih fitur map", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}