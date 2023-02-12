package com.alfikri.rizky.authentications.presentation.ui.state

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.alfikri.rizky.core.constant.DeeplinkConstant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationUiState, v 0.1 12/25/2022 7:35 AM by Rizky Alfikri Rachmat
 */

data class AuthenticationUiState(
    val scaffoldState: ScaffoldState,
    val scope: CoroutineScope,
    private val context: Context,
) {

    fun showErrorMessage(message: String) {
        scope.launch {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun redirectToStoryScreen() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(DeeplinkConstant.STORY_DEEPLINK)
        )
        context.startActivity(intent)
    }
}

class LoginComponentState(
    val focusManager: FocusManager,
    email: String,
    password: String,
    isEmailValid: Boolean,
    isPasswordValid: Boolean
) {

    val email = mutableStateOf(email)
    val password = mutableStateOf(password)
    val isEmailValid = mutableStateOf(isEmailValid)
    val isPasswordValid = mutableStateOf(isPasswordValid)

    fun isLoginFormValid(): Boolean {
        return isEmailValid.value && isPasswordValid.value
    }

}

class RegisterComponentState(
    val focusManager: FocusManager,
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    isNameValid: Boolean,
    isEmailValid: Boolean,
    isPasswordValid: Boolean
) {

    val name = mutableStateOf(name)
    val email = mutableStateOf(email)
    val password = mutableStateOf(password)
    val confirmPassword = mutableStateOf(confirmPassword)

    val isNameValid = mutableStateOf(isNameValid)
    val isEmailValid = mutableStateOf(isEmailValid)
    val isPasswordValid = mutableStateOf(isPasswordValid)

    fun isConfirmPasswordValid(): Boolean {
        return password.value == confirmPassword.value
    }

    fun isRegisterFormValid(): Boolean {
        return isNameValid.value && isEmailValid.value && isPasswordValid.value && isConfirmPasswordValid()
    }
}

@Composable
fun rememberAuthenticationUiState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
): AuthenticationUiState = remember(scaffoldState, scope, context) {
    AuthenticationUiState(scaffoldState, scope, context)
}

@Composable
fun rememberLoginComponentState(
    focusManager: FocusManager = LocalFocusManager.current,
    email: String = "",
    password: String = "",
    isEmailValid: Boolean = false,
    isPasswordValid: Boolean = false
): LoginComponentState = remember(
    focusManager, email, password
) {
    LoginComponentState(focusManager, email, password, isEmailValid, isPasswordValid)
}

@Composable
fun rememberRegisterComponentState(
    focusManager: FocusManager = LocalFocusManager.current,
    name: String = "",
    email: String = "",
    password: String = "",
    confirmPassword: String = "",
    isName: Boolean = false,
    isEmailValid: Boolean = false,
    isPasswordValid: Boolean = false
): RegisterComponentState = remember(
    focusManager, name, email, password, confirmPassword
) {
    RegisterComponentState(
        focusManager,
        name,
        email,
        password,
        confirmPassword,
        isName,
        isEmailValid,
        isPasswordValid
    )
}