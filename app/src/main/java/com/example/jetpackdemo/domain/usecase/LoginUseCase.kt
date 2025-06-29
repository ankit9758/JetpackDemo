package com.example.jetpackdemo.domain.usecase

import com.example.jetpackdemo.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String,password: String){
        authRepository.findByEmail(email,password)
    }
}