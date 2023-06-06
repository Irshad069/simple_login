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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Sign_up : AppCompatActivity() {

    private lateinit var text1:EditText
    private lateinit var text2:EditText
    private lateinit var text3:EditText
    private lateinit var btnregister:Button
    private lateinit var Tsignin:TextView

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    var user=FirebaseAuth.getInstance().currentUser

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth= FirebaseAuth.getInstance()

        text1=findViewById(R.id.Stext1)
        text2=findViewById(R.id.Stext2)
        text3=findViewById(R.id.Stext3)
        btnregister=findViewById(R.id.Sbtn1)
        Tsignin=findViewById(R.id.textview2)


        btnregister.setOnClickListener {
           usersignup()
        }

        Tsignin.setOnClickListener {
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun usersignup() {
        var name=text1.text.toString()
        var email=text2.text.toString()
        var password=text3.text.toString()
        if (name.isBlank()){
            Toast.makeText(this,"name required",Toast.LENGTH_SHORT).show()
            return
        }
        if (email.isBlank()){
            Toast.makeText(this,"email required",Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isBlank()){
            Toast.makeText(this,"password required",Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener (this){
            if (it.isSuccessful){
                intent= Intent(this,home::class.java)
                startActivity(intent)

                database= FirebaseDatabase.getInstance().getReference("users")
                val user=user(name, email, password)
                database.child(name).setValue(user).addOnSuccessListener {
                    text1.text.clear()
                    text2.text.clear()
                    text3.text.clear()
                    Toast.makeText(this,"data saved",Toast.LENGTH_SHORT).show()
                } .addOnFailureListener {
                    Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
                }
            }
        }

