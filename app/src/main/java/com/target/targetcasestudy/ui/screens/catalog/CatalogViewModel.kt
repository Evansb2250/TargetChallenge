package com.target.targetcasestudy.ui.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.data.api.models.DealDto
import com.target.targetcasestudy.data.api.models.toDeal
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem
import com.target.targetcasestudy.ui.screens.catalog.domain.Deal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _state: MutableStateFlow<CatalogScreenStates> =
        MutableStateFlow(CatalogScreenStates.Loading)
    val state: StateFlow<CatalogScreenStates> = _state.asStateFlow()

    private lateinit var userId: String

    fun loadData(id: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            userId = id
            getProducts()
        }
    }

    private suspend fun getProducts() {
        val response = productRepository.fetchProducts()
        _state.update { handleGetProductResponse(response) }
    }

    private fun handleGetProductResponse(
        response: AsyncResponse<List<DealDto>>
    ): CatalogScreenStates.Browsing {
        return when (response) {
            is AsyncResponse.Failed -> {
                CatalogScreenStates.Browsing(
                    userId = userId,
                    deals = emptyList(),
                    errorState = ErrorState(
                        isError = true,
                        errorMessage = response.message ?: "failed to retrieve products"
                    )
                )
            }

            is AsyncResponse.Success -> {
                CatalogScreenStates.Browsing(
                    userId = userId,
                    deals = response.data.map { it.toDeal() },
                )
            }
        }
    }
}