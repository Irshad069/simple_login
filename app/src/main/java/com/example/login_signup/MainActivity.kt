package com.example.login_signup

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var edittext1:EditText
    private lateinit var edittext2:EditText
    private lateinit var btnlogin:Button
    private lateinit var textsignup:TextView

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    var user= FirebaseAuth.getInstance().currentUser

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth=FirebaseAuth.getInstance()
        if (auth.currentUser !=null){
            intent= Intent(this,home::class.java)
            startActivity(intent)
        }

        edittext1=findViewById(R.id.Ltext1)
        edittext2=findViewById(R.id.Ltext2)
        btnlogin=findViewById(R.id.btn1)
        textsignup=findViewById(R.id.textview2)

        btnlogin.setOnClickListener {
           userlogin()
        }
        textsignup.setOnClickListener {
            intent= Intent(this,Sign_up::class.java)
            startActivity(intent)
        }

    }

    private fun userlogin() {
        val email=edittext1.text.toString()
        val password=edittext2.text.toString()

        if (email.isBlank()){
            Toast.makeText(this,"email required",Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isBlank()){
            Toast.makeText(this,"password required",Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener (this){
            if (it.isSuccessful){
                intent = Intent(this,home::class.java)
                startActivity(intent)
                Toast.makeText(this,"signed up successfully",Toast.LENGTH_SHORT).show()
                finish()
            } else{
                Toast.makeText(this,"signed in failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}