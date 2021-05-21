package com.picpay.desafio.android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.data.ApiService
import com.picpay.desafio.android.data.dao.UserDao
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("COMPATIBILITY_WARNING")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userDao: UserDao
    private lateinit var repository: UserRepository

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDao = ApiService.getDatabase(this).userDao()
        repository = UserRepository(ApiService.getDatabase(this))

        val viewModel = ViewModelProvider(
            this,
            UsersViewModel.UsersViewModelFactory(repository)
        ).get(UsersViewModel::class.java)

        viewModel.users.observe(this) { result ->
            with(binding.recyclerView) {
                layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
                adapter = result.data?.let { data -> UserListAdapter(data) }
            }
            if(result is Resource.Loading && result.data.isNullOrEmpty()){
                binding.viewFlipper.displayedChild = VIEW_LOADING
            }else if (result is Resource.Error && result.data.isNullOrEmpty()){
                binding.viewFlipper.displayedChild = VIEW_ERROR
            }else {
                binding.viewFlipper.displayedChild = VIEW_LIST
            }
        }
    }

    companion object {
        private const val VIEW_LOADING = 0
        private const val VIEW_LIST = 1
        private const val VIEW_ERROR = 2
    }
}
