package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entity.MoneyEntity
import com.example.data.local.dao.MoneyDao

@Database(
    entities = [MoneyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoneyDatabase : RoomDatabase() {

    companion object{
        const val DB_NAME = "MoneyDataBase.db"
    }
    abstract fun moneyDao() : MoneyDao
}