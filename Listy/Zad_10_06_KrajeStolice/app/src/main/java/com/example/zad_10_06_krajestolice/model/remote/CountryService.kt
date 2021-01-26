package com.example.zad_10_06_krajestolice.model.remote

import com.example.zad_10_06_krajestolice.model.Country
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryService {
    private val BASE_URL = "https://restcountries.eu/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(CountryAPI::class.java)

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }
}