package com.example.happyshop.utils

import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class RegularRadioButton(context:Context,attrs: AttributeSet) :AppCompatRadioButton(context,attrs) {

    init {
        applyFont()

    }
    fun applyFont(){

        val radioBtnTypeface :Typeface=Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        typeface=radioBtnTypeface


    }

}