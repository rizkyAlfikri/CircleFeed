package com.alfikri.rizky.story.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfikri.rizky.core.presentation.UiState
import com.alfikri.rizky.story.domain.model.StoryResultModel
import com.alfikri.rizky.story.domain.usecase.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version PostStoryViewModel, v 0.1 1/13/2023 5:39 AM by Rizky Alfikri Rachmat
 */
@HiltViewModel
class PostStoryViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase
) : ViewModel() {

    private val _imageState = MutableStateFlow(ImageContainer())
    val imageState: StateFlow<ImageContainer>
        get() = _imageState

    private val _postStoryEventState = MutableLiveData<PostStoryEventState>()
    val postStoryEventState: LiveData<PostStoryEventState>
        get() = _postStoryEventState

    private val _postStoryUiState =
        MutableStateFlow<UiState<StoryResultModel<Nothing>>>(UiState.Empty)
    val postStoryUiState: StateFlow<UiState<StoryResultModel<Nothing>>>
        get() = _postStoryUiState

    fun setImage(
        imageUri: Uri? = null,
        imageBitmap: Bitmap? = null
    ) {
        _imageState.value = ImageContainer(imageUri, imageBitmap)
    }

    fun setUiState(event: PostStoryEventState) {
        _postStoryEventState.value = event
    }

    fun postStory(
        file: MultipartBody.Part,
        description: RequestBody
    ) {
        viewModelScope.launch {
            _postStoryUiState.value = UiState.Loading
            storyUseCase.addStory(file, description).catch { error ->
                _postStoryUiState.value = UiState.Error(error.message.orEmpty())
            }.collect { result ->
                if (result.error == true) {
                    _postStoryUiState.value = UiState.Error(result.message.orEmpty())
                } else {
                    _postStoryUiState.value = UiState.Success(result)
                    delay(1200)
                    _postStoryEventState.value = PostStoryEventState.SuccessUploadImage
                }
            }
        }
    }
}

class ImageContainer(
    val imageUri: Uri? = null,
    val imageBitmap: Bitmap? = null
)

sealed class PostStoryEventState {
    object StartGallery : PostStoryEventState()
    object StartCameraX : PostStoryEventState()
    class StartUploadImage(val description: String) : PostStoryEventState()
    object SuccessUploadImage : PostStoryEventState()
}