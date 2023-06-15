package com.example.login_signup

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class send_message : AppCompatActivity() {

    private lateinit var button:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_message)

        button=findViewById(R.id.button)

        button.setOnClickListener {
            val intent= Intent(Intent.ACTION_SEND)
            intent.data= Uri.parse("Mail to:")
            intent.type="text/plan"
            startActivity(Intent.createChooser(intent,"choose email client "))
        }



    }
}