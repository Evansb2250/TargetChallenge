package com.target.targetcasestudy.ui.screens.landing

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.data.room.models.dummyUserEntity
import com.target.targetcasestudy.data.room.models.toUser
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.interfaces.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


class LandingPageViewModelTest {
    private lateinit var userRepository: UserRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var landingPageViewModel: LandingPageViewModel

    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()

        userRepository = mock()
        productRepository = mock()
        dispatcherProvider = mock()

        Dispatchers.setMain(testDispatcher)

        Mockito.`when`(dispatcherProvider.main).thenReturn(testDispatcher)

        landingPageViewModel = LandingPageViewModel(
            userRepository = userRepository,
            productRepository = productRepository,
            dispatcherProvider = dispatcherProvider,
        )
    }


    @Test
    fun loadDetails_Failed() = runTest {
        Mockito.`when`(userRepository.authenticateUserId(23)).thenReturn(AsyncResponse.Failed())

        landingPageViewModel.loadDetails(23)

        landingPageViewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(LandingScreenStates.Loading)

            val updatedState = awaitItem()
            assertThat(updatedState).isEqualTo(LandingScreenStates.Logout)
        }
    }

    @Test
    fun loadDetails_Succeed() = runTest {
        Mockito.`when`(userRepository.authenticateUserId(23))
            .thenReturn(AsyncResponse.Success(dummyUserEntity))

        Mockito.`when`(productRepository.getCartCount("0")).thenReturn(0)

        landingPageViewModel.loadDetails(23)

        landingPageViewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(LandingScreenStates.Loading)

            val updatedState = awaitItem()
            assertThat(updatedState).isEqualTo(
                LandingScreenStates.LoggedIn(
                    currentUser = dummyUserEntity.toUser(),
                    cartItems = 0
                )
            )
        }
    }


    @Test
    fun logout_Succeed() = runTest {
        Mockito.`when`(userRepository.authenticateUserId(23))
            .thenReturn(AsyncResponse.Success(dummyUserEntity))

        Mockito.`when`(productRepository.getCartCount("0")).thenReturn(0)

        landingPageViewModel.loadDetails(23)

        landingPageViewModel.logout()

        landingPageViewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(LandingScreenStates.Loading)

            val updatedState = awaitItem()
            assertThat(updatedState).isEqualTo(
                LandingScreenStates.LoggedIn(
                    currentUser = dummyUserEntity.toUser(),
                    cartItems = 0
                )
            )

            val logoutResult = awaitItem()

            assertThat(logoutResult).isEqualTo(LandingScreenStates.Logout)
        }
    }


    @Test
    fun delete_Succeed() = runTest {
        Mockito.`when`(userRepository.authenticateUserId(23))
            .thenReturn(AsyncResponse.Success(dummyUserEntity))

        Mockito.`when`(productRepository.getCartCount("0")).thenReturn(0)

        landingPageViewModel.loadDetails(23)

        landingPageViewModel.deleteUser("23")

        landingPageViewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(LandingScreenStates.Loading)

            val updatedState = awaitItem()
            assertThat(updatedState).isEqualTo(
                LandingScreenStates.LoggedIn(
                    currentUser = dummyUserEntity.toUser(),
                    cartItems = 0
                )
            )

            val logoutResult = awaitItem()

            assertThat(logoutResult).isEqualTo(LandingScreenStates.Logout)
        }
    }



}