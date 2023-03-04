package com.example.happyshop.ui.activities

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.happyshop.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {


private var mDoubleBackPressed=false
private lateinit var mProgressDialog:Dialog

    fun showErrorSnackBar(message:String,error:Boolean){
        val snackBar =Snackbar.make(findViewById(android.R.id.content),message,1000)
        val snackView=snackBar.view
        if (error){
            snackView.setBackgroundColor(
                ContextCompat.getColor(this,R.color.err_snackbar_color)
            )
        }else{
            snackView.setBackgroundColor(
                ContextCompat.getColor(this,R.color.succ_snackbar_color)
            )
        }
        snackBar.show()

    }
    fun onBackPressedToExit(){

        if(mDoubleBackPressed){
            super.onBackPressed()
            return
        }
      this.mDoubleBackPressed=true
        Toast.makeText(this,"Press Back Button Once More To Exit",Toast.LENGTH_SHORT).show()
        Handler().postDelayed({this.mDoubleBackPressed=false}
            ,2000)

    }
    fun showProgressDialog(context:Context,message:String){
        mProgressDialog= Dialog(context)
        val tvProgressDialog=findViewById<TextView>(R.id.tv_progress_dialog)
        mProgressDialog.setContentView(R.layout.progress_dialog)
        tvProgressDialog.text=message
        mProgressDialog.setCancelable(false)

        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        mProgressDialog.hide()
    }
}
