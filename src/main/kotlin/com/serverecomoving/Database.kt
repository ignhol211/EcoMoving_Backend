package com.serverecomoving

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Database {

    @Bean
    fun initDatabase(usersRepository: UsersRepository): CommandLineRunner {
        return CommandLineRunner{
            usersRepository.save(User("ignac@gmail.com","Ignac123$"))
        }
    }

}