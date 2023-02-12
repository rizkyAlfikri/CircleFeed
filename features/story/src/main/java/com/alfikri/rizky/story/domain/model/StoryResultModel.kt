package com.alfikri.rizky.story.domain.model

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryResultModel, v 0.1 12/31/2022 11:03 AM by Rizky Alfikri Rachmat
 */
data class StoryResultModel<T>(
    var error: Boolean? = null,
    var message: String? = null,
    var data: T? = null
)
