package com.alfikri.rizky.circlefeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfikri.rizky.authentications.domain.usecase.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version MainViewModel, v 0.1 12/23/2022 10:28 AM by Rizky Alfikri Rachmat
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    val state: LiveData<MainSplashState>
        get() = _state
    private var _state: MutableLiveData<MainSplashState> = MutableLiveData()

    init {
        viewModelScope.launch {
            delay(1000L)
            _state.postValue(MainSplashState.ShowMainSplashLabel)
            delay(1500L)
            authenticationUseCase.isLogin()
                .catch {
                    _state.postValue(MainSplashState.ShowMainSplashButton)
                }
                .collect { isUserHasLogin ->
                    if (isUserHasLogin) {
                        _state.postValue(MainSplashState.NavigateToHomeScreen)
                    } else {
                        _state.postValue(MainSplashState.ShowMainSplashButton)
                    }
                }
        }
    }
}

sealed class MainSplashState {
    object ShowMainSplashLabel: MainSplashState()
    object ShowMainSplashButton: MainSplashState()
    object NavigateToHomeScreen: MainSplashState()
}