package com.alfikri.rizky.story.utils

import com.alfikri.rizky.story.domain.model.StoryModel

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version DataDummy, v 0.1 1/30/2023 10:08 PM by Rizky Alfikri Rachmat
 */
object DataDummy {

    fun generateDummyStoryModel(): List<StoryModel> {
        val items: MutableList<StoryModel> = arrayListOf()
        for (i in 0..100) {
            val story = StoryModel(
                id = "id $i",
                name = "name $i",
                description = "description $i",
                photoUrl = "photoUrl $i",
                createdAt = "createdAt $i",
            )
            items.add(story)
        }
        return items
    }
}