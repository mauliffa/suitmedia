package com.mauliffa.suitmedia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.mauliffa.suitmedia.databinding.ActivityMenuBinding
import com.mauliffa.suitmedia.event.Event
import com.mauliffa.suitmedia.event.EventActivity
import com.mauliffa.suitmedia.guest.GuestActivity
import com.mauliffa.suitmedia.guest.GuestResponseItem

class MenuActivity : AppCompatActivity() {
    private var _binding: ActivityMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var activityResultEvent: ActivityResultLauncher<Intent>
    private lateinit var activityResultGuest: ActivityResultLauncher<Intent>

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PICTURE = "extra_picture"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val picture = intent.getStringExtra(EXTRA_PICTURE)?.toUri()
        binding.tvName.text = name
        binding.ivPhoto.setImageURI(picture)
        binding.progressBar.visibility = View.GONE

        binding.btnEvent.setOnClickListener {
            val moveIntent = Intent(this@MenuActivity, EventActivity::class.java)
            activityResultEvent.launch(moveIntent)
        }

        binding.btnGuest.setOnClickListener {
            val moveIntent = Intent(this@MenuActivity, GuestActivity::class.java)
            activityResultGuest.launch(moveIntent)
        }

        binding.btnLanguage.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        binding.btnChangeUser.setOnClickListener{
            val moveIntent = Intent(this@MenuActivity, MainActivity::class.java)
            startActivity(moveIntent)
        }

        activityResultEvent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            resultEvent: ActivityResult? ->
            if (resultEvent?.resultCode == Activity.RESULT_OK) {
                val eventData = resultEvent.data?.getParcelableExtra<Event>(EventActivity.EXTRA_EVENT)
                if (eventData != null) {
                    val txtEventName = getString(R.string.text_event_name)
                    val txtEventDate = getString(R.string.text_event_date)
                    val eventName = eventData.eventName
                    val eventDate = eventData.eventDate
                    val text = "\n$txtEventName -> $eventName\n\n$txtEventDate -> $eventDate\n"
                    binding.btnEvent.text = text
                } else {
                    Toast.makeText(this, "datanya null nih", Toast.LENGTH_SHORT).show()
                }
            }
        }

        activityResultGuest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult? ->
                if (result?.resultCode == Activity.RESULT_OK) {
                    val guestData = result.data?.getParcelableExtra<GuestResponseItem>(GuestActivity.EXTRA_GUEST)
                    if (guestData != null) {
                        val txtGuestName = getString(R.string.text_guest_name)
                        val txtGuestBirthdate = getString(R.string.text_guest_birthdate)
                        val guestName = guestData.name
                        val guestBirthdate = guestData.birthdate
                        val text = "\n$txtGuestName -> $guestName\n\n$txtGuestBirthdate -> $guestBirthdate\n"
                        binding.btnGuest.text = text
                    }
                }
            }
    }


    override fun onBackPressed() {
        showAlertDialog()
    }

    private fun showAlertDialog() {
        val dialogTitle = getString(R.string.text_close)
        val dialogMessage = getString(R.string.asking_to_close)

        val alertDialogBuilder = this.let { AlertDialog.Builder(it) }
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                moveTaskToBack(true)
                finish()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}