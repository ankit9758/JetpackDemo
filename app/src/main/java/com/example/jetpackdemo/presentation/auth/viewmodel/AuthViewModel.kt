package com.example.jetpackdemo.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jetpackdemo.domain.usecase.LoginUseCase
import com.example.jetpackdemo.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
}