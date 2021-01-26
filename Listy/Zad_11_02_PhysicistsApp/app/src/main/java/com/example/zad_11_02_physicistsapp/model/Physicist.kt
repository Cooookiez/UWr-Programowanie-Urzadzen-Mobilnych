package com.example.zad_11_02_physicistsapp.model

import com.google.gson.annotations.SerializedName

data class Physicist (
    @SerializedName("id")
    val physicistId: Long?,
    var uuid : Long?,
    var name : String?,

    @SerializedName("years")
    var life : String?,

    @SerializedName("country")
    var nationality : String?,
    var motivation : String?,

    @SerializedName("photo_url")
    var imageUrl : String?
        )