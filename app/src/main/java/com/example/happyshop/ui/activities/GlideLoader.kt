package com.example.happyshop.ui.activities

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.happyshop.R
import java.io.IOException

class GlideLoader(val context: Context) {

    fun imageLoader(image:Any,imageView: ImageView){
try {
    Glide.with(context)
        .load(image)
        .centerCrop()
        .placeholder(R.drawable.ic_user_placeholder)
        .into(imageView)

}catch (e:IOException){
    e.printStackTrace()

}

    }

    fun imageProductLoader(image:Any,imageView: ImageView){
        try {
            Glide.with(context).
            load(image).
            centerCrop().
            into(imageView)
        }catch (e:IOException){
            e.printStackTrace()
        }


    }



}