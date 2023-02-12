package com.alfikri.rizky.share.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfikri.rizky.share.R
import com.alfikri.rizky.share.ui.theme.*

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version Field, v 0.1 12/23/2022 5:30 AM by Rizky Alfikri Rachmat
 */

@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    type: TextFieldType = TextFieldType.NAME,
    onValidation: (Boolean) -> Unit,
    errorMessage: String = "",
    isError: Boolean = false,
    isTextVisible: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }

    PrimaryTextFieldComponent(
        value = value,
        isFocused = isFocused,
        onValueChange = {
            onValueChange(it)
            when (type) {
                is TextFieldType.NAME -> {
                    onValidation(it.length >= 4)
                }

                is TextFieldType.EMAIL -> {
                    onValidation(it.matches(Regex(EMAIL_REGEX)))
                }
            }
        },
        onFocusChanged = {
            isFocused = it
        },
        label = label,
        trailingIcon = trailingIcon,
        isError = isError,
        errorMessage = errorMessage,
        visualTransformation = if (isTextVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = if (isTextVisible) keyboardOptions else keyboardOptions.copy(
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = keyboardActions,
        modifier = modifier
    )
}

@Composable
fun PrimaryPasswordField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    onValidation: (Boolean) -> Unit,
    errorMessage: String = "",
    showTrailingIcon: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    PrimaryTextFieldComponent(
        modifier = modifier,
        value = value,
        isFocused = isFocused,
        onValueChange = {
            onValueChange(it)
            onValidation(it.length >= 8)
        },
        onFocusChanged = {
            isFocused = it
        },
        label = label,
        isError = isError,
        errorMessage = errorMessage,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingIcon = {
            if (showTrailingIcon) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = if (isPasswordVisible) painterResource(id = R.drawable.hide) else painterResource(
                            id = R.drawable.show
                        ), contentDescription = null
                    )
                }
            }
        },
    )
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholder: String,
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = Typography.body2.copy(
            color = Color.Black,
            fontSize = 14.sp
        ),
        singleLine = true,
        modifier = modifier
            .onFocusChanged {
                isFocused = it.isFocused
                onFocusChanged(it.isFocused)
            }
            .background(
                color = if (isFocused) Color.White else FieldBackground,
                shape = Shapes.large
            )
            .border(
                width = 1.dp,
                color = when {
                    isFocused -> PrimaryBlue
                    else -> Color.Transparent
                },
                shape = Shapes.large
            )
            .height(36.dp),
        keyboardActions = KeyboardActions(
            onDone = {
                isFocused = false
                onSubmit()
            }
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = Typography.caption.copy(
                                color = Placeholder,
                                letterSpacing = TextUnit.Unspecified
                            )
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}

@Composable
private fun PrimaryTextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    isFocused: Boolean,
    onValueChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    label: String,
    placeholder: String = "",
    errorMessage: String = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    Column {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChanged(it.isFocused) }
                .border(
                    width = 1.dp,
                    color = when {
                        value.isNotEmpty() && isError -> ErrorColor
                        isFocused -> PrimaryBlue
                        else -> Color.Transparent
                    },
                    shape = Shapes.large
                ),
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label, style = MaterialTheme.typography.body2.copy(
                        color = getErrorColorByStatus(
                            value.isNotEmpty() && isError,
                            isFocused,
                            Placeholder
                        )
                    )
                )
            },
            placeholder = {
                Text(
                    text = placeholder, style = Typography.caption.copy(
                        color = Placeholder
                    )
                )
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = if (isFocused) Color.White else FieldBackground,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                errorTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
                cursorColor = Color.Black,
            ),
            shape = Shapes.large,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions

        )
        Text(
            if (value.isNotEmpty() && isError) errorMessage else "",
            style = Typography.body2.copy(color = ErrorColor, fontSize = 12.sp),
            modifier = Modifier.padding(start = 18.dp, top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryTextFieldPreview() {
    PrimaryTextField(
        value = "", onValueChange = {}, label = "Email", isError = true, onValidation = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PrimaryPasswordFieldPreview() {
    PrimaryPasswordField(
        value = "", onValueChange = {}, label = "Password", onValidation = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    var text by remember {
        mutableStateOf("")
    }
    SearchField(
        value = text,
        onValueChange = {
            text = it
        },
        onFocusChanged = {},
        placeholder = "Search",
        onSubmit = {},
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                tint = PrimaryBlue
            )
        })
}

private fun getErrorColorByStatus(
    isError: Boolean,
    isFocused: Boolean,
    defaultColor: Color
): Color {
    return when {
        isError -> ErrorColor
        isFocused -> PrimaryBlue
        else -> defaultColor
    }
}

sealed class TextFieldType {
    object EMAIL: TextFieldType()
    object NAME: TextFieldType()
}

private const val EMAIL_REGEX =
    "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}"