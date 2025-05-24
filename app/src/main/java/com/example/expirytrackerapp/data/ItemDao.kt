package com.example.expirytrackerapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Query("SELECT * FROM items ORDER BY expiryDate")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE company LIKE '%' || :query || '%'")
    fun searchItems(query: String): Flow<List<Item>>

    @Query("DELETE FROM items WHERE id = :itemId")
    suspend fun deleteItemById(itemId: Int)
} 