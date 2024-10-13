package com.example.finallab1.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


//4.10.2024 Mamita Gurung
@Dao

interface ParliamentMemberDao {
    @Query("SELECT * FROM parliamentmember")
    fun getAll(): Flow<List<ParliamentMember>>

    @Query("SELECT * FROM parliamentmember WHERE hetekaId = :hetekaId")
    fun getById(hetekaId: Int?): Flow<ParliamentMember>

    @Query("SELECT * FROM parliamentmember ORDER BY RANDOM() LIMIT 1")
    fun getRandom(): Flow<ParliamentMember>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(members: List<ParliamentMember>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(member: ParliamentMember)

    @Delete
    suspend fun delete(member: ParliamentMember)


}