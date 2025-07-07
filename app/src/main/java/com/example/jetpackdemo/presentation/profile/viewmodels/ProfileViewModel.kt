package com.example.jetpackdemo.presentation.profile.viewmodels


import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.R
import com.example.jetpackdemo.domain.model.User
import com.example.jetpackdemo.domain.usecase.ProfileUseCase
import com.example.jetpackdemo.presentation.profile.ProfileUiState
import com.example.jetpackdemo.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileUseCase: ProfileUseCase,
                                           private val userPreferences: UserPreferences):ViewModel(){
    private var _uiStateProfile = MutableSharedFlow<ProfileUiState>()
    val uiStateProfile = _uiStateProfile.asSharedFlow()

    fun updateUserProfile(userName:String,phoneNumber:String,imageUrl:String,email: String){
        viewModelScope.launch {
            if (TextUtils.isEmpty(userName)) {
                _uiStateProfile.emit(ProfileUiState.ErrorWithId(R.string.name_error))
            }else if (TextUtils.isEmpty(phoneNumber)) {
                _uiStateProfile.emit(ProfileUiState.ErrorWithId(R.string.phone_error))
            }else if (phoneNumber.length < 10) {
                _uiStateProfile.emit(ProfileUiState.ErrorWithId(R.string.valid_phone_error))
            }else {
                _uiStateProfile.emit(ProfileUiState.Loading)
                delay(1500)
                val result = profileUseCase.updateUserProfile(username = userName, phoneNumber = phoneNumber, imageUrl = imageUrl, email = email)
                if (result!=null) {
                    _uiStateProfile.emit(ProfileUiState.Result(data = result))
                } else {
                    _uiStateProfile.emit(ProfileUiState.Error("Not Updated properly"))
                }
            }


        }
    }

    fun saveUserData(user: User) {
        viewModelScope.launch {
            userPreferences.saveProfile(user)
        }
    }

}