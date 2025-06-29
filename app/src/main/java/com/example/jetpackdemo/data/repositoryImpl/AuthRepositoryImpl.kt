package com.example.jetpackdemo.data.repositoryImpl

import com.example.jetpackdemo.data.local.dao.UserDao
import com.example.jetpackdemo.data.local.entity.toEntity
import com.example.jetpackdemo.data.local.entity.toUser
import com.example.jetpackdemo.domain.model.User
import com.example.jetpackdemo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val userDao: UserDao) : AuthRepository{
    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override suspend fun findByEmail(email: String,password:String): User? {
      return userDao.findByEmail(email)?.toUser()
    }

    override fun getAllUser(): Flow<List<User>> {
       return userDao.getAllUser().map { it.map { userEntity -> userEntity.toUser() } }
    }
}