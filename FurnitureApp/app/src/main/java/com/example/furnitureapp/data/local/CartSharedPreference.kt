package com.example.furnitureapp.data.local

import android.content.Context
import com.example.furnitureapp.services.fromJson
import com.example.furnitureapp.models.CartViewModel
import com.google.gson.Gson

class CartSharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "Cart"
    private val sharedPreferenceCart = "UserCart"

    fun saveCarts(carts: ArrayList<CartViewModel>) {
        val cartJson = Gson().toJson(carts)
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(sharedPreferenceCart, cartJson)
            apply()
        }
    }

    fun retrieveCarts(): ArrayList<CartViewModel> {
        val cartJson = context.getSharedPreferences(
            sharedPreferenceKey, Context.MODE_PRIVATE
        ).getString(sharedPreferenceCart, "")

        return if (cartJson.isNullOrEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson<ArrayList<CartViewModel>>(cartJson)
        }
    }

    fun addCart(productId: String): Boolean {
        val carts = retrieveCarts()

        for (i in carts) {
            if (i.ProductId == productId) {
                i.Quantity += 1
                return true
            }
        }
        return false
    }

    fun updateCart(productId: String, quantity: Int): Boolean {
        val carts = retrieveCarts()

        for (i in carts) {
            if (i.ProductId == productId) {
                i.Quantity = quantity
                return true
            }
        }
        return false
    }
}