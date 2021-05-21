package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.ApiService
import com.picpay.desafio.android.data.UsersResult
import com.picpay.desafio.android.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersApiDataSource {
     fun getUsers(userResultCallback: (userResult: UsersResult) -> Unit) {
        ApiService.service.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                when {
                    response.isSuccessful -> {
                        val statsResponse = mutableListOf<User>()

                        response.body()?.let {
                            for (result in it){
                                val user = User(result.img, result.name, result.id, result.username)
                                statsResponse.add(user)
                            }
                        }
                        userResultCallback(UsersResult.Success(statsResponse))
                    }
                    else -> userResultCallback(UsersResult.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                userResultCallback(UsersResult.ServerError)
            }

        })
    }

}