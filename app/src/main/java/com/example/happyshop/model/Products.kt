package com.example.happyshop.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Products (
    var productId:String="",
    val productTitle:String="",
    val productDescription:String="",
    val productPrice:String="",
    val productQuantity:Long=0,
    val productImage:String="",
    val userId:String="",
    val userName:String=""
    ):Parcelable