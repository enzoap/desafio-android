package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.model.User

sealed class UsersResult {
    class Success(val users: List<User>): UsersResult()
    class ApiError(val StatusCode: Int): UsersResult()
    object ServerError: UsersResult()
}
