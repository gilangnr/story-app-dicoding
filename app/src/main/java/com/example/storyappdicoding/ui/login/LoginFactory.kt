package com.example.storyappdicoding.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyappdicoding.data.remote.Repository
import com.example.storyappdicoding.di.Injection

class LoginFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginFactory? = null
        fun getInstance(context: Context): LoginFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LoginFactory(
                    Injection.repository(context)
                )
            }.also { INSTANCE = it }
    }
}