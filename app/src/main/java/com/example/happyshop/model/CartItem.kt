package com.example.happyshop.model

data class CartItem(
    val userID:String ="",
    val productId:String ="",
   val productName:String ="",
    val productImage:String = "",
    val productPrice:String = "",
    val cart_quantity:String ="",
    val stock_quantity:String=""
)