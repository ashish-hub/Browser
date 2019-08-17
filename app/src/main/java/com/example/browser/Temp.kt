package com.example.browser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_temp.*

class Temp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        //val ide = noid as WebView
        val data = intent.getStringExtra("data")
        Toast.makeText(this, "$data", Toast.LENGTH_LONG)
        Log.d("======================================", "$data")
    }
}
