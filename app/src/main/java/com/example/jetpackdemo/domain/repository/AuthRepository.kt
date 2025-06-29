package com.example.jetpackdemo.domain.repository

import com.example.jetpackdemo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun insertUser(user: User)
    suspend fun findByEmail(email:String,password:String):User?
    fun getAllUser():Flow<List<User>>
}