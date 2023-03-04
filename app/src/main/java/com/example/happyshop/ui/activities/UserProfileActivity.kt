package com.example.happyshop.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.User
import com.example.happyshop.storage.FirebaseStorage
import com.example.happyshop.utils.Constants
import java.io.IOException

class UserProfileActivity : BaseActivity() {
    private lateinit var mUserDetails: User
    var galleryImageUri: Uri?=null
    private lateinit var mImageUrl:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


//        val sharedPreferences=getSharedPreferences(Constants.HAPPY_SHOP_PREFERENCE,Context.MODE_PRIVATE)
         val firstName =findViewById<EditText>(R.id.et_first_name)
        val lastName =findViewById<EditText>(R.id.et_last_name)
        val email =findViewById<EditText>(R.id.et_email)
        val ivPerson=findViewById<ImageView>(R.id.iv_user_photo)
        val btnSubmit=findViewById<Button>(R.id.btn_submit)



        firstName.isEnabled=false
        lastName.isEnabled=false
        email.isEnabled=false

//        firstName.setText(sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,""))
//        lastName.setText(sharedPreferences.getString(Constants.LOGGED_IN_SURNAME,""))
//        email.setText(sharedPreferences.getString(Constants.USER_EMAIL,""))




     if (intent.hasExtra(Constants.USER_DETAILS)){
       mUserDetails= intent.getParcelableExtra(Constants.USER_DETAILS)!!

     }
        firstName.setText(mUserDetails.firstName)
        lastName.setText(mUserDetails.lastName)
        email.setText(mUserDetails.email)

//        Creating an Hash Map To store updated user Details






        ivPerson.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this)

            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_EXTERNAL_STORAGE_CODE
                )

            }
        }
        btnSubmit.setOnClickListener {

           if(validateForm()){
               if (galleryImageUri!=null){
                   FirebaseStorage().uploadImageToStorage(this,galleryImageUri,Constants.USER_PROFILE_IMAGE)
               }
               else {
                  updateUser()
               }

            }

        }




    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==Constants.READ_EXTERNAL_STORAGE_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this)
            }else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (resultCode==Activity.RESULT_OK){
           if (requestCode==Constants.GALLERY_REQUEST_CODE){
               if (data!=null) {
                   try {
                      galleryImageUri = data.data!!
                       val ivPerson = findViewById<ImageView>(R.id.iv_user_photo)
                        if (galleryImageUri!=null) {
                            GlideLoader(this).imageLoader(galleryImageUri!!, ivPerson)
                        }
                   }catch (e:IOException){
                       e.printStackTrace()
                       Toast.makeText(this,"Image Selection Failed",Toast.LENGTH_SHORT).show()
                   }
               }
           }


       }else if (resultCode==Activity.RESULT_CANCELED){
             Log.e("Request Cancelled","")

       }
    }
    private fun validateForm():Boolean{
        val phone =findViewById<EditText>(R.id.et_mobile_number)
        return if (phone.text.isEmpty() ||
            phone.text.toString().length !=10){
            showErrorSnackBar("Please Enter A Valid Phone No. ",true)
            false
        }else if (galleryImageUri == null) {
            showErrorSnackBar("Please Enter the image",true)
            false
        }
        else{
           true
        }
    }
    fun updateUser(){
        val profileCompleted=1
        val rbMale = findViewById<RadioButton>(R.id.rb_male)
        val phone = findViewById<EditText>(R.id.et_mobile_number)
        val phoneText = phone.text.toString()

        val hashMap = HashMap<String, Any>()

        val gender = if (rbMale.isChecked) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }

        //        Creating Key Value Pair Of User Details to Store in FireStore
        hashMap[Constants.PHONE] = phoneText.toLong()
        hashMap[Constants.GENDER] = gender
        if (mImageUrl.isNotEmpty()) {
            hashMap[Constants.IMAGE] = mImageUrl
        }
        hashMap[Constants.PROFILE_COMPLETED]=profileCompleted

        Firestore().updateUser(this, hashMap)


    }

    fun updateUserSuccess(){
        Toast.makeText(this,"User Details has been updated successfully",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun imageUploadSuccess(imageUrl:String){
//        Toast.makeText(this,"Image Uploaded to Storage Successfully $imageUrl ",Toast.LENGTH_SHORT).show()
        mImageUrl=imageUrl
        updateUser()
    }

}