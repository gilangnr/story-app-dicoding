package com.example.storyappdicoding.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyappdicoding.data.remote.Repository
import com.example.storyappdicoding.di.Injection

class DetailFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: DetailFactory? = null
        fun getInstance(context: Context): DetailFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DetailFactory(
                    Injection.repository(context)
                )
            }.also { INSTANCE = it }
    }
}