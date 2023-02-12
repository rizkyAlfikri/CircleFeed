package com.alfikri.rizky.story.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.alfikri.rizky.story.domain.model.StoryModel
import com.alfikri.rizky.story.domain.usecase.StoryInteractor
import com.alfikri.rizky.story.domain.usecase.StoryUseCase
import com.alfikri.rizky.story.utils.DataDummy
import com.alfikri.rizky.story.utils.MainDispatcherRule
import com.alfikri.rizky.story.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryViewModelTest, v 0.1 1/30/2023 9:48 PM by Rizky Alfikri Rachmat
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    private val storyUseCase: StoryUseCase = mock(StoryInteractor::class.java)

    @Test
    fun `when Get Story Should Not Null and Return Data`() = runTest {
        val dummyQuote = DataDummy.generateDummyStoryModel()
        val data: PagingData<StoryModel> = StoryPagingSource.snapshot(dummyQuote)
        val expectedStory = MutableLiveData<PagingData<StoryModel>>()
        expectedStory.value = data
        `when`(storyUseCase.getPagingStories()).thenReturn(expectedStory)

        val mainViewModel = StoryViewModel(storyUseCase)
        val actualStory: PagingData<StoryModel> = mainViewModel.storiesState.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = storyDifUtil,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyQuote.size, differ.snapshot().size)
        assertEquals(dummyQuote[0].id, differ.snapshot()[0]?.id)
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {
        val data: PagingData<StoryModel> = PagingData.from(emptyList())
        val expectedQuote = MutableLiveData<PagingData<StoryModel>>()
        expectedQuote.value = data
        `when`(storyUseCase.getPagingStories()).thenReturn(expectedQuote)
        val mainViewModel = StoryViewModel(storyUseCase)
        val actualQuote: PagingData<StoryModel> = mainViewModel.storiesState.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = storyDifUtil,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)
        assertEquals(0, differ.snapshot().size)
    }
}

private val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

private val storyDifUtil = object : DiffUtil.ItemCallback<StoryModel>() {
    override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
        return oldItem.id == newItem.id
    }
}

class StoryPagingSource : PagingSource<Int, LiveData<List<StoryModel>>>() {
    companion object {

        fun snapshot(items: List<StoryModel>): PagingData<StoryModel> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryModel>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryModel>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}