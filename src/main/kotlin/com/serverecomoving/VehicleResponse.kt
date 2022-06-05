package com.serverecomoving

import com.google.gson.Gson

data class VehicleResponse (val vehicles:List<Vehicle>) {
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}
