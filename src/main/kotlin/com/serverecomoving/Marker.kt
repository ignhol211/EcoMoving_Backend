package com.serverecomoving

import com.google.gson.Gson

class Marker(val latitude:Long) {

    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}