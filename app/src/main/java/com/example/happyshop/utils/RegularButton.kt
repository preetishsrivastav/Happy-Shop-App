package com.example.happyshop.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.happyshop.R

class RegularButton(context: Context,attributeSet: AttributeSet):AppCompatButton(context,attributeSet) {
init {
    applyFont()
}
    private fun applyFont()
    {
        val typeFace:Typeface=Typeface.createFromAsset(context.assets,"Montserrat-Regular.ttf")
        typeface=typeFace }


}