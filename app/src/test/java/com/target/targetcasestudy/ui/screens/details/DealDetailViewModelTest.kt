package com.target.targetcasestudy.ui.screens.details

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.ui.screens.details.domain.dummyDealDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class DealDetailViewModelTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var dealDetailViewModel: DealDetailViewModel
    private lateinit var testDispatcher: TestDispatcher

    private val userId = "123"
    private val productId = "12"

    @Before
    fun setUp() {
        productRepository = mock()
        dispatcherProvider = mock()

        testDispatcher = StandardTestDispatcher()

        Mockito.`when`(dispatcherProvider.main).thenReturn(testDispatcher)

        Dispatchers.setMain(testDispatcher)

        dealDetailViewModel = DealDetailViewModel(
            productRepository = productRepository,
            dispatcherProvider = dispatcherProvider,
        )

        dealDetailViewModel.setUserId(userId)
    }

    @Test
    fun loadProductDetails_Failed() = runTest {
        Mockito.`when`(productRepository.fetchProductDetails(productId))
            .thenReturn(AsyncResponse.Failed())

        dealDetailViewModel.loadProductDetails(productId)

        dealDetailViewModel.state.test {
            //initialState
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(DealScreenStates.Loading)

            //updatedState
            val updatedState = awaitItem()
            assertThat(updatedState).isEqualTo(
                DealScreenStates.ProductDetails(
                    deal = null,
                    errorState = ErrorState(
                        isError = true,
                        errorMessage = "Couldn't find product"
                    )
                )
            )
        }
    }

    @Test
    fun loadProductDetails_Success() = runTest {
        Mockito.`when`(productRepository.fetchProductDetails(productId))
            .thenReturn(AsyncResponse.Success(dummyDealDetails))

        dealDetailViewModel.loadProductDetails(productId)

        dealDetailViewModel.state.test {
            //initialState
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(DealScreenStates.Loading)

            //updatedState
            val updatedState = awaitItem()
            assertThat(updatedState).isEqualTo(
                DealScreenStates.ProductDetails(
                    deal = dummyDealDetails,
                )
            )
        }
    }
}