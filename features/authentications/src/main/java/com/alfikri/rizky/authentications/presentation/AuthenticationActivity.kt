package com.alfikri.rizky.authentications.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alfikri.rizky.authentications.R
import com.alfikri.rizky.authentications.presentation.ui.state.*
import com.alfikri.rizky.authentications.presentation.viewmodel.AuthenticationViewModel
import com.alfikri.rizky.core.presentation.UiState
import com.alfikri.rizky.share.ui.compose.*
import com.alfikri.rizky.share.ui.compose.TextFieldType.*
import com.alfikri.rizky.share.ui.theme.CircleFeedTheme
import com.alfikri.rizky.share.ui.theme.PrimaryBlue
import com.alfikri.rizky.share.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CircleFeedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AuthenticationScreen(viewModel = viewModel, onFinishActivity = { finish() })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CircleFeedTheme {
//        AuthenticationScreen()
    }
}

@ExperimentalAnimationApi
@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel,
    modifier: Modifier = Modifier,
    onFinishActivity: () -> Unit
) {

    val authenticationUiState = rememberAuthenticationUiState()
    var isLoginLoading by remember { mutableStateOf(false) }
    var isRegisterLoading by remember { mutableStateOf(false) }
    var isShowLoginScreen by remember { mutableStateOf(true) }

    viewModel.loginState.collectAsState(initial = UiState.Empty).value.let { loginState ->
        when (loginState) {
            is UiState.Loading -> {
                isLoginLoading = true
            }

            is UiState.Error -> {
                isLoginLoading = false
                authenticationUiState.showErrorMessage(loginState.error)
            }

            is UiState.Success -> {
                isLoginLoading = false
                authenticationUiState.redirectToStoryScreen()
                onFinishActivity()
            }

            is UiState.Empty -> {
                isLoginLoading = false
            }
        }
    }

    viewModel.registerState.collectAsState(initial = UiState.Empty).value.let { registerState ->
        when (registerState) {
            is UiState.Loading -> {
                isRegisterLoading = true
            }

            is UiState.Error -> {
                isRegisterLoading = false
                authenticationUiState.showErrorMessage(registerState.error)
            }

            is UiState.Success -> {
                isRegisterLoading = false
                authenticationUiState.redirectToStoryScreen()
                onFinishActivity()
            }

            is UiState.Empty -> {
                isRegisterLoading = false
            }
        }
    }

    Scaffold(
        scaffoldState = authenticationUiState.scaffoldState,
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.authentication_banner),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.TopCenter
            )
            Card(
                modifier = Modifier
                    .padding(PaddingValues())
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(540.dp)
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            ) {

                AnimatedContent(targetState = isShowLoginScreen) { targetState ->
                    if (targetState) {
                        LoginComponent(
                            modifier = Modifier.graphicsLayer {
                                alpha = if (isShowLoginScreen) 1F else 0F
                            },
                            onClickLogin = { email, password ->
                                viewModel.login(
                                    email = email,
                                    password = password
                                )
                            },
                            onClickRegisterScreen = {
                                isShowLoginScreen = false
                            }
                        )
                    } else {
                        RegisterComponent(
                            modifier = Modifier.graphicsLayer {
                                alpha = if (isShowLoginScreen) 0F else 1F
                            },
                            onClickRegister = { name, email, password ->
                                viewModel.register(
                                    name = name,
                                    email = email,
                                    password = password
                                )
                            },
                            onClickLoginScreen = {
                                isShowLoginScreen = true
                            }
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = isLoginLoading || isRegisterLoading,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                LoadingScreen()
            }
        }
    }
}

