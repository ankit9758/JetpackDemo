package com.example.jetpackdemo.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.jetpackdemo.domain.model.User

@Entity(tableName = "user_table", indices = [Index(value = ["email"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val imageUrl: String
)

fun UserEntity.toUser(): User {
    return User(username = username, email = email, password = password, phoneNumber = phoneNumber,imageUrl=imageUrl)
}

fun User.toEntity(): UserEntity {
    return UserEntity(username = username, email = email, password = password,phoneNumber=phoneNumber,imageUrl=imageUrl)
}
