package com.example.login_signup

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserHandle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.lang.Exception

class home : AppCompatActivity()  {

    lateinit var togggle:ActionBarDrawerToggle



    private lateinit var drawer:ImageButton
    private lateinit var text:TextView
    private lateinit var edit:EditText
    private lateinit var getbtn:Button
    private lateinit var textview1:TextView
    private lateinit var textview2:TextView
    private lateinit var textview3:TextView
    private lateinit var textview4:TextView
    private lateinit var textview5:TextView
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: user
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var uid: String
   // private lateinit var storageReference: DatabaseReference
    private lateinit var username: TextView
    private lateinit var useremail: TextView
    private lateinit var uname: String
    private lateinit var profileimage: CircleImageView
//    private lateinit var storageReference:FirebaseStorage



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        database = FirebaseDatabase.getInstance().getReference("users")
        auth=FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()


        drawer=findViewById(R.id.drawerbutton)
        text=findViewById(R.id.Htext)
        edit=findViewById(R.id.Hedittext)
        getbtn=findViewById(R.id.btn2)
        textview1=findViewById(R.id.Htext1)
        textview2=findViewById(R.id.Htext2)
        textview3=findViewById(R.id.Htext3)
        textview4=findViewById(R.id.Htext4)
        textview4=findViewById(R.id.Htext5)


        drawerLayout  = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navView.getHeaderView(0)
        username=headerView.findViewById(R.id.user_name)
        useremail=headerView.findViewById(R.id.user_email)
        profileimage=headerView.findViewById(R.id.profileimg)



        if (uid.isNotEmpty()){
            getUserData()
            getUserProfile()
        }


        togggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open,R.string.close)
        drawerLayout.addDrawerListener(togggle)
        togggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_home -> {

                }
                R.id.nav_message -> {
                    intent = Intent(this,send_message::class.java)
                    startActivity(intent)
                }

                R.id.nav_sync -> Toast.makeText(this, "Clicked sync", Toast.LENGTH_SHORT).show()
                R.id.nav_delete -> {
                    if (user != null) {
                        user.delete()
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                R.id.nav_setting -> {
                    intent = Intent(this,profile::class.java)
                    startActivity(intent)
                }

                R.id.nav_logout ->  {
                    auth.signOut()
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)}

                R.id.nav_share -> Toast.makeText(this, "Clicked share", Toast.LENGTH_SHORT).show()
                R.id.nav_rate -> Toast.makeText(this, "Clicked rate", Toast.LENGTH_SHORT).show()
            }
            true

        }

        getbtn.setOnClickListener {
            val username : String=edit.text.toString()
            if (username.isNotEmpty()){
                getdata(username)
            }else{
                Toast.makeText(this,"please enter username",Toast.LENGTH_SHORT).show()
            }
        }

        drawer.setOnClickListener {
         if (! drawerLayout.isDrawerOpen(GravityCompat.START)){
         drawerLayout.openDrawer(GravityCompat.START)
         }else{
             drawerLayout.closeDrawer(GravityCompat.END)
         }

        }



    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(uid).get().addOnSuccessListener{

            if (it.exists()){
                try {
                    val name = it.child("name").value
                    val email = it.child("email").value
                   // val password = it.child("imageUrl").value
                    Toast.makeText(this, "successfully get data", Toast.LENGTH_SHORT).show()
                    edit.text.clear()
                    username.text = username.toString()
                    useremail.text = useremail.toString()
                   // profileimage. = profileimage.toString()
                }catch (e:Exception){

                }

            }else{
                Toast.makeText(this,"user does't exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserProfile() {
//       storageReference.reference.child("profile/ $uid")
//        val localFile = File.createTempFile("tempimage","jpg")
//        storageReference.reference.getFile(localFile).addOnSuccessListener {
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            profileimage.setImageBitmap(bitmap)
//        }.addOnFailureListener{
//            Toast.makeText(this,"Failed to Retrive Image",Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (togggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

    private fun getdata(username: String) {
database = FirebaseDatabase.getInstance().getReference("users")
        database.child(username).get().addOnSuccessListener{

            if (it.exists()){
                try {
                    val name = it.child("name").value
                    val email = it.child("email").value
                    val password = it.child("password").value
                    val age = it.child("age").value
                    val gender = it.child("gender").value
                    Toast.makeText(this, "successfully get data", Toast.LENGTH_SHORT).show()
                    edit.text.clear()
                    textview1.text = name.toString()
                    textview2.text = email.toString()
                    textview3.text = password.toString()
                    textview4.text = age.toString()
                    textview5.text = gender.toString()
                }catch (e:Exception){

                }

            }else{
                Toast.makeText(this,"user does't exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            ActivityCompat.finishAffinity(this)
            finish()
        }
    }

}