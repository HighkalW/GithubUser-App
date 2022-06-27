package com.example.githubfavouriteapp.di

import android.content.Context
import com.dicoding.searchgithubuser.data.remote.retrofit.ApiConfig
import com.example.githubfavouriteapp.data.local.room.UsersDatabase
import com.example.githubfavouriteapp.data.remote.NewRepository


object Injection {
    fun provideRepository(context: Context): NewRepository {
        val apiService = ApiConfig.getApiService()
        val database = UsersDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewRepository.getInstance(apiService, dao)
    }
}