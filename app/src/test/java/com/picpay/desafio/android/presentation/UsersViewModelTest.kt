package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.dao.UserDao
import com.picpay.desafio.android.data.database.UserDatabase
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.repository.UsersRepository
import com.picpay.desafio.android.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UsersViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Mock
    private lateinit var userLiveDataObserver: Observer<Resource<List<User>>>

    private lateinit var viewModel: UsersViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when view model get users then set users`() = testDispatcher.runBlockingTest {
        val users = listOf<User>()
        viewModel = UsersViewModel(MockRepositorySuccess())
        viewModel.users.observeForever(userLiveDataObserver)

        assertThat(viewModel.users.value.let {
            it!!.data
        }).isEqualTo(users)

    }
}

class MockRepositorySuccess(): UsersRepository {
    override fun getUsers(): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Success(listOf<User>()))
        }
    }
}

class MockRepositoryLoading(resource: Resource<List<User>>): UsersRepository {
    override fun getUsers(): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading())
        }
    }
}

class MockRepositoryError(resource: Resource<List<User>>): UsersRepository {
    override fun getUsers(): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Error(Throwable("Erro")))
        }
    }
}

