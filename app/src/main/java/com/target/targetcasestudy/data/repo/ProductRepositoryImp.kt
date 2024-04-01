package com.target.targetcasestudy.data.repo

import com.target.targetcasestudy.core.AsyncResponse
import com.target.targetcasestudy.core.serverErrorMessage
import com.target.targetcasestudy.data.api.models.DealDto
import com.target.targetcasestudy.data.api.DealApiService
import com.target.targetcasestudy.data.api.models.toDealDetail
import com.target.targetcasestudy.data.room.dao.DealsDao
import com.target.targetcasestudy.data.room.models.toCartItem
import com.target.targetcasestudy.interfaces.DispatcherProvider
import com.target.targetcasestudy.interfaces.ProductRepository
import com.target.targetcasestudy.ui.screens.cart.domain.CartItem
import com.target.targetcasestudy.ui.screens.cart.domain.toCartItemEntity
import com.target.targetcasestudy.ui.screens.details.domain.DealDetails
import kotlinx.coroutines.withContext
import java.lang.NumberFormatException
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
    override suspend fun getCartCount(userId: String): Int {
        return withContext(dispatcherProvider.io) {
            dealsDao.retrieveCartList(userId.toInt()).sumOf { it.quantity }
        }
    }

    override suspend fun getCartItems(id: String): AsyncResponse<List<CartItem>> {
        return withContext(dispatcherProvider.io) {
            try {
                val cartItemEntities = dealsDao.retrieveCartList(userId = id.toInt())

                AsyncResponse.Success(
                    data = cartItemEntities.map { it.toCartItem() },
                )

            } catch (e: NumberFormatException) {
                AsyncResponse.Failed(message = e.message ?: "id failure")
            } catch (e: Exception) {
                AsyncResponse.Failed(message = e.message ?: "Couldn't access cartItems")
            }
        }
    }

    override suspend fun addDealToCart(cartItem: CartItem): AsyncResponse<String> {
        return withContext(dispatcherProvider.io) {
            try {
                val validatedCartItem = updateExistingCartItem(cartItem).toCartItemEntity()
                dealsDao.insertDealInCart(validatedCartItem)
                AsyncResponse.Success(data = "Item added to cart!")
            } catch (e: Exception) {
                AsyncResponse.Failed(message = "Item couldn't be added")
            }
        }
    }

    //Increments the quantity if the item already exist
    private suspend fun updateExistingCartItem(cartItem: CartItem): CartItem {
        val existingItems = dealsDao.retrieveCartList(cartItem.userId.toInt())
            .firstOrNull { it.dealId == cartItem.dealId.toInt() }

        return if (existingItems != null) {
            cartItem.copy(
                quantity = existingItems.quantity + 1
            )
        } else {
            cartItem
        }
    }

    override suspend fun deleteCartItem(cartItem: CartItem): AsyncResponse<String> {
        return withContext(dispatcherProvider.io) {
            try {
                dealsDao.removeFromCart(cartItem.toCartItemEntity())
                AsyncResponse.Success(data = "Deleted item !!")
            } catch (e: Exception) {
                AsyncResponse.Failed(message = e.message ?: "")
            }
        }
    }

    override suspend fun deleteUserCartItems(userId: String): AsyncResponse<String> {
        return withContext(dispatcherProvider.io) {
            try {
                dealsDao.deleteCartItemsByUserId(userId.toInt())
                AsyncResponse.Success(data = "All Items Deleted")
            } catch (e: Exception) {
                AsyncResponse.Failed(message = e.message?: "couldn't delete items")
            }
        }
    }
}