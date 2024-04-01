package com.target.targetcasestudy.ui.screens.login

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.data.room.models.UserEntity
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class LoginViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var loginViewModel: LoginViewModel

    //Testable Arguments
    private val userName = "Target"
    private val password = "interview"

    private val asyncSuccessResponse: AsyncResponse.Success<UserEntity?> = AsyncResponse.Success(data = UserEntity(userId = 2, "", ""))

    @Before
    fun setUp() {
        userRepository = mock()
        dispatcherProvider = mock()

        val testDispatcher = StandardTestDispatcher()

        Dispatchers.setMain(testDispatcher)

        //Set up mock replacement
        Mockito.`when`(dispatcherProvider.main).thenReturn(testDispatcher)

        loginViewModel = LoginViewModel(
            userRepository = userRepository,
            dispatcherProvider = dispatcherProvider
        )
    }

    @Test
    fun initialStateTest_Success() = runTest {
        loginViewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(LoginScreenStates.InProgress())
        }
    }

    @Test
    fun loadCredentials_Failed() = runTest {
        Mockito.`when`(userRepository.getCurrentUserId())
            .thenReturn(AsyncResponse.Failed(message = "Couldn't"))

        loginViewModel.loadCredentials()

        loginViewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(LoginScreenStates.InProgress())

            advanceTimeBy(1000)

            //does not emit a new value since it is the same state after the loadCredentials()
            expectNoEvents()
        }

    }


    @Test
    fun loadCredentials_Success() = runTest {
        Mockito.`when`(userRepository.getCurrentUserId())
            .thenReturn(asyncSuccessResponse)

        loginViewModel.loadCredentials()

        loginViewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(LoginScreenStates.InProgress())

            val updatedState = awaitItem()

            assertThat(updatedState).isEqualTo(LoginScreenStates.Login(userId = asyncSuccessResponse.data!!.userId))
        }

    }

    @Test
    fun signIn_Failed() = runTest {
        val expectedResult = LoginScreenStates.InProgress(
            errorState = ErrorState(
                isError = true,
                errorMessage = "Failed login",
            ),
        )

        Mockito.`when`(userRepository.getUser(userName, password))
            .thenReturn(AsyncResponse.Failed())
        loginViewModel.signIn(userName, password)

        loginViewModel.state.test {
            //Initial state ignore
            awaitItem()

            val updatedState = awaitItem()

            assertThat(updatedState).isEqualTo(expectedResult)

        }
    }


    @Test
    fun signIn_Success() = runTest {
        val expectedResult = LoginScreenStates.InProgress(
            errorState = ErrorState(
                isError = true,
                errorMessage = "Failed login",
            ),
        )

        Mockito.`when`(userRepository.getUser(userName, password))
            .thenReturn(asyncSuccessResponse)
        loginViewModel.signIn(userName, password)

        loginViewModel.state.test {
            //Initial state ignore
            awaitItem()

            val updatedState = awaitItem()

            assertThat(updatedState).isEqualTo(LoginScreenStates.Login(userId = asyncSuccessResponse.data!!.userId))

        }
    }


}