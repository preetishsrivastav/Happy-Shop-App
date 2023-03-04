package com.example.happyshop.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.Products
import com.example.happyshop.utils.Constants

class ProductDetailActivity : AppCompatActivity() {
 private  lateinit var userId:String
 private lateinit var productOwnerId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setUpActionBar()


       val btnAddToCart:Button=findViewById(R.id.btn_add_to_cart)

        if (intent.hasExtra(Constants.PRODUCT_ID)){
            val productId= intent.getStringExtra(Constants.PRODUCT_ID)!!
            Firestore().getProductDetails(this,productId)
        }
        if (intent.hasExtra(Constants.PRODUCT_OWNER_ID)){
             productOwnerId= intent.getStringExtra(Constants.PRODUCT_OWNER_ID)!!
           userId = Firestore().getCurrentUserId()

        }
        if (productOwnerId == userId){
            btnAddToCart.visibility= View.GONE
        }

    }

// function to setup action bar
private fun setUpActionBar(){
        val toolbarProduct=findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_product_details_activity)
        setSupportActionBar(toolbarProduct)

       val actionBar= supportActionBar
       if ( actionBar!= null){

           actionBar.setDisplayHomeAsUpEnabled(true)
           actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

       }
        toolbarProduct.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    fun productReceivedSuccessfully(products: Products){
        val productDetailTitle= findViewById<TextView>(R.id.tv_product_details_title)
        val productDetailPrice=findViewById<TextView>(R.id.tv_product_details_price)
        val productDetailDes=findViewById<TextView>(R.id.tv_product_details_description)
        val ivProduct=findViewById<ImageView>(R.id.iv_product_detail_image)
        val stock=findViewById<TextView>(R.id.tv_product_details_available_quantity)

        GlideLoader(this).imageProductLoader(products.productImage,ivProduct)
        productDetailTitle.text=products.productTitle
        productDetailPrice.text=products.productPrice
        productDetailDes.text=products.productDescription
        stock.text=products.productQuantity.toString()
    }
}