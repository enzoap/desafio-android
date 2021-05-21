package com.picpay.desafio.android.data.repository

import androidx.room.withTransaction
import com.picpay.desafio.android.data.ApiService
import com.picpay.desafio.android.data.database.UserDatabase
import com.picpay.desafio.android.utils.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.await

class UserRepository(private val db: UserDatabase): UsersRepository {
    private val userDao = db.userDao()

    @ExperimentalCoroutinesApi
   override fun getUsers() = networkBoundResource(
        query = {
            userDao.getAllUsers()
        },
        fetch = {
            ApiService.service.getUsers()
        },
        saveFetchResult = { call ->
           db.withTransaction {
               userDao.deleteAllUsers()
               userDao.insertUsers(call.await())
           }
        }
    )

}