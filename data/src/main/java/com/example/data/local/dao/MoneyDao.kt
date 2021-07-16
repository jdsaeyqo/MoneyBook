package com.example.data.local.dao


import androidx.room.*
import com.example.data.entity.MoneyEntity

@Dao
interface MoneyDao {

    @Query("SELECT * from MoneyEntity")
    suspend fun getAll(): List<MoneyEntity>

    @Query("SELECT * from MoneyEntity where id=:id")
    suspend fun getById(id:Long): MoneyEntity?

    @Insert
    suspend fun insert(moneyEntity: MoneyEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moneyEntityList: List<MoneyEntity>)

    @Update
    suspend fun update(moneyEntity: MoneyEntity)

    @Query("DELETE FROM MoneyEntity WHERE id=:id")
    suspend fun delete(id:Long)

    @Query("DELETE FROM MoneyEntity")
    suspend fun deleteAll()

}