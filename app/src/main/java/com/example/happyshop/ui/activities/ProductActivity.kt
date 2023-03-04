package com.example.happyshop.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.Products
import com.example.happyshop.storage.FirebaseStorage
import com.example.happyshop.utils.Constants
import java.io.IOException

class ProductActivity :BaseActivity(), View.OnClickListener {

    private var mSelectedImageUri:Uri?=null
    private var mImageURL:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        val addUpdateProduct=findViewById<ImageView>(R.id.add_image_product)
        val btnSubmit=findViewById<Button>(R.id.btn_submit_product)
        setUpActionBar()

        addUpdateProduct.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

    }
    private fun setUpActionBar(){
        val productToolbar=findViewById<Toolbar>(R.id.product_toolbar)
        setSupportActionBar(productToolbar)

        val actionBar=supportActionBar

        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }
        productToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onClick(v: View?) {
          if(v!=null){
              when(v.id){
                  R.id.add_image_product->{
                      if (ContextCompat.checkSelfPermission(
                              this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                      {
                        Constants.showImageChooser(this)

                      }else{
                          ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),Constants.READ_EXTERNAL_STORAGE_CODE)

                      }

                  }
                  R.id.btn_submit_product->{
                      if (validateForm()){
                         uploadProductImage()
                      }


                  }

              }



          }

    }
    private fun uploadProductsToDb(){
        val userName=this.getSharedPreferences(Constants.HAPPY_SHOP_PREFERENCE, Context.MODE_PRIVATE)
            .getString(Constants.LOGGED_IN_USERNAME,"")
        val userId=Firestore().getCurrentUserId()
        val productTitle=findViewById<EditText>(R.id.et_product_title)
        val productDescription=findViewById<EditText>(R.id.et_product_description)
        val productPrice=findViewById<EditText>(R.id.et_product_price)
        val productQuantity=findViewById<EditText>(R.id.et_product_quantity)

        val productInfo=Products("",productTitle.text.toString(),productDescription.text.toString(),
        productPrice.text.toString(),productQuantity.text.toString().toLong(),mImageURL!!,userId,userName.toString())
        Firestore().registerProductsToDb(this,productInfo)



    }
    fun productUploadSuccess(){
        Toast.makeText(this,"Products Have Been Uploaded Successfully",Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun uploadProductImage(){
        if (mSelectedImageUri!=null){
//            showProgressDialog(this,"Please Wait")
            FirebaseStorage().uploadImageToStorage(this,mSelectedImageUri,Constants.PRODUCT_IMAGE)
        }


    }
    fun imageUploadSuccess(imageUrl:String){
//        hideProgressDialog()
//        showErrorSnackBar("Image Uploaded Successfully $imageUrl",false)
        mImageURL=imageUrl

        uploadProductsToDb()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==Constants.READ_EXTERNAL_STORAGE_CODE){
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Constants.showImageChooser(this)
            }else{
                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK){
            if (requestCode==Constants.GALLERY_REQUEST_CODE){

                if (data!=null) {
                    val productImage=findViewById<ImageView>(R.id.iv_product_image)
                    val addUpdateProduct=findViewById<ImageView>(R.id.add_image_product)
                    addUpdateProduct.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_edit))

                     mSelectedImageUri = data.data!!
                   try {
                       GlideLoader(this).imageLoader(mSelectedImageUri!!, productImage)
                   }catch (e:IOException){
                       e.printStackTrace()

                   }
                }


            }


        }

    }

    private fun validateForm():Boolean{

        val productTitle=findViewById<EditText>(R.id.et_product_title)
        val productDescription=findViewById<EditText>(R.id.et_product_description)
        val productPrice=findViewById<EditText>(R.id.et_product_price)
        val productQuantity=findViewById<EditText>(R.id.et_product_quantity)

        return when{

           mSelectedImageUri==null->{
               showErrorSnackBar("Please Select A product image",true)
               false
           }

            TextUtils.isEmpty(productPrice.text.toString())->{
                showErrorSnackBar("Please Enter Product Price",true)
                false
            }


            TextUtils.isEmpty(productTitle.text.toString())->{
                showErrorSnackBar("Please Enter Product Title",true)
                false
            }
            TextUtils.isEmpty(productDescription.text.toString())->{
                showErrorSnackBar("Please Enter Product Description",true)
                false
            }
            TextUtils.isEmpty(productPrice.text.toString())->{
                showErrorSnackBar("Please Enter Product Price",true)
                false
            }
            TextUtils.isEmpty(productQuantity.text.toString())->{
                showErrorSnackBar("Please Enter Product quantity",true)
                false
            }

            else->{
                true
            }

        }



    }

}