package com.example.furnitureapp.User

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.furnitureapp.R
import com.example.furnitureapp.allUser
import com.example.furnitureapp.data.api.UserApi
import kotlinx.android.synthetic.main.fragment_create_new.*
import kotlinx.android.synthetic.main.fragment_phone_number.view.*

/**
 * A simple [Fragment] subclass.
 */
class PhoneNumberFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_phone_number, container, false)
        val newPhoneNumber = view.findViewById<View>(R.id.phone_setting_new_phone) as EditText
        val newVerification = view.findViewById<View>(R.id.phone_setting_password) as EditText
        val random = ((Math.random() * 9000) + 1000).toInt()

        view.verification_code.setOnClickListener {
            val builder = AlertDialog.Builder(this.activity)
            builder.setTitle("Verification Code is $random")
            builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int -> }
            builder.show()
        }
        view.phone_setting_back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }
        view.phone_save.setOnClickListener {
            val builder = AlertDialog.Builder(this.activity)
            if (newPhoneNumber.text.toString().length != 10) {
                builder.setTitle("Invalid Phone Number")
                builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int -> }
            } else {
                if (newVerification.text.toString() == random.toString()) {
                    UserApi().updateTelephoneNumber(newPhoneNumber.text.toString()) {
                        if (it) {
                            builder.setTitle("Success")
                            builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int ->
                                val fragment = activity!!.supportFragmentManager
                                fragment.popBackStack()
                            }
                        } else {
                            builder.setTitle("Error Occurred")
                            builder.setPositiveButton("Okay") {_: DialogInterface, _: Int -> }
                        }
                    }
                } else {
                    builder.setTitle("Wrong Verification Code")
                    builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int -> }
                }
            }
            builder.show()
        }
        return view

    }


}