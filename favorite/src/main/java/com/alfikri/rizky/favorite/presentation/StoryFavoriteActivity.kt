package com.alfikri.rizky.favorite.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.alfikri.rizky.circlefeed.di.FavoriteModuleDependency
import com.alfikri.rizky.core.domain.model.StoryFavoriteModel
import com.alfikri.rizky.core.presentation.utils.DateFormat
import com.alfikri.rizky.favorite.presentation.di.DaggerStoryFavoriteComponent
import com.alfikri.rizky.favorite.presentation.viewmodel.StoryFavoriteViewModel
import com.alfikri.rizky.favorite.presentation.viewmodel.StoryFavoriteViewModelFactory
import com.alfikri.rizky.share.ui.theme.*
import com.alfikri.rizky.story.R
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class StoryFavoriteActivity : ComponentActivity() {

    @Inject
    lateinit var factory: StoryFavoriteViewModelFactory

    private val storyFavoriteViewModel: StoryFavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerStoryFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependency::class.java
                )
            ).build()
            .inject(this)

        setContent {
            CircleFeedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StoryFavoriteScreen(
                        viewModel = storyFavoriteViewModel,
                        navigateToDetail = {
                            moveToStoryDetailActivity(it)
                        },
                        onNavigateBack = {
                            finish()
                        }
                    )
                }
            }
        }
    }

    private fun moveToStoryDetailActivity(storyId: String) {
        startActivity(
            Intent(
                this,
                Class.forName("com.alfikri.rizky.story.presentation.ui.detail.StoryDetailActivity")
            ).apply {
                putExtra("story_id", storyId)
            }
        )
    }
}

@Composable
fun StoryFavoriteScreen(
    viewModel: StoryFavoriteViewModel,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        backgroundColor = FieldBackground,
        modifier = modifier,
        topBar = {
            HeaderComponent(onNavigateBack = onNavigateBack)
        }) { padding ->

        val storyState = viewModel.storiesState.collectAsLazyPagingItems()

        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(
                start = 20.dp, end = 20.dp, top = 16.dp, bottom = 100.dp,
            )
        ) {
            items(items = storyState, key = { it.id.orEmpty() }) { story ->
                StoryFavoriteContent(
                    modifier = modifier,
                    story = story,
                    navigateToDetail = navigateToDetail,
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}

@Composable
private fun HeaderComponent(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = "Story Favorite", style = Typography.h1)
        },
        navigationIcon = {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }
        },
        backgroundColor = Color.White,
        elevation = 0.dp,
    )
}

@Composable
private fun StoryFavoriteContent(
    modifier: Modifier = Modifier,
    story: StoryFavoriteModel?,
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
