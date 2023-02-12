package com.alfikri.rizky.story.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alfikri.rizky.core.presentation.utils.DateFormat
import com.alfikri.rizky.share.ui.compose.ShimmerAnimation
import com.alfikri.rizky.share.ui.theme.Placeholder
import com.alfikri.rizky.share.ui.theme.PrimaryBlue
import com.alfikri.rizky.share.ui.theme.Typography

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version Components, v 0.1 1/8/2023 11:07 AM by Rizky Alfikri Rachmat
 */
@Composable
fun StoryContentComponent(
    modifier: Modifier = Modifier,
    storyName: String,
    storyPhotoUrl: String,
    storyCreatedAt: String,
    additionalInfo: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = storyName,
                style = Typography.body2,
                modifier = Modifier
                    .padding(start = 10.dp, end = 16.dp)
                    .weight(1f)
            )
            Text(
                text = DateFormat.formatDateWithFormatTimeAgo(storyCreatedAt),
                style = Typography.caption.copy(
                    color = Placeholder,
                    letterSpacing = TextUnit.Unspecified
                )
            )
        }

        AsyncImage(
            model = storyPhotoUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .height(224.dp)
                .fillMaxSize()
        )

        additionalInfo?.invoke()
    }
}

@Composable
fun StoryContentShimmerComponent(
    modifier: Modifier = Modifier,
    additionalShimmerInfo: (@Composable (Brush) -> Unit)? = null
) {
    ShimmerAnimation { brush ->
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )

                Spacer(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 16.dp)
                        .weight(1f)
                        .height(21.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush = brush)
                )

                Spacer(
                    modifier = Modifier
                        .width(64.dp)
                        .height(21.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush = brush)
                )

            }

            Spacer(
                modifier = Modifier
                    .height(224.dp)
                    .width(335.dp)
                    .background(brush = brush)
            )

            additionalShimmerInfo?.invoke(brush)
        }
    }
}