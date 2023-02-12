package com.alfikri.rizky.story.presentation.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.alfikri.rizky.share.ui.theme.CircleFeedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storyId = intent.getStringExtra(STORY_ID).orEmpty()

        setContent {
            CircleFeedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StoryDetailScreen(
                        storyId = storyId,
                        onNavigateBack = {
                            finish()
                        },
                        onShowErrorMessage = {}
                    )
                }
            }
        }
    }

    companion object {
        const val STORY_ID = "story_id"
    }
}
