package com.example.happyshop.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.happyshop.R
import com.example.happyshop.model.Products
import com.example.happyshop.ui.activities.GlideLoader
import com.example.happyshop.ui.activities.ProductDetailActivity
import com.example.happyshop.ui.fragments.ProductFragment
import com.example.happyshop.utils.Constants

open class RecyclerViewAdapter(private val context: Context,private val list:ArrayList<Products>,private val fragment: ProductFragment):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
             return MyViewHolder(
               LayoutInflater.from(context).inflate(R.layout.rv_item_list,parent,false)
             )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model=list[position]

        if (holder is MyViewHolder){
            GlideLoader(context).imageProductLoader(model.productImage,holder.imageItem)
            holder.itemName.text=model.productTitle
            holder.itemPrice.text="$${model.productPrice}"


            holder.ibDelete.setOnClickListener {
               fragment.deleteProduct(model.productId)
            }

            holder.itemView.setOnClickListener {
                val intent=Intent(context,ProductDetailActivity::class.java)
                intent.putExtra(Constants.PRODUCT_ID,model.productId)
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

  inner  class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val imageItem:ImageView= view.findViewById(R.id.iv_item_image)
        val itemName:TextView=view.findViewById(R.id.rv_item_name)
        val itemPrice:TextView=view.findViewById(R.id.rv_item_price)
        val ibDelete:ImageButton=view.findViewById(R.id.ib_delete_product)
    }
}