package com.example.jetpackdemo.domain.repository

import com.example.jetpackdemo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun insertUser(user: User):Boolean
    suspend fun findByEmail(email:String,password:String):User?
    suspend fun updateUserPassword(email:String,password:String):Boolean
    fun getAllUser():Flow<List<User>>
    suspend fun updateUserProfile(username:String,imageUrl:String,phoneNumber: String,email: String):User?
}