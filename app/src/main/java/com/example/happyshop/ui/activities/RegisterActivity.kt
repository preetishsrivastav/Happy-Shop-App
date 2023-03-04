package com.example.happyshop.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : BaseActivity() {
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

        setContentView(R.layout.activity_register)


        setupActionBar()
// Getting views
        val login =findViewById<TextView>(R.id.tv_login)
        val firstName=findViewById<EditText>(R.id.et_first_name)
        val lastName=findViewById<EditText>(R.id.et_last_name)
        val email=findViewById<EditText>(R.id.et_email)
        val password =findViewById<EditText>(R.id.et_password)
        val confirmPassword =findViewById<EditText>(R.id.et_confirm_password)
        val checkBox =findViewById<CheckBox>(R.id.cb_terms_and_condition)
        val registerBtn =findViewById<Button>(R.id.btn_register)




        registerBtn.setOnClickListener {
            val isValidate =validateForm(firstName, lastName, email, password, confirmPassword, checkBox)
            if(isValidate){

                registerUser()
            }
        }


        login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(
    ) {

        auth=FirebaseAuth.getInstance()
        val email=findViewById<EditText>(R.id.et_email)
        val password =findViewById<EditText>(R.id.et_password)
        val firstName=findViewById<EditText>(R.id.et_first_name)
        val lastName=findViewById<EditText>(R.id.et_last_name)


        val emailText= email.text.toString()
        val passwordText =password.text.toString()


        auth.createUserWithEmailAndPassword(
            emailText,
            passwordText
        ).addOnCompleteListener { task ->

            if (task.isSuccessful){

                val firebaseUser: FirebaseUser = task.result!!.user!!
                val user =User(
                    firebaseUser.uid,firstName.text.toString().trim { it<=' ' },
                    lastName.text.toString().trim { it<=' ' },emailText.trim { it<=' ' }
                )

                Firestore().registerUserToDB(user)

                Toast.makeText(this, "User Has Been Registered Successfully", Toast.LENGTH_SHORT).show()

                val intent =Intent(this, MainActivity::class.java)
                intent.putExtra("user_id",firebaseUser.uid)
                intent.putExtra("email_id",emailText)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(
                    this, task.exception!!.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }

    private fun setupActionBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar_register_activity)
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

    override fun onBackPressed() {
        val intent =Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

private fun validateForm(firstName:EditText,lastName:EditText,email:EditText
                         ,password:EditText,confirmPassword:EditText,checkBox: CheckBox):Boolean
 {
     return when {
         TextUtils.isEmpty(firstName.text.toString().trim{ it <= ' '}) ->{
             showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name),true)
             false
         }
         TextUtils.isEmpty(lastName.text.toString().trim{ it <= ' '}) ->{
             showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name),true)
             false
         }
         TextUtils.isEmpty(email.text.toString().trim{ it <= ' '}) ->{
             showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
             false
         }
         TextUtils.isEmpty(password.text.toString().trim{ it <= ' '}) ->{
             showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
             false
         }
         TextUtils.isEmpty(confirmPassword.text.toString().trim{ it <= ' '}) ->{
             showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password),true)
             false
         }
         password.text.toString().trim{it <= ' '} != confirmPassword.text.toString().trim{it <= ' '} ->{
             showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),true)
             false

         }
         !checkBox.isChecked ->{
             showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition),true)
             false
         }
         else ->{
//             showErrorSnackBar("Thanks for Registering",false)
                true
         }

     }


 }

}