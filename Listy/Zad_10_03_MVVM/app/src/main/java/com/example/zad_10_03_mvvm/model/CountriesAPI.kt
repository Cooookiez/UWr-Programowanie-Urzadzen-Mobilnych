package com.example.zad_10_03_mvvm.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountriesAPI {

    @GET("all")
    fun getCountries(): Single<List<Country>>

}