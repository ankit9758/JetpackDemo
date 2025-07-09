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
    override suspend fun insertUser(user: User):Boolean {
        val rowId =userDao.insertUser(user.toEntity())
        return rowId != -1L     // ‑1 means UNIQUE constraint hit
    }

    override suspend fun findByEmail(email: String,password:String): User? {
      return userDao.findByEmail(email=email)?.toUser()
    }

    override suspend fun updateUserPassword(email: String, password: String): Boolean {
        return userDao.updateUserPassword(password, email) > 0
    }

    override fun getAllUser(): Flow<List<User>> {
       return userDao.getAllUser().map { it.map { userEntity -> userEntity.toUser() } }
    }

    override suspend fun updateUserProfile(
        username: String,
        imageUrl: String,
        phoneNumber: String,
        email: String
    ): User? {
        val updated = userDao.updateProfileData(username, imageUrl, phoneNumber, email)
        return if (updated > 0) userDao.findByEmail(email)?.toUser() else null
    }
}