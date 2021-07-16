package com.example.data.local.dao

import android.content.Context
import androidx.room.Room
import com.example.data.local.MoneyDatabase

fun provideDB(context: Context): MoneyDatabase =
    Room.databaseBuilder(context, MoneyDatabase::class.java, MoneyDatabase.DB_NAME).fallbackToDestructiveMigration().build()

fun provideToDoDao(database: MoneyDatabase) = database.moneyDao()