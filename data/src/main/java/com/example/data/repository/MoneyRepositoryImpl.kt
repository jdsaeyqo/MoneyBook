package com.example.data.repository

import com.example.data.local.dao.MoneyDao
import com.example.data.mapper.MoneyMapper
import com.example.domain.model.Money
import com.example.domain.repository.MoneyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MoneyRepositoryImpl(
    private val moneyDao : MoneyDao,
    private val ioDispatcher: CoroutineDispatcher
) : MoneyRepository {
    override suspend fun getMoneyList(): List<Money> = withContext(ioDispatcher) {
       moneyDao.getAll().map{
           MoneyMapper.mapperToMoney(it)
       }
    }

    override suspend fun getMoneyItem(id: Long): Money?= withContext(ioDispatcher) {
        moneyDao.getById(id)?.let { MoneyMapper.mapperToMoney(it) }
    }

    override suspend fun insertMoneyItem(money: Money): Long = withContext(ioDispatcher) {
        moneyDao.insert(MoneyMapper.mapperToMoneyEntity(money))
    }

    override suspend fun insertMoneyList(moneyList: List<Money>) = withContext(ioDispatcher) {
        moneyDao.insert(moneyList.map { MoneyMapper.mapperToMoneyEntity(it)})
    }

    override suspend fun updateMoneyItem(money: Money) = withContext(ioDispatcher) {
        moneyDao.update(MoneyMapper.mapperToMoneyEntity(money))
    }

    override suspend fun deleteMoneyItem(id: Long) = withContext(ioDispatcher) {
        moneyDao.delete(id)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        moneyDao.deleteAll()
    }
}