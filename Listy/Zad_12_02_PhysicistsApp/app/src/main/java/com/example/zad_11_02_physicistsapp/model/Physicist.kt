package com.example.zad_11_02_physicistsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Physicists")
data class Physicist (
    @ColumnInfo(name = "physicists_id")
    @SerializedName("id")
    val physicistId: Long?,

    @PrimaryKey(autoGenerate = true)
    var uuid : Long?,
    var name : String?,

    @SerializedName("years")
    var life : String?,

    @SerializedName("country")
    var nationality : String?,
    var motivation : String?,

    @ColumnInfo(name = "image_url")
    @SerializedName("photo_url")
    var imageUrl : String?
        )