package com.alfikri.rizky.authentications.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfikri.rizky.authentications.domain.model.AuthenticationResultModel
import com.alfikri.rizky.authentications.domain.model.LoginRequestModel
import com.alfikri.rizky.authentications.domain.model.RegisterRequestModel
import com.alfikri.rizky.authentications.domain.usecase.AuthenticationUseCase
import com.alfikri.rizky.core.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationViewModel, v 0.1 12/26/2022 11:11 AM by Rizky Alfikri Rachmat
 */

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    val registerState: StateFlow<UiState<AuthenticationResultModel<Nothing>>>
        get() = _registerState
    private val _registerState: MutableStateFlow<UiState<AuthenticationResultModel<Nothing>>> =
        MutableStateFlow(UiState.Empty)

    val loginState: StateFlow<UiState<AuthenticationResultModel<Nothing>>>
        get() = _loginState
    private var _loginState: MutableStateFlow<UiState<AuthenticationResultModel<Nothing>>> =
        MutableStateFlow(UiState.Empty)

    fun register(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            authenticationUseCase.register(
                RegisterRequestModel(name, email, password)
            )
                .catch {
                    _registerState.value = UiState.Error(it.message.orEmpty())
                }
                .collect { result ->
                    if (result.error == true) {
                        _registerState.value = UiState.Error(result.message.orEmpty())
                    } else {
                        _registerState.value = UiState.Empty
                        login(email, password, true)
                    }
                }
        }
    }

    fun login(
        email: String,
        password: String,
        isLoginFromRegister: Boolean = false
    ) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            authenticationUseCase.login(
                LoginRequestModel(
                    email = email,
                    password = password
                )
            )
                .catch {
                    val errorMessage = if (isLoginFromRegister) {
                        "User has created, please login with created user data"
                    } else {
                        it.message.orEmpty()
                    }
                    _loginState.value = UiState.Error(errorMessage)
                }
                .collect { result ->
                    if (result.error == true) {
                        val errorMessage = if (isLoginFromRegister) {
                            "User has created, please login with created user data"
                        } else {
                            result.message.orEmpty()
                        }
                        _loginState.value = UiState.Error(errorMessage)
                    } else {
                        _loginState.value = UiState.Success(result)
                    }
                }
        }
    }
}