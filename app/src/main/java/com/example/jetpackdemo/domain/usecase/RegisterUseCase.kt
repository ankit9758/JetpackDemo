package com.example.jetpackdemo.domain.usecase

import com.example.jetpackdemo.domain.model.User
import com.example.jetpackdemo.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val  authRepository: AuthRepository) {
    suspend operator fun invoke(user: User):Boolean{
       return authRepository.insertUser(user)
    }
}