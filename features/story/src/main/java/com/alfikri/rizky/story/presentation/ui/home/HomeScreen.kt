package com.alfikri.rizky.story.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.alfikri.rizky.share.ui.theme.Placeholder
import com.alfikri.rizky.share.ui.theme.PrimaryBlue
import com.alfikri.rizky.share.ui.theme.Typography
import com.alfikri.rizky.story.R
import com.alfikri.rizky.story.domain.model.StoryModel
import com.alfikri.rizky.story.presentation.ui.components.StoryContentComponent
import com.alfikri.rizky.story.presentation.ui.components.StoryContentShimmerComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version HomeScreen, v 0.1 12/29/2022 7:16 AM by Rizky Alfikri Rachmat
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    isRefresh: Boolean,
    onRefreshChange: (Boolean) -> Unit,
    scope: CoroutineScope = rememberCoroutineScope(),
    storiesState: LazyPagingItems<StoryModel>,
    navigateToDetail: (String) -> Unit,
    onErrorShowDialog: (String) -> Unit,
) {
    val lazyState = rememberLazyListState()
    scope.launch {
        if (isRefresh) {
            delay(1000)
            lazyState.animateScrollToItem(0)
            onRefreshChange(false)
        }
    }

    LazyColumn(
        state = lazyState,
        contentPadding = PaddingValues(
            start = 20.dp, end = 20.dp, top = 16.dp, bottom = 100.dp,
        )
    ) {
        items(items = storiesState, key = { it.id.orEmpty() }) { story ->
            HomeContent(
                modifier = modifier,
                story = story,
                navigateToDetail = navigateToDetail,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        when (val state = storiesState.loadState.refresh) { // first load
            is LoadState.Error -> {
                onErrorShowDialog(state.error.message.orEmpty())
            }

            is LoadState.Loading -> {
                item {
                    for (i in 0..5) {
                        HomeShimmerContent(
                            modifier = modifier,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            else -> {
                // No Implementation
            }
        }

        when (val state = storiesState.loadState.append) {
            is LoadState.Error -> {
                onErrorShowDialog(state.error.message.orEmpty())
            }

            is LoadState.Loading -> {
                item {
                    HomeShimmerContent(
                        modifier = modifier,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            else -> {
                // No Implementation
            }
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    story: StoryModel?,
    navigateToDetail: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .clickable {
                navigateToDetail(story?.id.orEmpty())
            },
        shape = RoundedCornerShape(10.dp),
    ) {

        StoryContentComponent(
            modifier = modifier,
            storyName = story?.name.orEmpty(),
            storyPhotoUrl = story?.photoUrl.orEmpty(),
            storyCreatedAt = story?.createdAt.orEmpty(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus_circle),
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "-", style = Typography.caption.copy(
                        color = Placeholder, letterSpacing = TextUnit.Unspecified
                    ), modifier = Modifier.padding(end = 6.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "-", style = Typography.caption.copy(
                        color = Placeholder, letterSpacing = TextUnit.Unspecified
                    ), modifier = Modifier.padding(start = 16.dp, end = 6.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun HomeShimmerContent(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
        shape = RoundedCornerShape(10.dp),
    ) {
        StoryContentShimmerComponent { brush ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
            ) {

                Spacer(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(brush = brush)
                )

                Spacer(modifier = Modifier.weight(1f))

                Spacer(
                    modifier = Modifier
                        .width(104.dp)
                        .height(21.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush = brush)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    HomeContent(story = StoryModel(), navigateToDetail = {})
}