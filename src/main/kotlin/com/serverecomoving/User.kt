package com.serverecomoving

import com.google.gson.Gson
import com.google.gson.JsonArray
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(@Id val email:String, val password:String) {

    var token:String? = null
    val codeKey: String = email
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

}