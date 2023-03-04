package com.example.happyshop.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.Products
import com.example.happyshop.ui.activities.ProductActivity
import com.example.happyshop.ui.adapters.RecyclerViewAdapter

class ProductFragment : Fragment() {

//    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       setHasOptionsMenu(true)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
          val root =inflater.inflate(R.layout.fragment_products,container,false)


        return root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_product->{
                startActivity(Intent(activity,ProductActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun gettingProductSuccessfully(productList:ArrayList<Products>){
        val textNoProductsAdded:TextView=activity!!.findViewById(R.id.tv_no_products)
        val rvItemList:RecyclerView=activity!!.findViewById(R.id.rv_my_product_list)

        if (productList.isNotEmpty()){
            textNoProductsAdded.visibility=View.GONE
            rvItemList.visibility=View.VISIBLE

            rvItemList.layoutManager=LinearLayoutManager(requireContext())
            rvItemList.setHasFixedSize(true)
            rvItemList.adapter= RecyclerViewAdapter(requireContext(),productList,this)
        }
        else{
            textNoProductsAdded.visibility=View.VISIBLE
            rvItemList.visibility=View.GONE

        }

    }

    override fun onResume() {
        super.onResume()

        Firestore().getProductListFromDb(this)
    }


   fun deletionOfProductSuccessfull(){
       Firestore().getProductListFromDb(this)
    Toast.makeText(requireContext(),"Product Has Been Deleted Successfully",Toast.LENGTH_SHORT).show()
   }

   fun deleteProduct(productId:String){
    showAlertDialog(productId)
   }

   fun showAlertDialog(productId: String){
       val builder=AlertDialog.Builder(requireActivity())

       builder.setTitle("Delete Product")
       builder.setMessage("Are You Sure You Want To Delete This Product")
       builder.setIcon(R.drawable.ic_alert)

       builder.setPositiveButton("Yes"){dialogInterFace, _->
          Firestore().deleteProduct(this,productId)
           dialogInterFace.dismiss()

       }
       builder.setNegativeButton("No"){dialogInterFace,_ ->
           dialogInterFace.dismiss()
       }

          val alertDialog:AlertDialog=builder.create()
          alertDialog.setCancelable(true)

          alertDialog.show()


   }
}