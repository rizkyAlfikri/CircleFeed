package com.alfikri.rizky.story.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alfikri.rizky.share.ui.compose.SearchField
import com.alfikri.rizky.share.ui.theme.CircleFeedTheme
import com.alfikri.rizky.share.ui.theme.FieldBackground
import com.alfikri.rizky.share.ui.theme.PrimaryBlue
import com.alfikri.rizky.share.ui.theme.Tertiary
import com.alfikri.rizky.story.R
import com.alfikri.rizky.story.domain.model.StoryModel
import com.alfikri.rizky.story.domain.model.StoryUserData
import com.alfikri.rizky.story.presentation.state.StoryUiState
import com.alfikri.rizky.story.presentation.state.rememberStoryUiState
import com.alfikri.rizky.story.presentation.ui.home.HomeScreen
import com.alfikri.rizky.story.presentation.ui.map.StoryMapsActivity
import com.alfikri.rizky.story.presentation.ui.poststory.PostStoryActivity
import com.alfikri.rizky.story.presentation.ui.profile.ProfileScreen
import com.alfikri.rizky.story.presentation.utils.Screen
import com.alfikri.rizky.story.presentation.utils.bottomNavigationItems
import com.alfikri.rizky.story.presentation.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CircleFeedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    StoryScreen(onFinishActivity = { finish() })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CircleFeedTheme {
        StoryScreen(onFinishActivity = {})
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StoryScreen(
    storyUiState: StoryUiState = rememberStoryUiState(),
    storyViewModel: StoryViewModel = hiltViewModel(),
    onFinishActivity: () -> Unit
) {

    val navBackStackEntry by storyUiState.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val storyDataState = storyViewModel.storiesState.asFlow().collectAsLazyPagingItems()

    var isRefresh by remember { mutableStateOf(false) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                if (it.resultCode == ComponentActivity.RESULT_OK) {
                    storyDataState.refresh()
                    isRefresh = true
                }
            })

    Scaffold(scaffoldState = storyUiState.scaffoldState,
        topBar = {
            if (currentRoute != Screen.Profile.route) {
                HeaderComponent(
                    searchQuery = storyUiState.searchQuery.value,
                    onSearchQueryChange = {
                        storyUiState.onSearchQueryChange(it)
                    },
                    onSubmit = { },
                    onClickFavorite = {
                        storyUiState.installFavoriteModule()
                    },
                    onClickMap = {
                        val intent =
                            Intent(storyUiState.context, StoryMapsActivity::class.java).apply {
                                putParcelableArrayListExtra(
                                    StoryMapsActivity.STORY_LOCATION_DATA,
                                    storyViewModel.stories
                                )
                            }
                        launcher.launch(intent)

                    },
                    isFocused = storyUiState.isSearchFieldActive.value,
                    onFocusChanged = {
                        storyUiState.onFocusChanged(it)
                    },
                    focusManager = storyUiState.focusManager,
                    keyboardController = storyUiState.keyboardController
                )
            }
        },
        bottomBar = {
            StoryBottomNavigationBar(navController = storyUiState.navController)
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(storyUiState.context, PostStoryActivity::class.java)
                    launcher.launch(intent)
                },
                shape = CircleShape,
                contentColor = Color.White,
                backgroundColor = PrimaryBlue
            ) {
                Card(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    backgroundColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = PrimaryBlue
                    )
                }

            }
        }) {
        StoryContent(navController = storyUiState.navController,
            storiesState = storyDataState,
            isRefresh = isRefresh,
            onRefreshChange = {
                isRefresh = it
            },
            userData = storyViewModel.userData.collectAsState().value,
            onShowHomeScreenErrorMessage = {
                storyUiState.showSnackbarWithAction(it) {
                    storyDataState.refresh()
                }
            },
            navigateToDetail = {
                storyUiState.navigateToDetail(it)
            }
        ) {
            storyViewModel.logout()
            storyUiState.redirectToAuthScreen()
            onFinishActivity()
        }
    }
}

@Composable
fun StoryBottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    BottomAppBar(
        modifier = modifier
            .height(75.dp)
            .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)),
        cutoutShape = CircleShape,
        backgroundColor = Color.White,
        elevation = 22.dp
    ) {
        StoryBottomNavItem(navController = navController)
    }
}

@Composable
fun StoryBottomNavItem(modifier: Modifier = Modifier, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    BottomNavigation(
        modifier = modifier, backgroundColor = Color.White, elevation = 0.dp,
    ) {
        bottomNavigationItems.forEach { screen ->
            val isSelected = currentRoute?.hierarchy?.any { it.route == screen.route } == true
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.unselectedIcon!!),
                        contentDescription = null
                    )
                },
                selected = isSelected,
                selectedContentColor = PrimaryBlue,
                unselectedContentColor = Tertiary,
                onClick = {
                    screen.route?.let { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )

            if (screen.route == Screen.Category.route) Spacer(modifier = Modifier.width(84.dp))
        }
    }
}

@Composable
fun StoryContent(
    navController: NavHostController,
    storiesState: LazyPagingItems<StoryModel>,
    isRefresh: Boolean,
    onRefreshChange: (Boolean) -> Unit,
    userData: StoryUserData,
    navigateToDetail: (String) -> Unit,
    onShowHomeScreenErrorMessage: (String) -> Unit,
    onLogout: () -> Unit,
) {
    NavHost(navController, startDestination = Screen.Home.route!!) {
        composable(Screen.Home.route) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF1F1FE))
            ) {
                HomeScreen(
                    storiesState = storiesState,
                    isRefresh = isRefresh,
                    onRefreshChange = onRefreshChange,
                    navigateToDetail = navigateToDetail,
                    onErrorShowDialog = onShowHomeScreenErrorMessage
                )
            }
        }

        composable(Screen.Category.route!!) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = "Category")
            }      // PickupScreen()
        }

        composable(Screen.Feed.route!!) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = "Feed")
            }
        }

        composable(Screen.Profile.route!!) {
            Box(contentAlignment = Alignment.Center) {
                ProfileScreen(storyUserData = userData, onClickLogout = onLogout)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HeaderComponent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSubmit: (String) -> Unit,
    isFocused: Boolean,
    onFocusChanged: (Boolean) -> Unit,
    onClickFavorite: () -> Unit,
    onClickMap: () -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {

    TopAppBar(
        modifier = modifier
            .background(Color.White)
            .padding(top = 12.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        backgroundColor = Color.White,
        elevation = 0.dp
    ) {
        SearchField(value = searchQuery,
            onValueChange = onSearchQueryChange,
            onFocusChanged = onFocusChanged,
            onSubmit = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onSubmit(searchQuery)
            },
            placeholder = "Search",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.alfikri.rizky.share.R.drawable.search),
                    contentDescription = null,
                    tint = PrimaryBlue
                )
            })

        Spacer(modifier = Modifier.width(16.dp))

        Card(modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable { onClickFavorite() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier
                    .size(16.dp)
                    .background(FieldBackground)
                    .padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Card(modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .clickable { onClickMap() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier
                    .size(16.dp)
                    .background(FieldBackground)
                    .padding(6.dp)
            )
        }

        BackHandler(isFocused) {
            keyboardController?.hide()
            focusManager.clearFocus(force = true)
        }
    }
}