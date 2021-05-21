package com.picpay.desafio.android.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("UNCHECKED_CAST")
class UsersViewModel(dataSource: UsersRepository): ViewModel() {
    @ExperimentalCoroutinesApi
    val users = dataSource.getUsers().asLiveData()

    class UsersViewModelFactory(private val dataSource: UsersRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UsersViewModel(dataSource) as T
        }
    }
}