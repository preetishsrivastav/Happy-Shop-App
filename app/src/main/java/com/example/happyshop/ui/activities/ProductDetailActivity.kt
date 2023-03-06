package com.example.happyshop.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.CartItem
import com.example.happyshop.model.Products
import com.example.happyshop.ui.fragments.CartFragment
import com.example.happyshop.utils.Constants

class ProductDetailActivity : AppCompatActivity(),View.OnClickListener {
 private  lateinit var userId:String
 private lateinit var productOwnerId:String
 private lateinit var mProductDetail:Products
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
        btnAddToCart.setOnClickListener(this)
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
        mProductDetail=products

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

   private fun addToCart(){
        val cartItem=CartItem(userId,mProductDetail.productId,mProductDetail.productTitle,mProductDetail.productImage
        ,mProductDetail.productPrice,Constants.DEFAULT_CART_QUANTITY,mProductDetail.productQuantity.toString()
        )
       Firestore().addToCart(this,cartItem)
    }
    fun addToCartSuccess(){
        Toast.makeText(this,"Product Has Been Added TO The CART",Toast.LENGTH_SHORT).show()
//        Intent(this,CartFragment::class.java)
    }


    override fun onClick(v: View?) {
        if (v!=null){
            when(v.id){
                R.id.btn_add_to_cart->{
                    addToCart()
                }


            }



        }
    }
}