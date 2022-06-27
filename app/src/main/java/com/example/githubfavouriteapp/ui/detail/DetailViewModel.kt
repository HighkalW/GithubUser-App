package com.example.githubfavouriteapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.searchgithubuser.data.remote.retrofit.ApiConfig
import com.example.githubfavouriteapp.utils.Event
import com.example.githubfavouriteapp.data.remote.response.ItemsItem
import com.example.githubfavouriteapp.data.local.entity.NewsEntity
import com.example.githubfavouriteapp.data.remote.NewRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (private val newRepository: NewRepository) : ViewModel() {

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    fun getFollowers(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                    _toastText.value = Event("Success")
                } else {
                    _toastText.value = Event("Tidak ada data yang ditampilkan!")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event("onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getFollowing(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    _toastText.value = Event("Data tidak ada yang ditampilkan")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = Event("onFailure: ${t.message.toString()}")
            }
        })
    }
    fun getUser(username: String) = newRepository.getUser(username)

    fun saveFavourite(user: NewsEntity) {
        viewModelScope.launch {
            newRepository.setUsersFavourite(user, true)
        }
    }

    fun deleteFavourite(user: NewsEntity) {
        viewModelScope.launch {
            newRepository.setUsersFavourite(user, false)
        }
    }

    fun getFavouriteUsers() = newRepository.getFavouriteUsers()

    fun deleteAll(){
        viewModelScope.launch {
            newRepository.deleteAll()
        }
    }
}