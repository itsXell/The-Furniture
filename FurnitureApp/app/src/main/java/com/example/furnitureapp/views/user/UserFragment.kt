package com.example.furnitureapp.views.user


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.furnitureapp.views.main.MainActivity
import com.example.furnitureapp.R
import com.example.furnitureapp.data.local.CartSharedPreference
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.services.AlertBuilder
import kotlinx.android.synthetic.main.yes_no_dialog.*
import kotlinx.android.synthetic.main.yes_no_dialog.view.*
import kotlinx.android.synthetic.main.yes_no_dialog.view.yes_btn

//import com.example.furnitureapp.isLogin

/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        MainActivity.pageId = R.id.user
        val logout = view.findViewById<View>(R.id.logout)
        val account = view.findViewById<View>(R.id.user_account) as EditText
        var purchaseList = view.findViewById<View>(R.id.user_purchase_list) as EditText
        var about = view.findViewById<View>(R.id.about) as EditText


        // Account Button
        account.setOnClickListener {
            MainActivity.pageId = R.id.user_account
            val setting = UserSettingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, setting)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        //Purchase List
        purchaseList.setOnClickListener {
            val purchaseListFragment = UserPurchaseListFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.frame_layout, purchaseListFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        //Logout Button
        logout.setOnClickListener{
            val alertBuilder = AlertBuilder()
            val alert = alertBuilder.showYesNoAlert("Logout", getString(R.string.logout_confirm))
            alert?.yes_btn?.setOnClickListener {
                UserSharedPreference(MainActivity.mainThis).logOut()
                CartSharedPreference(MainActivity.mainThis).clearCart()
                val login = LoginFragment()
                val  fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_layout, login)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                alertBuilder.dismiss()
            }


        }

        return view
    }


}
