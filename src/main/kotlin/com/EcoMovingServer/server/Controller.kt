package com.EcoMovingServer.server

import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


@RestController
class Controller (private val userRepository : UsersRepository) {

    @GetMapping("/")
    fun index():List<User>{
        return userRepository.findAll()
    }

    @PostMapping("register")
    fun userSignUp(@RequestBody request:User):Any{
        userRepository.save(request)
        return userRepository.findAll()
    }
/*
    @RequestMapping("signUp", method = [RequestMethod.POST])
    fun userSignUp2(@RequestBody user:User):Any{
        userRepository.save(user)
        return userRepository.findAll().first()
    }


    @PostMapping("signUp", consumes = [MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun userSignUp3(@RequestBody user:User):Any{
        userRepository.save(user)
        return userRepository.findAll().first()
    }

    @PostMapping("signUp", consumes=["application/octet-stream","application/x-www-form-urlencoded","application/json"])
    fun userSignUp4(@RequestBody user:User):Any{
        userRepository.save(user)
        return userRepository.findAll().first()
    }
*/
    @GetMapping("login")
    fun userSignIn(@RequestBody request:User):User?{
        return if(validateUserViaPassword(request.email,request.password, userRepository)){

            val token = getRandomToken()
            val user = userRepository.getById(request.email)
            user.token = token
            userRepository.save(user)
            user.token = encode(token,user.codeKey)
            user
        }else
            null
    }

    @GetMapping("journey")
    fun journey(@RequestBody request:User):Any?{
        return if(validateUserViaToken(request.email,request.token,userRepository)){
            true
        }else
            null
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

private fun validateUserViaToken(email:String,token: String?, userRepository : UsersRepository):Boolean{
    token?.let {
        val user = userRepository.getById(email)
        return user.token == decode(it,user.codeKey)
    }
    return false
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