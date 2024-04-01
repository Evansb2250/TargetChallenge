package com.target.targetcasestudy.ui.screens.cart

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.ui.screens.cart.domain.dummyCartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class CartViewModelTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var cartViewModel: CartViewModel

    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()

        productRepository = Mockito.mock()
        dispatcherProvider = Mockito.mock()
        Dispatchers.setMain(testDispatcher)

        Mockito.`when`(dispatcherProvider.main).thenReturn(testDispatcher)

        cartViewModel = CartViewModel(
            productRepository = productRepository,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Test
    fun loadData_failed() = runTest {
        cartViewModel.loadCartData()

        cartViewModel.state.test {
            val initialState = awaitItem()

            assertThat(initialState).isEqualTo(CartScreenStates.Loading)

            val afterLoadCartData = awaitItem()

            assertThat(afterLoadCartData).isEqualTo(
                CartScreenStates.CartView(
                    products = emptyList(),
                    cartTotalInPennies = 0,
                    errorState = ErrorState(
                        isError = true,
                        errorMessage = "userId not found",
                    )
                )
            )
        }
    }


    @Test
    fun loadData_Succeed_No_Items() = runTest {
        cartViewModel.setCartId("23")
        cartViewModel.loadCartData()

        Mockito.`when`(productRepository.getCartItems("23")).thenReturn(
            AsyncResponse.Success(
                emptyList()
            )
        )

        cartViewModel.state.test {
            val initialState = awaitItem()

            assertThat(initialState).isEqualTo(CartScreenStates.Loading)

            val afterLoadCartData = awaitItem()

            assertThat(afterLoadCartData).isEqualTo(
                CartScreenStates.CartView(
                    products = emptyList(),
                    cartTotalInPennies = 0,
                )
            )
        }
    }


    @Test
    fun loadData_Succeed_1_Items() = runTest {
        cartViewModel.setCartId("23")
        cartViewModel.loadCartData()

        Mockito.`when`(productRepository.getCartItems("23")).thenReturn(
            AsyncResponse.Success(
                listOf(
                    dummyCartItem.copy(
                        userId = "23"
                    )
                )
            )
        )

        cartViewModel.state.test {
            val initialState = awaitItem()

            assertThat(initialState).isEqualTo(CartScreenStates.Loading)

            val afterLoadCartData = awaitItem()

            assertThat(afterLoadCartData).isEqualTo(
                CartScreenStates.CartView(
                    products = listOf(
                        dummyCartItem.copy("23")
                    ),
                    cartTotalInPennies = listOf(
                        dummyCartItem.copy("23")
                    ).sumOf { it.quantity * it.price.amountInCents },
                )
            )
        }
    }
}