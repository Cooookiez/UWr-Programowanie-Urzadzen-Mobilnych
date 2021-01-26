package com.example.zad_10_06_krajestolice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey(autoGenerate = true)
    val uuid: Long?,
    val name: String?,
    val capital: String?,
    @SerializedName("flag")
    val flag_url: String?,
)