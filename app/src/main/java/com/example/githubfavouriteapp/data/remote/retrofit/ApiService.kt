package com.example.githubfavouriteapp.data.remote.retrofit



import com.example.githubfavouriteapp.data.remote.response.ItemsItem
import com.example.githubfavouriteapp.data.remote.response.Search
import com.example.githubfavouriteapp.data.remote.response.SearchDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<Search>


    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): SearchDetail

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String?
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String?
    ): Call<List<ItemsItem>>
}