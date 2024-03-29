package com.target.targetcasestudy.data.repo

import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.serverErrorMessage
import com.target.targetcasestudy.data.api.models.DealDto
import com.target.targetcasestudy.data.api.DealApiService
import com.target.targetcasestudy.data.api.models.toDealDetail
import com.target.targetcasestudy.data.room.dao.DealsDao
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem
import com.target.targetcasestudy.ui.screens.cart.domain.toCartItemEntity
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImp @Inject constructor(
    private val dealsDao: DealsDao,
    private val productService: DealApiService,
    private val dispatcherProvider: DispatcherProvider,
) : ProductRepository {
    override suspend fun fetchProducts(): AsyncResponse<List<DealDto>> {
        return withContext(dispatcherProvider.io) {
            try {
                AsyncResponse.Success(data = productService.retrieveDeals().deals)
            } catch (e: Exception) {
                AsyncResponse.Failed(message = e.message ?: serverErrorMessage)
            }
        }
    }

    override suspend fun fetchProductDetails(id: String): AsyncResponse<DealDetails> {
        return withContext(dispatcherProvider.io) {
            try {
                AsyncResponse.Success(data = productService.retrieveDeal(id).toDealDetail())
            } catch (e: Exception) {
                AsyncResponse.Failed(message = e.message ?: serverErrorMessage)
            }
        }
    }

    override suspend fun clearCart(userId: String) {
        withContext(dispatcherProvider.io) {
            //TODO("need to implement this ")
        }
    }

    override suspend fun getCartCount(userId: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun addDealToCart(cartItem: CartItem): AsyncResponse<String> {
        return withContext(dispatcherProvider.io) {
            try {
                dealsDao.insertDealInCart(cartItem.toCartItemEntity())
                AsyncResponse.Success(data = "Item added to cart!")
            } catch (e: Exception) {
                AsyncResponse.Failed(message = "Item couldn't be added")
            }
        }
    }
}