package com.alfikri.rizky.story.presentation.state

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alfikri.rizky.core.constant.DeeplinkConstant
import com.alfikri.rizky.story.presentation.ui.detail.StoryDetailActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryUiState, v 0.1 1/5/2023 7:26 PM by Rizky Alfikri Rachmat
 */

abstract class BaseStoryUiState(
    val scaffoldState: ScaffoldState,
    private val scope: CoroutineScope,
    val context: Context
) {

    fun showSnackbarWithAction(message: String, action: () -> Unit) {
        scope.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Retry"
            )

            if (result == SnackbarResult.ActionPerformed) {
                action()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
class StoryUiState(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    context: Context,
    val navController: NavHostController,
    val focusManager: FocusManager,
    val keyboardController: SoftwareKeyboardController?,
    searchQuery: String,
    isSearchFieldActive: Boolean,
) : BaseStoryUiState(scaffoldState, scope, context) {

    val searchQuery = mutableStateOf(searchQuery)
    val isSearchFieldActive = mutableStateOf(isSearchFieldActive)

    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun onFocusChanged(isFocus: Boolean) {
        isSearchFieldActive.value = isFocus
    }

    fun redirectToAuthScreen() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(DeeplinkConstant.AUTHENTICATION_DEEPLINK)
        )
        context.startActivity(intent)
    }

    fun navigateToDetail(storyId: String) {
        val intent = Intent(
            context, StoryDetailActivity::class.java
        ).apply {
            putExtra(StoryDetailActivity.STORY_ID, storyId)
        }
        context.startActivity(intent)
    }

    fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(context)
        val moduleChat = "favorite"
        if (splitInstallManager.installedModules.contains(moduleChat)) {
            moveToFavoriteActivity()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleChat)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        "Success installing favorite module",
                        Toast.LENGTH_SHORT
                    ).show()
                    moveToFavoriteActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error installing favorite module", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun moveToFavoriteActivity() {
        context.startActivity(
            Intent(
                context,
                Class.forName("com.alfikri.rizky.favorite.presentation.StoryFavoriteActivity")
            )
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberStoryUiState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    searchQuery: String = "",
    isSearchFieldActive: Boolean = false,
): StoryUiState = remember(
    scaffoldState,
    scaffoldState,
    navController,
    context,
    focusManager,
    keyboardController,
    searchQuery,
    isSearchFieldActive,
) {
    StoryUiState(
        scaffoldState,
        scope,
        context,
        navController,
        focusManager,
        keyboardController,
        searchQuery,
        isSearchFieldActive
    )
}