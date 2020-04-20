package com.example.furnitureapp.views.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.furnitureapp.R
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.ProductController
import com.example.furnitureapp.services.productData
import kotlinx.android.synthetic.main.fragment_purchase.view.*

/**
 * A simple [Fragment] subclass.
 */
class PurchaseFragment : Fragment() {
    val product =  ProductController()
    var currentProduct: Product? = null
    var id = ""
    var currentAmount =1
    var productIndex:Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_purchase, container, false)
        id = arguments?.getString("id").toString()
        findProduct(id)
//      UI Element
        var img = view.findViewById<View>(R.id.pur_img) as ImageView
        var increase = view.findViewById<View>(R.id.pur_increase)
        var amount = view.findViewById<View>(R.id.buy_amount) as EditText
        var decrease = view.findViewById<View>(R.id.pur_reduce)
        var next = view.findViewById<View>(R.id.purchase) as Button
        var cancel = view.findViewById<View>(R.id.cancel) as Button

////      set property

                img.setImageResource(productData[productIndex!!].image)
                view.pur_name.text = productData[productIndex!!].name
                view.pur_code.text = productData[productIndex!!].code
                view.pur_price.text = productData[productIndex!!].price.toString()



        increase.setOnClickListener {
            if (currentAmount.equals(productData[productIndex!!].available)){
                increase.isEnabled = false
            }else{
                currentAmount += 1
                decrease.isEnabled = true
                amount.setText(currentAmount.toString())
            }

        }

        decrease.setOnClickListener {
            if (currentAmount.equals(1)){
                decrease.isEnabled = false
            }else{
                increase.isEnabled = true
                currentAmount -= 1
                amount.setText(currentAmount.toString())
            }

        }

        next.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", id)
            bundle.putInt("amount", currentAmount)
            val confirmPurchase = ConfirmPurchaseFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            confirmPurchase.arguments = bundle
            fragmentTransaction.replace(R.id.frame_layout,confirmPurchase)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        cancel.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }

        return view
    }
    fun findProduct(id:String){
        for (i in 0 until productData.size){
            if (productData[i].id.equals(id)){
                productIndex = i
            }
        }
    }

}