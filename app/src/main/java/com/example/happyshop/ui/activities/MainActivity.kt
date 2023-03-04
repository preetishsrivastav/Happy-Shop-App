package com.example.happyshop.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.happyshop.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userName =intent.getStringExtra("user_name")
        val uid = intent.getStringExtra("user_id")
        val email =intent.getStringExtra("email_id")


        val tvUserName =findViewById<TextView>(R.id.user_name)
        val tvUserId =findViewById<TextView>(R.id.user_id)
        val tvEmail =findViewById<TextView>(R.id.tv_email)
        val btnLogout=findViewById<Button>(R.id.btn_logout)
        tvUserName.text=userName
        tvUserId.text=uid
        tvEmail.text=email

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }



    }
}