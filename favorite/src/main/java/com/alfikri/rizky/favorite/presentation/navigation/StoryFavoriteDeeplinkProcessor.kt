package com.alfikri.rizky.favorite.presentation.navigation

import android.content.Context
import android.content.Intent
import com.alfikri.rizky.core.constant.DeeplinkConstant
import com.alfikri.rizky.core.navigation.DeeplinkProcessor
import com.alfikri.rizky.favorite.presentation.StoryFavoriteActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteDeeplinkProcessor, v 0.1 2/6/2023 7:02 AM by Rizky Alfikri Rachmat
 */
@Singleton
class StoryFavoriteDeeplinkProcessor @Inject constructor(
    private val context: Context
): DeeplinkProcessor {

    override fun matches(deeplink: String): Boolean {
        return deeplink.contains(DeeplinkConstant.STORY_FAVORITE_DEEPLINK)
    }

    override fun execute(deeplink: String) {
        val intent = Intent(context, StoryFavoriteActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}