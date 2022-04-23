package com.EcoMovingServer.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EcoMovingServerApplication

fun main(args: Array<String>) {
	runApplication<EcoMovingServerApplication>(*args)
}
