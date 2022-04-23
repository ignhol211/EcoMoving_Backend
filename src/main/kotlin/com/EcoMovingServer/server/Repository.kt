package com.EcoMovingServer.server

import org.springframework.data.jpa.repository.JpaRepository

@org.springframework.stereotype.Repository
interface UsersRepository : JpaRepository<User,String>