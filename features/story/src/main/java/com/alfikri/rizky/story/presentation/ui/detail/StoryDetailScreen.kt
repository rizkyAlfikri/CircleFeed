package com.alfikri.rizky.story.presentation.ui.detail

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alfikri.rizky.core.presentation.UiState
import com.alfikri.rizky.share.ui.compose.ShimmerAnimation
import com.alfikri.rizky.share.ui.theme.Placeholder
import com.alfikri.rizky.share.ui.theme.PrimaryBlue
import com.alfikri.rizky.share.ui.theme.TextSecondary
import com.alfikri.rizky.share.ui.theme.Typography
import com.alfikri.rizky.story.R
import com.alfikri.rizky.story.presentation.ui.components.StoryContentComponent
import com.alfikri.rizky.story.presentation.ui.components.StoryContentShimmerComponent
import com.alfikri.rizky.story.presentation.viewmodel.StoryDetailViewModel

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryDetailScreen, v 0.1 1/8/2023 10:51 AM by Rizky Alfikri Rachmat
 */
@Composable
fun StoryDetailScreen(
    modifier: Modifier = Modifier,
    storyId: String,
    onShowErrorMessage: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: StoryDetailViewModel = hiltViewModel()
) {
    viewModel.storyDetailState.collectAsState(UiState.Empty).value.let { state ->
        when (state) {
            is UiState.Loading -> {
                StoryContentDetailShimmerComponent(
                    modifier = modifier,
                    onNavigateBack = onNavigateBack
                )
            }

            is UiState.Error -> {
                onShowErrorMessage(state.error)
            }

            is UiState.Empty -> {
                viewModel.getStoryDetail(storyId)
                viewModel.getStoryFavorite(storyId)
            }

            is UiState.Success -> {
                val isStoryFavorite = viewModel.isStoryFavorite.collectAsState().value
                val context = LocalContext.current
                StoryContentDetailComponent(
                    modifier = modifier,
                    name = state.data.name.orEmpty(),
                    photoUrl = state.data.photoUrl.orEmpty(),
                    description = state.data.description.orEmpty(),
                    createdAt = state.data.createdAt.orEmpty(),
                    isStoryFavorite = isStoryFavorite,
                    onNavigateBack = onNavigateBack,
                    onClickFavorite = {
                        if (isStoryFavorite) {
                            viewModel.deleteStoryFavorite(state.data)
                            Toast.makeText(context, "Success delete favorite story", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.insertStoryFavorite(state.data)
                            Toast.makeText(context, "Success add favorite story", Toast.LENGTH_SHORT).show()
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun HeaderComponent(
    modifier: Modifier = Modifier,
    isStoryFavorite: Boolean,
    onClickFavorite: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(Color.White),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null,
            modifier = Modifier.clickable {
                onNavigateBack()
            })

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = if (isStoryFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_heart_outline_black),
            contentDescription = null,
            tint = if (isStoryFavorite) Color.Red else LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
            modifier = Modifier.clickable {
                onClickFavorite()
            })

        Spacer(modifier = Modifier.width(20.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_plus_circle_black),
            contentDescription = null,
            modifier = Modifier.clickable { })

        Spacer(modifier = Modifier.width(20.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_upload_outline_black),
            contentDescription = null,
            modifier = Modifier.clickable { })
    }
}

@Composable
private fun StoryContentDetailComponent(
    name: String,
    photoUrl: String,
    createdAt: String,
    description: String,
    modifier: Modifier = Modifier,
    isStoryFavorite: Boolean = false,
    onClickFavorite: () -> Unit,
    onNavigateBack: () -> Unit,
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        HeaderComponent(
            isStoryFavorite = isStoryFavorite,
            onClickFavorite = onClickFavorite,
            onNavigateBack = onNavigateBack
        )

        StoryContentComponent(
            storyName = name,
            storyPhotoUrl = photoUrl,
            storyCreatedAt = createdAt,
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp)
            ) {
                StoryContentDetailAdditionalIconComponent(
                    value = "125",
                    icon = com.alfikri.rizky.share.R.drawable.show,
                    modifier = Modifier.padding(end = 6.dp)
                )
                StoryContentDetailAdditionalIconComponent(
                    value = "20",
                    icon = R.drawable.chat,
                    modifier = Modifier.padding(end = 6.dp)
                )
                StoryContentDetailAdditionalIconComponent(
                    value = "125",
                    icon = R.drawable.heart,
                    modifier = Modifier.padding(end = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = description,
            style = Typography.body2.copy(color = TextSecondary, fontSize = 14.sp),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun StoryContentDetailAdditionalIconComponent(
    value: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
) {
    Row {
        Text(
            text = value,
            style = Typography.caption.copy(
                color = Placeholder,
                letterSpacing = TextUnit.Unspecified
            ),
            modifier = modifier
        )
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun StoryContentDetailShimmerComponent(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        HeaderComponent(
            isStoryFavorite = false,
            onClickFavorite = {},
            onNavigateBack = onNavigateBack
        )

        StoryContentShimmerComponent { brush ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp)
            ) {
                StoryContentDetailAdditionalIconComponent(
                    value = "   ",
                    icon = com.alfikri.rizky.share.R.drawable.show,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .background(brush)
                )
                StoryContentDetailAdditionalIconComponent(
                    value = "   ",
                    icon = R.drawable.chat,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .background(brush)
                )
                StoryContentDetailAdditionalIconComponent(
                    value = "   ",
                    icon = R.drawable.heart,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .background(brush)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        for (i in 1..4) {
            ShimmerAnimation { brush ->
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 2.dp)
                        .height(21.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush = brush)
                )
            }
        }
    }
}

@Preview
@Composable
fun StoryDetailScreenPreview() {
//    StoryDetailScreen(storyId = "", onGetStoryDetail = {}, onShowSnackbarErrorMessage = {})
}