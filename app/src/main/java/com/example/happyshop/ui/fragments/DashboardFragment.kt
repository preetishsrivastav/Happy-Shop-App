package com.example.happyshop.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happyshop.R
import com.example.happyshop.firestore.Firestore
import com.example.happyshop.model.Products
import com.example.happyshop.ui.adapters.DashBoardRecyclerViewAdapter
import com.example.happyshop.ui.activities.SettingActivity

class DashboardFragment : Fragment() {

//    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        Firestore().getDashBoardItemList(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root =inflater.inflate(R.layout.fragment_dashboard,container,false)


        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.settings_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id =item.itemId
        when(id){
            R.id.settings_menu ->{
                startActivity(Intent(activity, SettingActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    fun gettingDashBoardProductSuccessfully(productList:ArrayList<Products>){
        val textDashBoard:TextView=activity!!.findViewById(R.id.text_no_products_to_show)
        val rvDashBoard:RecyclerView=activity!!.findViewById(R.id.rv_dashboard_list)

        if (productList.isNotEmpty()){
            textDashBoard.visibility=View.GONE
            rvDashBoard.visibility=View.VISIBLE

            rvDashBoard.layoutManager= GridLayoutManager(requireContext(),2)
            rvDashBoard.setHasFixedSize(true)
            rvDashBoard.adapter= DashBoardRecyclerViewAdapter(requireContext(),productList)

        }else{
            textDashBoard.visibility=View.VISIBLE
            rvDashBoard.visibility=View.GONE
        }

    }


}