package com.example.zad_11_02_physicistsapp.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface PhysicistAPI {
    @GET("Cooookiez/UWr-Programowanie-Urzadzen-Mobilnych/7163fa2891d5aa3dfdd0ec9d2cb62fb1c38a946b/Listy/Zad_11_02_PhysicistsApp/physicistv7.json")
    fun getPhysicists(): Single<List<Physicist>>
}