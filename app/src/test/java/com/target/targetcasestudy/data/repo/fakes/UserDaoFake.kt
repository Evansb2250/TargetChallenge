package com.target.targetcasestudy.data.repo.fakes

import com.target.targetcasestudy.data.room.dao.UserDao
import com.target.targetcasestudy.data.room.models.UserEntity

class UserDaoFake: UserDao {
    private val dealsCartItemEntity = mutableMapOf<String, UserEntity>()
    override suspend fun insertUser(user: UserEntity) {
        if(!dealsCartItemEntity.containsKey(user.userId.toString())){
            dealsCartItemEntity[user.userId.toString()] = user
        }else
            throw Exception()
    }

    override suspend fun retrieveUser(userId: Int): UserEntity? {
         return dealsCartItemEntity[userId.toString()]
    }

    override suspend fun retrieveUser(userName: String, password: String): UserEntity? {
        return dealsCartItemEntity.values.firstOrNull{it.userName.equals(userName) && it.password.equals(password)}
    }

    override suspend fun userExist(userName: String): UserEntity? {
        return dealsCartItemEntity.values.firstOrNull{it.userName.equals(userName)}
    }

    override suspend fun deleteUser(user: UserEntity) {
      dealsCartItemEntity.remove(user.userId.toString())
    }
}