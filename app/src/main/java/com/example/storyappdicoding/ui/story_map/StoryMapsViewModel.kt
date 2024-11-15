package com.example.storyappdicoding.ui.story_map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.remote.Repository
import com.example.storyappdicoding.data.remote.response.ListStoryItem
import kotlinx.coroutines.launch

class StoryMapsViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _stories = MutableLiveData<Result<List<ListStoryItem>>>()
    val stories: LiveData<Result<List<ListStoryItem>>> = _stories

    fun getAllStoriesWithMap(token: String, location: Int = 1) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getStoryWithMap(token, location)
            _stories.value = result
            _isLoading.value = false
        }
    }
}