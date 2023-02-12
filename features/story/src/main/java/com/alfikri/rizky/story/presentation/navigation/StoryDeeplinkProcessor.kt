package com.alfikri.rizky.story.presentation.navigation

import android.content.Context
import android.content.Intent
import com.alfikri.rizky.core.constant.DeeplinkConstant
import com.alfikri.rizky.core.navigation.DeeplinkProcessor
import com.alfikri.rizky.story.presentation.StoryActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryDeeplinkProcessor, v 0.1 12/28/2022 9:10 PM by Rizky Alfikri Rachmat
 */
@Singleton
class StoryDeeplinkProcessor @Inject constructor(
    private val context: Context
): DeeplinkProcessor {

    override fun matches(deeplink: String): Boolean {
        return deeplink.contains(DeeplinkConstant.STORY_DEEPLINK)
    }

    override fun execute(deeplink: String) {
        val intent = Intent(context, StoryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}