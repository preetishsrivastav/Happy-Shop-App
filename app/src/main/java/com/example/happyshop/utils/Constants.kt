package com.example.happyshop.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

object Constants {
    const val USERS ="Users"
    const val HAPPY_SHOP_PREFERENCE ="HappyShopPreference"
    const val LOGGED_IN_USERNAME ="LoggedInUserName"
    const val USER_DETAILS="UserDetails"
    const val GALLERY_REQUEST_CODE= 100
    const val READ_EXTERNAL_STORAGE_CODE=101
    const val LOGGED_IN_SURNAME="LoggedInSurname"
    const val USER_EMAIL="userEmail"
    const val IMAGE="image"
    const val GENDER ="gender"
    const val MALE="male"
    const val FEMALE="female"
    const val PHONE="phone"
    const val USER_PROFILE_IMAGE="UserProfileImage"
    const val PROFILE_COMPLETED="profileCompleted"
    const val PRODUCT_IMAGE="ProductImage"
    const val PRODUCTS="Products"
    const val USER_ID="userId"
    const val PRODUCT_ID="productId"
    const val PRODUCT_OWNER_ID="productOwnerId"
    const val DEFAULT_CART_QUANTITY="1"
    const val CART_ITEMS="cart_items"


    fun showImageChooser(activity: Activity){
        val galleryIntent =Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }


}