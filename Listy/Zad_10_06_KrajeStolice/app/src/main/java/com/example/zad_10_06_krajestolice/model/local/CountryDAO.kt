package com.example.zad_10_06_krajestolice.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.zad_10_06_krajestolice.model.Country

@Dao
interface CountryDAO {

    @Insert
    suspend fun insertAll(vararg country: Country): List<Long>

    @Query("SELECT * FROM `Countries`")
    suspend fun getAllCountries(): List<Country>

    @Query("DELETE FROM `Countries`")
    suspend fun deleteAllCountries()

}