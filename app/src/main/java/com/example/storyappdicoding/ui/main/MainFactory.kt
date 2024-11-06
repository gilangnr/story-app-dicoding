package com.example.storyappdicoding.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyappdicoding.data.remote.Repository
import com.example.storyappdicoding.di.Injection

class MainFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: MainFactory? = null
        fun getInstance(context: Context): MainFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MainFactory(
                    Injection.repository(context)
                )
            }.also { INSTANCE = it }
    }
}