package com.example.stickynoteapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplassScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splass_screen)

        android.os.Handler().postDelayed(Runnable {
             val intent = Intent(SplassScreen@this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },4000)

    }
}