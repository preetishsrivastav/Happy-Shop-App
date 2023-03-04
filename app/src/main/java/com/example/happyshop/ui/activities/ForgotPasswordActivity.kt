package com.example.happyshop.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.happyshop.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setContentView(R.layout.activity_forgot_password)
        setupActionBar()

//        FINDING VIEWS
        val btnSubmit =findViewById<Button>(R.id.btn_submit)

//        Clicking On Submit Button
        btnSubmit.setOnClickListener {

            forgetPassword()

        }



    }

    private fun forgetPassword(){
        val email = findViewById<EditText>(R.id.et_email)
        val emailText=email.text.toString().trim { it<=' ' }
        if (emailText.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(emailText)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Open Your Email to Reset Your Password",
                            Toast.LENGTH_LONG
                        )
                            .show()

                    } else {
                        Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_LONG)
                            .show()
                    }

                }
        }else{
            Toast.makeText(this ,"Please Enter Your Email",Toast.LENGTH_SHORT).show()

        }

    }

    private fun setupActionBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar_forgot_password_activity)
        setSupportActionBar(toolbar)

        val actionBar =supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}