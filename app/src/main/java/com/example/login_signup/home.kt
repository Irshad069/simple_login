package com.example.login_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class home : AppCompatActivity() {

    private lateinit var logout:ImageButton
    private lateinit var btndelete:Button
    private lateinit var databse: DatabaseReference
    private lateinit var auth: FirebaseAuth
    val user=FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth=FirebaseAuth.getInstance()

        logout=findViewById(R.id.Imgbutton)
        btndelete=findViewById(R.id.btn1)

        logout.setOnClickListener {
            auth.signOut()
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btndelete.setOnClickListener {
            if (user != null) {
                user.delete()

                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}