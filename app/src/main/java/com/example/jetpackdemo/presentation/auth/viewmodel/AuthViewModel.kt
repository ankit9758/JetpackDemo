package com.example.jetpackdemo.presentation.auth.viewmodel

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.R
import com.example.jetpackdemo.di.IoDispatcher
import com.example.jetpackdemo.di.MainDispatcher
import com.example.jetpackdemo.domain.model.User
import com.example.jetpackdemo.domain.usecase.ChangePasswordUseCase
import com.example.jetpackdemo.domain.usecase.ForgotPasswordUseCase
import com.example.jetpackdemo.domain.usecase.LoginUseCase
import com.example.jetpackdemo.domain.usecase.RegisterUseCase
import com.example.jetpackdemo.presentation.auth.state.AuthUiState
import com.example.jetpackdemo.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val userPreferences: UserPreferences,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableSharedFlow<AuthUiState>()
    val uiState = _uiState.asSharedFlow()
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    val isLoggedIn: StateFlow<Boolean> = userPreferences.isLoggedIn.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

   private fun setLoggedIn() {
        viewModelScope.launch { userPreferences.setLoggedIn(true) }
    }


    fun logout() {
        viewModelScope.launch { userPreferences.clear() }
    }


    fun onLogin(email: String, password: String) {
        viewModelScope.launch() {
            if (email.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.email_error))
            } else if (!email.matches(emailRegex)) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.valid_email))
            } else if (password.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.password_error))
            } else {
                _uiState.emit( AuthUiState.Loading)
                //delay(1000)
                // 👇 Run DB call in IO
                val result = withContext(ioDispatcher) {
                    loginUseCase.invoke(email, password)
                }
                if (result != null) {
                    if(result.password==password){
                        saveUserData(result)
                        _uiState.emit(AuthUiState.Success)
                    }else{
                        _uiState.emit(AuthUiState.ErrorWithId(R.string.enter_correct_password))
                    }

                } else {
                    _uiState.emit(AuthUiState.ErrorWithId(R.string.valid_credential_error))
                }
            }


        }
    }

    fun onRecoverPassword(email: String) {
        viewModelScope.launch {
            if (email.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.email_error))
            } else if (!email.matches(emailRegex)) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.valid_email))
            } else {
                _uiState.emit( AuthUiState.Loading)
                delay(2000)
                val result = forgotPasswordUseCase.invoke(email)
                if (result != null) {
                    _uiState.emit(AuthUiState.Success)
                } else {
                    _uiState.emit(AuthUiState.ErrorWithId(R.string.valid_email))
                }
            }


        }
    }

    fun onChangePassword(currentPassword: String,newPassword:String,confirmPassword:String,email: String) {
        viewModelScope.launch {
            if (newPassword.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.password_error))
            } else if (newPassword==currentPassword) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.new_password_same_error))
            }else if (confirmPassword.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.confirm_password_error))
            } else if (confirmPassword!=newPassword) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.password_same_error))
            }else {
                _uiState.emit( AuthUiState.Loading)
                delay(1500)
                val result = changePasswordUseCase.changePassword(password = newPassword, email = email)
                if (result) {
                    _uiState.emit(AuthUiState.Success)
                } else {
                    _uiState.emit(AuthUiState.Error("Something went wrong"))
                }
            }


        }
    }


    fun onRegister(email: String, password: String,name: String,phoneNumber:String,confirmPassword:String) {
        viewModelScope.launch {
            if (name.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.name_error))
            }else if (email.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.email_error))
            } else if (!email.matches(emailRegex)) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.valid_email))
            }else if (phoneNumber.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.phone_error))
            } else if (password.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.password_error))
            }else if (confirmPassword.isBlank()) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.confirm_password_error))
            } else if (confirmPassword!=password) {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.password_same_error))
            }
            else {
                _uiState.emit( AuthUiState.Loading)
                delay(1500)
                val user=User(email = email, password = password, username =name, phoneNumber = phoneNumber, imageUrl = "")
                val result = registerUseCase.invoke(user)
                if (result) {
                    saveUserData(user)
                    _uiState.emit(AuthUiState.Success)
                } else {
                    _uiState.emit(AuthUiState.ErrorWithId(R.string.valid_credential_error))
                }
            }


        }

    }

    private fun saveUserData(user: User) {
        viewModelScope.launch {
            userPreferences.saveProfile(user)
        }
        setLoggedIn()
    }

    fun findUserDetailByEmail(email: String) {
        viewModelScope.launch {
            _uiState.emit(AuthUiState.Loading)
            delay(500)
            val result = forgotPasswordUseCase.invoke(email)
            if (result != null) {
                _uiState.emit(AuthUiState.Result(result))
            } else {
                _uiState.emit(AuthUiState.ErrorWithId(R.string.valid_email))
            }
        }
    }
}