package com.EcoMovingServer.server

import com.google.gson.Gson
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(@Id val user:String, val password:String) {

    private var token:String? = null
    private val codeKey = user+password

    override fun toString():String{
        val gson = Gson()
        return gson.toJson(this)
    }

}