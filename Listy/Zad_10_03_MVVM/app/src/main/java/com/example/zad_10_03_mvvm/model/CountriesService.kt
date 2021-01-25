package com.example.zad_10_03_mvvm.model

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {

    companion object {
        private val BASE_URL = "https://restcountries.eu/rest/v2/"
        private lateinit var api: CountriesAPI
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        api = retrofit.create(CountriesAPI::class.java)
    }

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }

}