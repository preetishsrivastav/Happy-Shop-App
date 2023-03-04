package com.example.happyshop.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.happyshop.R
import com.example.happyshop.model.Products
import com.example.happyshop.ui.activities.GlideLoader
import com.example.happyshop.ui.activities.ProductDetailActivity
import com.example.happyshop.utils.Constants

open class DashBoardRecyclerViewAdapter(val context: Context,val list:ArrayList<Products>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rv_dashboard_list_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model=list[position]
        if (holder is MyViewHolder){
            holder.dashBoardItemName.text=model.productTitle
            holder.dashBoardItemPrice.text=model.productPrice
            GlideLoader(context).imageProductLoader(model.productImage,holder.dashBoardItemImage)

            holder.itemView.setOnClickListener {
                val intent= Intent(context, ProductDetailActivity::class.java)
                intent.putExtra(Constants.PRODUCT_ID,model.productId)
                intent.putExtra(Constants.PRODUCT_OWNER_ID,model.userId)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val dashBoardItemImage:ImageView=view.findViewById(R.id.iv_dashboard_item_image)
        val dashBoardItemName:TextView=view.findViewById(R.id.tv_dashboard_item_title)
        val dashBoardItemPrice:TextView=view.findViewById(R.id.tv_dashboard_item_price)
    }
}