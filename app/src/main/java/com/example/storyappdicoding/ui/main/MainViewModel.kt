package com.example.storyappdicoding.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.data.remote.Repository
import com.example.storyappdicoding.data.remote.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

   fun stories(token: String): LiveData<PagingData<ListStoryItem>> {
       _isLoading.value = true
       return repository.getStoriesPaging(token)
           .cachedIn(viewModelScope)
           .also {
               _isLoading.value = false
           }
   }


}