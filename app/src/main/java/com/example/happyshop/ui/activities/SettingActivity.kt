package com.example.happyshop.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.User
import com.google.firebase.auth.FirebaseAuth

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val btnLogout=findViewById<Button>(R.id.btn_logout)
        setupActionBar()
         btnLogout.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    private fun setupActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_settings_activity)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserDetails() {
        Firestore().getCurrentUserDetails(this)
    }

    fun userDetailsSuccess(user:User){
        val name =findViewById<TextView>(R.id.tv_name)
        val  gender=findViewById<TextView>(R.id.tv_gender)
        val  email=findViewById<TextView>(R.id.tv_email)
        val phone=findViewById<TextView>(R.id.tv_mobile_number)
        val  imageView= findViewById<ImageView>(R.id.iv_user_photo)

        GlideLoader(this).imageLoader(user.image,imageView)
        name.text="${user.firstName} ${user.lastName}"
        gender.text=user.gender
        email.text=user.email
        phone.text=user.phone.toString()
    }

    override fun onClick(view: View?) {
         if (view!=null){
             when(view.id){
                 R.id.btn_logout->{
                     FirebaseAuth.getInstance().signOut()
                     val intent=Intent(this,LoginActivity::class.java)
                     intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                     startActivity(intent)
                     finish()
                 }
                 R.id.tv_edit->{
                     TODO("DO what required ")

                 }


             }





         }



    }


}