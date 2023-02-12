package com.alfikri.rizky.story.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alfikri.rizky.story.domain.model.StoryModel
import com.alfikri.rizky.story.domain.model.StoryUserData
import com.alfikri.rizky.story.domain.usecase.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryViewModel, v 0.1 12/31/2022 11:32 AM by Rizky Alfikri Rachmat
 */
@HiltViewModel
class StoryViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase
) : ViewModel() {

    val storiesState: LiveData<PagingData<StoryModel>>
        get() = storyUseCase.getPagingStories().cachedIn(viewModelScope)

    val stories = arrayListOf<StoryModel>()

    init {
        getStoryData()
    }

    private fun getStoryData() {
        viewModelScope.launch(Dispatchers.IO) {
            storyUseCase.getStories().collect { data ->
                data.forEach {
                    stories.add(it)
                }
            }
        }
    }

    val userData: StateFlow<StoryUserData>
        get() = _email
    private val _email = MutableStateFlow(storyUseCase.getUserData())

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            storyUseCase.logout()
        }
    }
}