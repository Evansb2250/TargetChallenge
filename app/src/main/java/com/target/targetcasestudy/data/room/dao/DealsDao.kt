package com.target.targetcasestudy.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.target.targetcasestudy.data.room.models.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDealInCart(cartItem: CartItemEntity)

    @Delete
    suspend  fun removeFromCart(cartItem: CartItemEntity)

    @Query("SELECT * FROM CartItemEntity WHERE userId=:userId")
    fun retrieveCartItems(userId: Int): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM CartItemEntity WHERE userId=:userId")
    suspend fun retrieveCartList(userId: Int): List<CartItemEntity>

    @Query("DELETE FROM CartItemEntity WHERE userId=:userId")
    suspend fun deleteCartItemsByUserId(userId: Int)
}