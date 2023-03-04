package com.example.happyshop.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.User
import com.example.happyshop.utils.Constants

import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity() {
private lateinit var auth: FirebaseAuth
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
        setContentView(R.layout.activity_login)

        val register=findViewById<TextView>(R.id.tv_register)
        val loginBtn =findViewById<Button>(R.id.btn_login)
        val email = findViewById<EditText>(R.id.et_email)
        val password = findViewById<EditText>(R.id.et_password)
        val forgotPassword=findViewById<TextView>(R.id.tv_forgot_password)

        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))

        }

        register.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }

        loginBtn.setOnClickListener {
            if (validateForm(email, password)){
            loginUser()
            }
        }


    }

    private fun loginUser() {
         auth = FirebaseAuth.getInstance()
        val email = findViewById<EditText>(R.id.et_email)
        val password = findViewById<EditText>(R.id.et_password)
        val emailText = email.text.toString()
        val passwordText=password.text.toString()

            auth.signInWithEmailAndPassword(
                emailText, passwordText

            ).addOnCompleteListener { task ->


                if (task.isSuccessful) {
                    Firestore().getCurrentUserDetails(this)
                    Toast.makeText(this,"User Has been Logged In Successfully",Toast.LENGTH_SHORT).show()

                }


            else {
                Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT)
                    .show()
                Log.e("NOT ABLE TO LOGIN", task.exception!!.message.toString())
            }

        }
    }




    
    private fun validateForm(email:EditText,password:EditText):Boolean{

    return when {
        TextUtils.isEmpty(email.text.toString().trim{it<=' '})->{
            showErrorSnackBar("Please Enter Email",true)
            false

        }
        TextUtils.isEmpty(password.text.toString().trim { it<=' ' })-> {
            showErrorSnackBar("Please Enter Password",true)
            false
        }

        else ->{
//            showErrorSnackBar("Logged In",false)
            true
        }



    }

}

    fun getUser(user: User){
        if (user.profileCompleted==0) {
            val intent = Intent(this, UserProfileActivity::class.java)
            intent.putExtra(Constants.USER_DETAILS, user)
            startActivity(intent)
            finish()
        }
        else{
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}



