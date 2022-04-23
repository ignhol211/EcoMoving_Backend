package com.EcoMovingServer.server

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller (private val userRepository : UsersRepository) {

    @GetMapping("/")
    fun index():String{
        return "SUCCESS"
    }

    @PostMapping("signUp")
    fun userSignUp(@RequestBody user:User):User{
        userRepository.save(user)
        val data = userRepository.getById(user.user)
        return data
    }

    @GetMapping("signIn/{userId}/{password}")
    fun userSignIn(@PathVariable userId:String, @PathVariable password: String):User?{
        val possibleUser = userRepository.findById(userId)
        return if(possibleUser.isPresent){
            val user = userRepository.getById(userId)
            return if(user.password == password)
                user
            else
                null
        }else
            null
    }

}