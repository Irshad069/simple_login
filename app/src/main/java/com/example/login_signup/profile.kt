package com.example.login_signup

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Date

class profile : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var button: Button
    private lateinit var userImage:CircleImageView
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var databaseReference: DatabaseReference
    private lateinit var selectedimg:Uri
    private lateinit var dialog: AlertDialog.Builder
    var user=FirebaseAuth.getInstance().currentUser

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        name = findViewById(R.id.profile_edittext)
        button = findViewById(R.id.continue1)
        userImage = findViewById(R.id.user_image)
        dialog = AlertDialog.Builder(this)
            .setMessage("Updating Profile")
            .setCancelable(false)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        userImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type="image/*"
            startActivityForResult(intent,1)
        }

        button.setOnClickListener {
            if (name.text. isEmpty()){
                Toast.makeText(this,"Please Enter Your Name",Toast.LENGTH_SHORT).show()
            }else if (selectedimg == null){
                Toast.makeText(this,"Please Select Your Image",Toast.LENGTH_SHORT).show()
            }else uploadData()

        }

    }

    private fun uploadData() {
        val reference = storage.reference.child("profile").child(Date().time.toString())
        reference.putFile(selectedimg).addOnCompleteListener{
            if (it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {

        val user = user(auth.uid,name.text.toString(),imgUrl)

        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Data Inserted",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,home::class.java))
                finish()
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null){
            if (data.data != null){
                selectedimg = data.data!!
                userImage.setImageURI(selectedimg)
            }
        }
    }
}