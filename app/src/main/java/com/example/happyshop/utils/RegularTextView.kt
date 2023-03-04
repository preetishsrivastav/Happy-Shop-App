package com.example.happyshop.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class RegularTextView(context: Context,attributeSet: AttributeSet):AppCompatTextView(context,attributeSet) {

    init {
        applyFont()
    }
    private fun applyFont(){
        val normalTypeface:Typeface= Typeface.createFromAsset(context.assets,"Montserrat-Regular.ttf")
        typeface=normalTypeface

    }
}