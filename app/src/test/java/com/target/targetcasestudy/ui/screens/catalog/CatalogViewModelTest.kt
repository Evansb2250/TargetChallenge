package com.target.targetcasestudy.ui.screens.catalog

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.data.api.models.dummyDealDto
import com.target.targetcasestudy.data.api.models.toDeal
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.ui.screens.details.DealDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class CatalogViewModelTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var catalogViewModel: CatalogViewModel
    private lateinit var testDispatcher: TestDispatcher

    private val dealDtolist = listOf(dummyDealDto)

    @Before
    fun setUp() {
        productRepository = mock()
        dispatcherProvider = mock()

        testDispatcher = StandardTestDispatcher()

        Mockito.`when`(dispatcherProvider.main).thenReturn(testDispatcher)

        Dispatchers.setMain(testDispatcher)

        catalogViewModel = CatalogViewModel(
            productRepository = productRepository,
            dispatcherProvider = dispatcherProvider,
        )
    }


    @Test
    fun loadData_fail() = runTest {
        Mockito.`when`(productRepository.fetchProducts()).thenReturn(AsyncResponse.Failed())

        catalogViewModel.loadData("2")

        catalogViewModel.state.test {
            awaitItem()

            val result = awaitItem()

            assertThat(result).isEqualTo(
                CatalogScreenStates.Browsing(
                    userId = "2",
                    deals = emptyList(),
                    errorState = ErrorState(
                        isError = true,
                        errorMessage = "failed to retrieve products"
                    )
                )
            )
        }
    }


    @Test
    fun loadData_Success() = runTest {
        Mockito.`when`(productRepository.fetchProducts()).thenReturn(
            AsyncResponse.Success(dealDtolist)
        )

        catalogViewModel.loadData("2")

        catalogViewModel.state.test {
            awaitItem()

            val result = awaitItem()

            assertThat(result).isEqualTo(
                CatalogScreenStates.Browsing(
                    userId = "2",
                    deals = dealDtolist.map { it.toDeal() },
                )
            )
        }
    }


}