package com.example.githubfavouriteapp.ui.favourite

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfavouriteapp.data.remote.response.ItemsItem
import com.example.githubfavouriteapp.ListUserAdapter
import com.example.githubfavouriteapp.R
import com.example.githubfavouriteapp.databinding.ActivityFavouriteBinding
import com.example.githubfavouriteapp.ui.detail.DetailUserActivity
import com.example.githubfavouriteapp.ui.detail.DetailViewModel
import com.example.githubfavouriteapp.ui.detail.ViewModelFactory

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.app_name3)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: DetailViewModel by viewModels {
            factory
        }
        viewModel.deleteAll()
        viewModel.getFavouriteUsers().observe(this) { user ->
            binding.progressBar.visibility = View.GONE
            val userList = user.map {
                ItemsItem(it.username, it.avatar)
            }
            val userAdapter = ListUserAdapter(userList as ArrayList<ItemsItem>)
            binding.rvUser.adapter = userAdapter
            userAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    showSelectedUser(data)
                }
            })
        }
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        binding.rvUser.setHasFixedSize(true)
    }

    private fun showSelectedUser(user: ItemsItem) {
        val detailUserIntent = Intent(this, DetailUserActivity::class.java)
        detailUserIntent.putExtra(DetailUserActivity.EXTRA_USER, user.username)
        startActivity(detailUserIntent)
    }
}