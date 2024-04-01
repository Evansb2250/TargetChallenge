package com.target.targetcasestudy.ui.screens.signup

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.DispatcherProviderImp
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

class SignUpViewModelTest {


    private lateinit var userRepository: UserRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var testDispatcher: TestDispatcher

    private val username = "John"
    private val password = "Smith"

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        dispatcherProvider = mock()
        userRepository = mock()

        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        Mockito.`when`(dispatcherProvider.main).thenReturn(testDispatcher)

        signUpViewModel = SignUpViewModel(userRepository, dispatcherProvider)
    }

    @Test
    fun testRegisterUser_Success() = runTest {
        // Given
        Mockito.`when`(userRepository.createUser(username, password))
            .thenReturn(AsyncResponse.Success("User created!!"))

        //When
        signUpViewModel.registerUser(username, password)

        // Then
        signUpViewModel.state.test {
            //Initial State


            awaitItem()

            val newState = awaitItem()

            assertThat(newState).isEqualTo(SignUpStates.Registered)
        }
    }

    @Test
    fun testRegisterUser_Failure() = runTest {

        val expectedResult = SignUpStates.Registering(
            errorState = ErrorState(
                isError = true,
                errorMessage = "Couldn't register account"
            )
        )

        // Given
        // Mockito.`when`(dispatcherProvider.main).thenReturn(Dispatchers.Unconfined)
        Mockito.`when`(userRepository.createUser(username, password))
            .thenReturn(AsyncResponse.Failed("Error"))

        // When
        signUpViewModel.registerUser(username, password)

        signUpViewModel.state.test {


            //Initial State
            awaitItem()

            val afterRegisterUserCall = awaitItem()

            assertThat(afterRegisterUserCall).isEqualTo(expectedResult)
        }
    }


    @Test
    fun testReset() {
        // When
        signUpViewModel.reset()

        // Then
        runBlocking {
            signUpViewModel.state.collect { state ->
                Assert.assertTrue(state is SignUpStates.Registering)
            }
        }
    }
}