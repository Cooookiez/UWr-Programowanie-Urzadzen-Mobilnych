package com.example.zad_11_02_physicistsapp.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface PhysicistAPI {
    @GET("Cooookiez/UWr-Programowanie-Urzadzen-Mobilnych/master/Listy/Zad_11_02_PhysicistsApp/physicistv7.json")
    fun getPhysicists(): Single<List<Physicist>>
}