package com.picpay.desafio.android.data

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.database.UserDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
    @Volatile
    private var INSTANCE: UserDatabase? = null
    private val gson: Gson by lazy { GsonBuilder().create() }
    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

   fun getDatabase(context: Context): UserDatabase {
       val tempInstance = INSTANCE
       if(tempInstance != null){
           return tempInstance
       }
       synchronized(this){
           val instance = Room.databaseBuilder(
               context.applicationContext,
               UserDatabase::class.java,
               "user_database"
           ).build()
           INSTANCE = instance
           return instance
       }
   }

    val service: PicPayService = initRetrofit().create(PicPayService::class.java)


}