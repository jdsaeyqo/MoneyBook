package com.example.domain.repository

import com.example.domain.model.Money

interface MoneyRepository {

    suspend fun getMoneyList(): List<Money>

    suspend fun getMoneyItem(id: Long): Money?

    suspend fun insertMoneyItem(money: Money): Long

    suspend fun insertMoneyList(moneyList: List<Money>)

    suspend fun updateMoneyItem(money: Money)

    suspend fun deleteMoneyItem(id: Long)

    suspend fun deleteAll()

}