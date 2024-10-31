package com.example.storyappdicoding.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyappdicoding.data.remote.Repository
import com.example.storyappdicoding.di.Injection

class RegisterFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: RegisterFactory? = null
        fun getInstance(context: Context): RegisterFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RegisterFactory(
                    Injection.repository(context)
                )
            }.also { INSTANCE = it }
    }
}