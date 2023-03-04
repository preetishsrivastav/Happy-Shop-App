package com.example.happyshop.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.happyshop.R

class OrderFragment : Fragment() {

//    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val notificationsViewModel =
//            ViewModelProvider(this).get(NotificationsViewModel::class.java)

//        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        val root =inflater.inflate(R.layout.fragment_orders,container,false)

        val textView: TextView = root.findViewById(R.id.text_orders)
        textView.text="This is Orders Fragment"

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}