package com.example.zad_11_02_physicistsapp.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.zad_11_02_physicistsapp.model.Physicist

@Dao
interface PhysicistDAO {

    @Insert
    suspend fun insertAll(vararg physicist: Physicist): List<Long>

    @Query("SELECT * FROM Physicists")
    suspend fun getAllPhysicists()

    @Query("SELECT * FROM physicists WHERE `uuid` = :physicistId")
    suspend fun getPhysicist(physicistId: Long): Physicist?

    @Query("DELETE FROM Physicists")
    suspend fun deleteAllPhysicists()

}