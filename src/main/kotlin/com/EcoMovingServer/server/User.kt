package com.EcoMovingServer.server

import com.google.gson.Gson
import com.google.gson.JsonArray
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(val password:String,@Id val email:String) {

    var token:String? = null
    val codeKey = email
    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

}