package com.serverecomoving

import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


@RestController
class Controller (private val userRepository : UsersRepository) {

    @GetMapping("aaa")
    fun index():List<User>{
        return userRepository.findAll()
    }

    @PostMapping("register")
    @ResponseBody
    fun register(@RequestParam request:User): Boolean {
        userRepository.save(request)
        return true
    }

    @PostMapping("login")
    fun login(@RequestBody request: User): AuthUser?{
        return if(validateUserViaPassword(request.email,request.password, userRepository)){
            val token = getRandomToken()
            val user = userRepository.getById(request.email)
            user.token = token
            userRepository.save(user)
            val authUser = AuthUser(user)
            authUser
        }else
            null
    }
    @PostMapping("getVehicles")
    fun getVehicles(@RequestBody authUserToken:String): VehicleResponse? {
        return if(validateUserViaToken(authUserToken,userRepository)){
            VehicleResponse(VehicleRepository.vehicles)
        }else{
            null
        }
    }
}
private fun getRandomToken():String{
    var token = ""
    repeat(15){
        token += (0..9).random()
        token += ('a' .. 'z').random()
    }
    return token
}
private fun validateUserViaPassword(email:String,password: String, userRepository : UsersRepository):Boolean{
    val possibleUser = userRepository.findById(email)
    return if(possibleUser.isPresent){
        val user = userRepository.getById(email)
        return user.password == password
    }else
        false
}

private fun validateUserViaToken(token: String, userRepository : UsersRepository):Boolean{
    println(token)
    val possibleUser = userRepository.findByToken(token)
    println(possibleUser.isPresent)
    return possibleUser.isPresent
}

private fun encode(text: String, key: String): String {
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, getKey(key))
    val encodedText = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
    return Base64.getUrlEncoder().encodeToString(encodedText)
}

private fun decode(text: String, key: String): String {
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, getKey(key))
    val decodedText = Base64.getUrlDecoder().decode(text)
    return String(cipher.doFinal(decodedText))
}

private fun getKey(key:String):SecretKeySpec{
    var keyUtf8 = key.toByteArray(Charsets.UTF_8)
    val sha = MessageDigest.getInstance("SHA-1")
    keyUtf8 = sha.digest(keyUtf8)
    keyUtf8 = keyUtf8.copyOf(16)
    return SecretKeySpec(keyUtf8, "AES")
}