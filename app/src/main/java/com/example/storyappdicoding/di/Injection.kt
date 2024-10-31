package com.example.storyappdicoding.di

import android.content.Context
import com.example.storyappdicoding.data.remote.Repository
import com.example.storyappdicoding.data.remote.retrofit.ApiConfig


object Injection {
    fun repository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}