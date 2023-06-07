package com.example.login_signup

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class home : AppCompatActivity() {


    private lateinit var logout:ImageButton
    private lateinit var text:TextView
    private lateinit var edit:EditText
    private lateinit var getbtn:Button
    private lateinit var textview1:TextView
    private lateinit var textview2:TextView
    private lateinit var textview3:TextView
    private lateinit var btndelete:Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    val user=FirebaseAuth.getInstance().currentUser

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        database = Firebase.database.reference
        auth=FirebaseAuth.getInstance()

        logout=findViewById(R.id.Imgbutton)
        btndelete=findViewById(R.id.btn1)
        text=findViewById(R.id.Htext)
        edit=findViewById(R.id.Hedittext)
        getbtn=findViewById(R.id.btn2)
        textview1=findViewById(R.id.Htext1)
        textview2=findViewById(R.id.Htext2)
        textview3=findViewById(R.id.Htext3)

        getbtn.setOnClickListener {
            val username : String=edit.text.toString()
            if (username.isNotEmpty()){
                getdata(username)
            }else{
                Toast.makeText(this,"please enter username",Toast.LENGTH_SHORT).show()
            }
        }




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

    private fun getdata(username: String) {
database = FirebaseDatabase.getInstance().getReference("users")
        database.child(username).get().addOnSuccessListener{

            if (it.exists()){
                val name=it.child("name").value
                val email=it.child("email").value
                val password=it.child("password").value
                Toast.makeText(this,"successfully get data",Toast.LENGTH_SHORT).show()
                edit.text.clear()
                textview1.text=name.toString()
                textview2.text=email.toString()
                textview3.text=password.toString()


            }else{
                Toast.makeText(this,"user does't exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
        }
    }
}