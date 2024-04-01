package com.target.targetcasestudy.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.domain.ErrorState
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _state: MutableStateFlow<DealScreenStates> = MutableStateFlow(DealScreenStates.Loading)
    var state: StateFlow<DealScreenStates> = _state.asStateFlow()

    private lateinit var _userId: String

    fun setUserId(userId: String){
        _userId = userId
    }

    fun loadProductDetails(
        productId: String,
    ) {
        viewModelScope.launch(dispatcherProvider.main) {
            val response = productRepository.fetchProductDetails(productId)
            _state.update {
                handleFetchResponse(response = response)
            }
        }
    }

    fun addToCart(deal: DealDetails) {
        viewModelScope.launch(dispatcherProvider.main) {
            productRepository.addDealToCart(
                cartItem = CartItem(
                    userId = _userId,
                    dealId = "${deal.id}",
                    quantity = 1,
                    title = deal.title,
                    imageUrl = deal.imageUrl,
                    fulfillment = deal.fulfillment,
                    price = deal.salePrice ?: deal.regularPrice
                )
            )
        }
    }

    private fun handleFetchResponse(
        response: AsyncResponse<DealDetails>
    ): DealScreenStates.ProductDetails {
        return when (response) {
            is AsyncResponse.Failed -> DealScreenStates.ProductDetails(
                deal = null,
                errorState = ErrorState(
                    isError = true,
                    errorMessage = response.message ?: "Couldn't find product"
                )
            )

            is AsyncResponse.Success -> DealScreenStates.ProductDetails(
                deal = response.data,
            )
        }
    }

    fun dismissDialog() {
        _state.update {
            DealScreenStates.Error
        }
    }
}