package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers() : Flow<Resource<List<User>>>
}