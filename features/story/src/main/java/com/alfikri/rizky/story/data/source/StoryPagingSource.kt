package com.alfikri.rizky.story.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alfikri.rizky.core.data.remote.response.StoryResult
import com.alfikri.rizky.story.domain.model.StoryModel

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryPagingSource, v 0.1 1/29/2023 7:25 PM by Rizky Alfikri Rachmat
 */
class StoryPagingSource(
    private val dataSource: StoryDataSource,
    private val onStoryModelCallback: (List<StoryModel>) -> Unit
) :
    PagingSource<Int, StoryModel>() {

    override fun getRefreshKey(state: PagingState<Int, StoryModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryModel> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData =
                dataSource.getPagingStories(position, params.loadSize).stories?.map { it.toStoryModel() }
            responseData?.let { onStoryModelCallback(it) }

            LoadResult.Page(
                data = responseData.orEmpty(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun StoryResult.toStoryModel(): StoryModel = StoryModel(
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        createdAt = createdAt,
        lat = lat,
        lon = lon,
    )

    companion object {

        const val INITIAL_PAGE_INDEX = 1
    }
}