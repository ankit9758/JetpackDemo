package com.example.jetpackdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackdemo.data.local.dao.UserDao
import com.example.jetpackdemo.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun userDao():UserDao
}