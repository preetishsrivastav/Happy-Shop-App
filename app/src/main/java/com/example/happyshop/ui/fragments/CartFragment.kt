package com.example.happyshop.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.happyshop.R

class CartFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val root= inflater.inflate(R.layout.fragment_cart, container, false)
        val tvCart=root.findViewById<TextView>(R.id.tv_cart_fragment)
        tvCart.text= resources.getString(R.id.tv_cart_fragment)
        return root
    }


}