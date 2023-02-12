package com.alfikri.rizky.story.presentation.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfikri.rizky.share.ui.theme.Shapes
import com.alfikri.rizky.share.ui.theme.Typography
import com.alfikri.rizky.story.R
import com.alfikri.rizky.story.domain.model.StoryUserData

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version ProfileScreen, v 0.1 1/15/2023 5:36 PM by Rizky Alfikri Rachmat
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    storyUserData: StoryUserData,
    onClickLogout: () -> Unit
) {
    val context = LocalContext.current
    val optionTitles = context.resources.getStringArray(R.array.profile_option)

    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = com.alfikri.rizky.share.R.drawable.splash_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            ProfileCardComponent(storyUserData = storyUserData)

            Spacer(modifier = Modifier.height(24.dp))

            for (title in optionTitles) {
                ProfileOptionComponent(
                    optionTitle = title,
                    modifier = Modifier.align(Alignment.Start),
                    onClickOption = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = onClickLogout,
                modifier = Modifier.padding(start = 20.dp),
                shape = Shapes.large,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Log out", style = Typography.body2.copy(color = Color.Black))
            }
        }
    }
}

@Composable
fun ProfileCardComponent(modifier: Modifier = Modifier, storyUserData: StoryUserData) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(14.dp)
            .background(color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(
                    CircleShape
                ),
            colorFilter = ColorFilter.tint(Color.White)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp)
        ) {
            Text(
                text = storyUserData.username,
                style = Typography.body2.copy(color = Color.White),
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = storyUserData.email,
                style = Typography.body2.copy(color = Color(0xFFC0C0C0), fontSize = 12.sp),
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_profile_edit),
            contentDescription = null,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
fun ProfileOptionComponent(
    modifier: Modifier = Modifier,
    optionTitle: String,
    onClickOption: () -> Unit
) {
    Row(
        modifier = modifier
            .width(250.dp)
            .height(42.dp)
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp)
            )
            .clip(shape = RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
            .clickable { onClickOption() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = optionTitle,
            style = Typography.body2.copy(color = Color.White),
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp, end = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.arrow_profile_right),
            contentDescription = null,
            Modifier
                .background(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = CircleShape
                )
                .size(30.dp)
                .clip(shape = CircleShape),
        )

        Spacer(modifier = Modifier.width(6.dp))

    }
}