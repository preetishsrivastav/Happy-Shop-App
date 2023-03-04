package com.example.happyshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
       val id:String ="",
       val firstName:String="",
       val lastName:String="",
       val email:String="",
       val image:String="",
       val phone:Long= 0,
       val gender : String ="",
       val profileCompleted:Int =0
):Parcelable