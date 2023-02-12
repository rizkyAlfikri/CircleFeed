package com.alfikri.rizky.share.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alfikri.rizky.share.R
import com.alfikri.rizky.share.ui.theme.Grey4
import com.alfikri.rizky.share.ui.theme.Shapes

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version Button, v 0.1 12/22/2022 8:34 PM by Rizky Alfikri Rachmat
 */

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
) {
    PrimaryButtonComponent(
        text = {
            Text(
                text = text, style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.SemiBold, color = Color.White
                )
            )
        },
        isEnable = isEnable, onClick = onClick,
        startIcon = startIcon, endIcon = endIcon,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding( vertical = 8.dp),
    )

}

@Composable
fun PrimaryButtonSmall(
    modifier: Modifier = Modifier,
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit,
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
) {
    PrimaryButtonComponent(
        text = {
            Text(
                text = text, style = MaterialTheme.typography.caption.copy(
                    color = Color.White
                )
            )
        },
        isEnable = isEnable, onClick = onClick,
        startIcon = startIcon, endIcon = endIcon,
        modifier = modifier
            .height(36.dp)
            .padding(horizontal = 16.dp)
    )

}

@Composable
private fun PrimaryButtonComponent(
    isEnable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null,
) {
    Button(
        modifier = modifier,
        enabled = isEnable,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        shape = Shapes.large,
        onClick = { onClick() },
    ) {

        val modifierColor = if (isEnable) {
            Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF5151C6),
                            Color(0xFF5151C6),
                            Color(0xFF888BF4),

                            )
                    )
                )
                .then(modifier)
        } else {
            Modifier
                .background(
                    Grey4
                )
                .then(modifier)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifierColor
        ) {
            if (startIcon != null) {
                startIcon()
                Spacer(modifier = Modifier.width(6.dp))
            }

            text()

            if (endIcon != null) {
                Spacer(modifier = Modifier.width(6.dp))
                endIcon()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(
        text = "Sign in",
        isEnable = true,
        onClick = {},
        startIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_android_black_24dp),
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonSmallPreview() {
    PrimaryButtonSmall(
        text = "Sign in",
        isEnable = true,
        onClick = {},
    )
}

