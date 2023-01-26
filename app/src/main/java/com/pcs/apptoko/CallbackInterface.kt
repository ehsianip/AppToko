package com.pcs.apptoko

import com.pcs.apptoko.response.cart.Cart

interface CallbackInterface {
    fun passResultCallback(total:String,cart:ArrayList<Cart>)
}