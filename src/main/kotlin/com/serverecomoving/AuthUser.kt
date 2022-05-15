package com.serverecomoving

import com.google.gson.Gson

class AuthUser(user: User) {

    var email:String
    var token:String?

    init {
        this.email = user.email
        this.token = user.token
    }

    override fun toString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

}