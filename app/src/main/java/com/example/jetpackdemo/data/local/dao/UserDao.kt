package com.example.jetpackdemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetpackdemo.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity):Long

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun findByEmail(email: String): UserEntity?

    @Query("SELECT * FROM user_table ORDER BY id DESC")
     fun getAllUser():Flow<List<UserEntity>>

    @Query("UPDATE user_table SET password = :password WHERE email = :email")
    suspend fun updateUserPassword(password: String,email: String):Int

    @Query("UPDATE user_table SET username = :username, phoneNumber = :phoneNumber WHERE email = :email")
    suspend fun updateProfileData(username: String,phoneNumber: String,email: String):Int
}