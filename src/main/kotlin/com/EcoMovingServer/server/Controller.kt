package com.EcoMovingServer.server

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@RestController
@EnableWebMvc
class Controller (private val userRepository : UsersRepository) {

    @GetMapping("/")
    fun index():List<User>{
        return userRepository.findAll()
    }

    @PostMapping("signUp", consumes=["application/octet-stream","application/x-www-form-urlencoded","application/json"])
    fun userSignUp(@RequestBody user:User):Any{
        userRepository.save(user)
        return userRepository.findAll().first()
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

