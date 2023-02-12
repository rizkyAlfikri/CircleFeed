package com.alfikri.rizky.story.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfikri.rizky.core.presentation.UiState
import com.alfikri.rizky.story.domain.model.StoryModel
import com.alfikri.rizky.story.domain.usecase.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryDetailViewModel, v 0.1 1/8/2023 1:13 PM by Rizky Alfikri Rachmat
 */
@HiltViewModel
class StoryDetailViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase
) : ViewModel() {

    private val _storyDetailState =
        MutableStateFlow<UiState<StoryModel>>(UiState.Loading)
    val storyDetailState: StateFlow<UiState<StoryModel>>
        get() = _storyDetailState

    private val _isStoryFavorite = MutableStateFlow(false)
    val isStoryFavorite: StateFlow<Boolean>
        get() = _isStoryFavorite

    fun getStoryDetail(id: String) {
        viewModelScope.launch {
            _storyDetailState.value = UiState.Loading
            storyUseCase.getStoryDetail(id)
                .catch { error ->
                    _storyDetailState.value = UiState.Error(error.message.toString())
                }.collect { result ->
                    when {
                        result.error == true -> {
                            _storyDetailState.value = UiState.Error(result.message.toString())
                        }

                        result.data == null -> {
                            _storyDetailState.value = UiState.Error(result.message.toString())
                        }

                        else -> {
                            _storyDetailState.value = UiState.Success(result.data!!)
                        }
                    }
                }
        }
    }

    fun getStoryFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            storyUseCase.getStoryFavorite(id).collect { storyFavorite ->
                _isStoryFavorite.value = storyFavorite != null
            }
        }
    }

    fun insertStoryFavorite(storyModel: StoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            storyUseCase.insertStoryFavorite(storyModel)
        }
    }

    fun deleteStoryFavorite(storyModel: StoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            storyUseCase.deleteStoryFavorite(storyModel)
        }
    }
}