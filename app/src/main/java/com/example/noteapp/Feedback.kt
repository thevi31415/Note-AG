package com.example.noteapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feedback.*

class Feedback : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Phản hồi"
        val s1 = (0..9).random().toString() + (0..9).random().toString() + (0..9).random()
            .toString() + (0..9).random().toString() + (0..9).random().toString() + (0..9).random()
            .toString()
        btn_sent.setOnClickListener {
            val email = "noteag31415@gmail.com"
            val subject = "[NoteAG $s1] Phản hồi: "
            val message = edit_messsg.text.toString()
            val address = email.split(",".toRegex()).toTypedArray()
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, address)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
            }
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}