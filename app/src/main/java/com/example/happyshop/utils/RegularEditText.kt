package com.example.happyshop.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class RegularEditText(context: Context,attributeSet: AttributeSet):AppCompatEditText(context,attributeSet) {
    init {
        applyFont()
    }

    private fun applyFont(){
        val editRegularTypeface: Typeface =Typeface.createFromAsset(context.assets,"Montserrat-Regular.ttf")
        typeface=editRegularTypeface

    }

}