package com.example.storyappdicoding.ui.add_story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.remote.Repository
import com.example.storyappdicoding.data.remote.response.AddStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading: MutableLiveData<Boolean> = _isLoading

    private val _uploadResult = MutableLiveData<Result<AddStoryResponse>>()
    var uploadResult: MutableLiveData<Result<AddStoryResponse>> = _uploadResult

    fun uploadStory(
        token: String,
        description: RequestBody,
        photo: MultipartBody.Part,
        lat: RequestBody?,
        lon: RequestBody?
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.addStory(token, description, photo, lat, lon)
            _uploadResult.value = result
            _isLoading.value = false
        }
    }
}