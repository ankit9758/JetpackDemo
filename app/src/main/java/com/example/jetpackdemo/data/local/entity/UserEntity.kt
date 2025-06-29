package com.example.jetpackdemo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetpackdemo.domain.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val password: String
)

fun UserEntity.toUser(): User {
    return User(id = id, username = username, email = email, password = password)
}

fun User.toEntity(): UserEntity {
    return UserEntity(id = id, username = username, email = email, password = password)
}
