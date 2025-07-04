package com.example.jetpackdemo.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackdemo.data.local.AppDataBase
import com.example.jetpackdemo.data.local.dao.UserDao
import com.example.jetpackdemo.data.repositoryImpl.AuthRepositoryImpl
import com.example.jetpackdemo.domain.repository.AuthRepository
import com.example.jetpackdemo.domain.usecase.ChangePasswordUseCase
import com.example.jetpackdemo.domain.usecase.ForgotPasswordUseCase
import com.example.jetpackdemo.domain.usecase.LoginUseCase
import com.example.jetpackdemo.domain.usecase.RegisterUseCase
import com.example.jetpackdemo.utils.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "app.db").build()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDataBase: AppDataBase): UserDao {
        return appDataBase.userDao()
    }

    @Singleton
    @Provides
    fun provideAuthRepositoryImpl(userDao: UserDao): AuthRepository {
        return AuthRepositoryImpl(userDao)
    }

    @Provides
    fun provideLoginUseCase(authRepository : AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }
    @Provides
    fun provideChangePasswordUseCase(authRepository: AuthRepository): ChangePasswordUseCase {
        return ChangePasswordUseCase(authRepository)
    }
    @Provides
    fun provideForgotPasswordUseCase(authRepository: AuthRepository): ForgotPasswordUseCase {
        return ForgotPasswordUseCase(authRepository)
    }
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

}