@Composable
fun LoginComponent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    uiState: LoginComponentState = rememberLoginComponentState(),
    onClickLogin: (String, String) -> Unit,
    onClickRegisterScreen: () -> Unit,
    scroll: ScrollState = rememberScrollState(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .verticalScroll(scroll)
            .padding(start = 30.dp, end = 30.dp, top = 40.dp, bottom = 56.dp)
    ) {
        PrimaryTextField(
            value = uiState.email.value,
            onValueChange = {
                uiState.email.value = it
            },
            label = stringResource(id = R.string.login_email_label),
            errorMessage = stringResource(id = R.string.login_email_error),
            isError = !uiState.isEmailValid.value,
            onValidation = {
                uiState.isEmailValid.value = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { uiState.focusManager.moveFocus(FocusDirection.Down) }
            ),
            type = EMAIL
        )

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryPasswordField(
            value = uiState.password.value,
            onValueChange = {
                uiState.password.value = it
            },
            label = stringResource(id = R.string.login_password_label),
            errorMessage = stringResource(id = R.string.login_password_error),
            isError = !uiState.isPasswordValid.value,
            onValidation = {
                uiState.isPasswordValid.value = it
            },
            showTrailingIcon = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { uiState.focusManager.clearFocus() }
            ),
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(id = R.string.login_forgot_password_label),
            style = Typography.caption.copy(color = PrimaryBlue)
        )

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(
            text = stringResource(id = R.string.login_label),
            onClick = {
                onClickLogin(uiState.email.value, uiState.password.value)
            },
            isEnable = uiState.isLoginFormValid()
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(id = R.string.login_or_login_by_label),
            style = Typography.caption.copy(color = Color(0xFF606060))
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_google_login),
                contentDescription = null, modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_facebook_login),
                contentDescription = null, modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row {
            Text(
                text = stringResource(id = R.string.login_do_not_have_account_label) + " ",
                style = Typography.body2.copy(color = Color(0xFF606060))
            )
            Text(
                text = stringResource(id = R.string.register_label),
                style = Typography.body2.copy(color = PrimaryBlue),
                modifier = Modifier.clickable { if (!isLoading) onClickRegisterScreen() }
            )
        }
    }
}

@Composable
fun RegisterComponent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClickRegister: (String, String, String) -> Unit,
    onClickLoginScreen: () -> Unit,
    uiState: RegisterComponentState = rememberRegisterComponentState(),
    scroll: ScrollState = rememberScrollState()
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .verticalScroll(scroll)
            .padding(start = 30.dp, end = 30.dp, top = 40.dp, bottom = 84.dp)
    ) {
        PrimaryTextField(
            value = uiState.name.value,
            onValueChange = {
                uiState.name.value = it

            },
            label = stringResource(id = R.string.register_name_label),
            errorMessage = stringResource(id = R.string.register_name_error),
            isError = !uiState.isNameValid.value,
            onValidation = {
                uiState.isNameValid.value = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { uiState.focusManager.moveFocus(FocusDirection.Down) }
            ),
            type = NAME
        )

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryTextField(
            value = uiState.email.value,
            onValueChange = {
                uiState.email.value = it
            },
            label = stringResource(id = R.string.login_email_label),
            errorMessage = stringResource(id = R.string.login_email_error),
            isError = !uiState.isEmailValid.value,
            onValidation = {
                uiState.isEmailValid.value = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { uiState.focusManager.moveFocus(FocusDirection.Down) }
            ),
            type = EMAIL
        )

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryPasswordField(
            value = uiState.password.value,
            onValueChange = {
                uiState.password.value = it
            },
            label = stringResource(id = R.string.login_password_label),
            errorMessage = stringResource(id = R.string.login_password_error),
            isError = !uiState.isPasswordValid.value,
            onValidation = {
                uiState.isPasswordValid.value = it
            },
            showTrailingIcon = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { uiState.focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryPasswordField(
            value = uiState.confirmPassword.value,
            onValueChange = {
                uiState.confirmPassword.value = it
            },
            label = stringResource(id = R.string.register_confirm_password_label),
            errorMessage = stringResource(id = R.string.register_confirm_password_error),
            isError = !uiState.isConfirmPasswordValid(),
            onValidation = {},
            showTrailingIcon = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { uiState.focusManager.clearFocus() }
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        PrimaryButton(
            text = stringResource(id = R.string.register_label),
            onClick = {
                onClickRegister(uiState.name.value, uiState.email.value, uiState.password.value)
            },
            isEnable = uiState.isRegisterFormValid()
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row {
            Text(
                text = stringResource(id = R.string.register_already_have_account_label) + " ",
                style = Typography.body2.copy(color = Color(0xFF606060))
            )
            Text(
                text = stringResource(id = R.string.login_label),
                style = Typography.body2.copy(color = PrimaryBlue),
                modifier = Modifier.clickable {
                    if (!isLoading) onClickLoginScreen()
                }
            )
        }
    }
}