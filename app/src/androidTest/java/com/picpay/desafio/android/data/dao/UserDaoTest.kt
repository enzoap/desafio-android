package com.picpay.desafio.android.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.data.database.UserDatabase
import com.picpay.desafio.android.data.getOrAwaitValue
import com.picpay.desafio.android.data.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UserDatabase
    private lateinit var dao: UserDao


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUsers() = runBlockingTest {
//        val users = listOf(
//            User("url", "eduardo", 1, "edu"),
//            User("url", "eduardo", 1, "aaaa")
//        )
//        dao.insertUsers(users)
//        var allUsers = listOf<User>()
//        val ao = dao.getAllUsers()
//         ao.collect{
//             allUsers = it
//        }
//        assertThat(allUsers.contains(users)).isTrue()
    }
}




