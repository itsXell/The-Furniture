package com.example.furnitureapp.Cart


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.Purchase.ConfirmPurchaseFragment
import com.example.furnitureapp.User.LoginFragment
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.CategoriesController
import com.example.furnitureapp.models.ProductController
import com.example.furnitureapp.models.Product

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    var singleton = ProductController()
    var currentKey:String = ""
    var cartProduct = ArrayList<Product>()
    var categories = CategoriesController()
//    lateinit var recyclerView: RecyclerView
//    lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //TODO
        getAllSharePref()
//        e("global: ", getSharePref("t1"))
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val arrayKey = currentKey.split(",")
        val listOfProduct = view.findViewById<RecyclerView>(R.id.recycler_view_cart) as RecyclerView
        val placeOrder = view.findViewById<View>(R.id.place_order)


        for (i in 0 until arrayKey.size-1){
            for (j in productData){
                if (arrayKey[i].substring(0,2) == j.id){
                    if (!cartProduct.contains(j)){
                        cartProduct.add(j)
                        e("cart product is:", j.name ?: "" )
                    }
                }
            }
        }
        var adapter = CartAdapter(cartProduct,this)

        listOfProduct.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)
        listOfProduct.adapter =
            adapter


        placeOrder.setOnClickListener {
            if (!UserSharedPreference(MainActivity.mainThis).isLogged()){
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Please Login Before Make Purchase")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                    val login = LoginFragment()
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, login)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()

                }
                builder.show()
            }else{
                val bundle = Bundle()
                bundle.putStringArrayList("cart product",adapter.selectProductCode)
                bundle.putIntegerArrayList("cart amount", adapter.selectProductAmount)
                val confirmPurchase = ConfirmPurchaseFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                confirmPurchase.arguments = bundle
                fragmentTransaction.replace(R.id.frame_layout,confirmPurchase)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

        }

        return view
    }



    fun getSharePref(name: String): String {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.apply()
        return sharedPref?.getString(name, null).toString()
    }
    fun getAllSharePref() {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        var key = sharedPref?.all

        if (key != null) {
            for (entry in key) {
                currentKey += "$entry,"
                e("global is", currentKey)
            }
        }
    }



//    fun refreshAdapter() {
//        adapter = CartAdapter(cater, R.layout., dateList, selectIndex)
//        countryListView.adapter = adapter
//        adapter.notifyDataSetChanged()
//    }



}
