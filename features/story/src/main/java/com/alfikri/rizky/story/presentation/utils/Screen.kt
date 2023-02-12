package com.alfikri.rizky.story.presentation.utils

import androidx.annotation.DrawableRes
import com.alfikri.rizky.story.R

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version Screen, v 0.1 12/28/2022 7:47 PM by Rizky Alfikri Rachmat
 */
sealed class Screen(
    val route: String?,
    @DrawableRes val selectedIcon: Int?,
    @DrawableRes val unselectedIcon: Int?
) {

    object Home : Screen("home", R.drawable.home_selected, R.drawable.home_unselected)

    object Category :
        Screen("category", R.drawable.category_selected, R.drawable.category_unselected)

    object Feed : Screen("feed", R.drawable.feeds_selected, R.drawable.feeds_unselected)

    object Profile :
        Screen("profile", R.drawable.profile_selected, R.drawable.profile_unselected)

}

val bottomNavigationItems = listOf(Screen.Home, Screen.Category, Screen.Feed, Screen.Profile)