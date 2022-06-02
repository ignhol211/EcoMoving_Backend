package com.serverecomoving

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@org.springframework.stereotype.Repository
interface UsersRepository : JpaRepository<User,String>{
    fun findByToken (Token:String): Optional<User>
}