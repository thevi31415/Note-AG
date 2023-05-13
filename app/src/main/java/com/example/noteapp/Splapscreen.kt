package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_splapscreen.*

class Splapscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splapscreen)
        var fontc = ResourcesCompat.getFont(this, R.font.fc)
        txt_name.typeface = fontc
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }
}