package com.example.jetpackdemo.domain.usecase

import com.example.jetpackdemo.domain.model.User
import com.example.jetpackdemo.domain.repository.AuthRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val profileRepository: AuthRepository) {
    suspend fun updateUserProfile(username: String, imageUrl: String, phoneNumber: String, email: String): User? {
        return profileRepository.updateUserProfile(username, imageUrl, phoneNumber, email)
    }

}