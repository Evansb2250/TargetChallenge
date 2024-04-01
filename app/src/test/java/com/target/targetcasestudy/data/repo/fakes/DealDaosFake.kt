package com.target.targetcasestudy.data.repo.fakes

import com.target.targetcasestudy.data.room.dao.DealsDao
import com.target.targetcasestudy.data.room.models.CartItemEntity
import kotlinx.coroutines.flow.Flow

class DealDaosFake : DealsDao {
    private val dealsCartItemEntity = mutableMapOf<String, CartItemEntity>()
    override suspend fun insertDealInCart(cartItem: CartItemEntity) {
        dealsCartItemEntity["${cartItem.dealId}+${cartItem.userId}"] = cartItem
    }

    override suspend fun removeFromCart(cartItem: CartItemEntity) {
        dealsCartItemEntity.remove("${cartItem.dealId}+${cartItem.userId}")
    }

    override suspend fun retrieveCartList(userId: Int): List<CartItemEntity> {
        return dealsCartItemEntity.values.filter { it.userId == userId }
    }

    override suspend fun deleteCartItemsByUserId(userId: Int) {
        val updatedMap = dealsCartItemEntity.filter { it.value.userId != userId }
        dealsCartItemEntity.clear()
        dealsCartItemEntity.putAll(updatedMap)
    }
}