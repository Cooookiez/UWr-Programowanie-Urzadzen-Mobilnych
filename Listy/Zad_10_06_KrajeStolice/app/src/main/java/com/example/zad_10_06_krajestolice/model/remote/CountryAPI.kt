package com.example.zad_10_06_krajestolice.model.remote

import com.example.zad_10_06_krajestolice.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountryAPI {
    @GET("rest/v2/all/")
    fun getCountries(): Single<List<Country>>
}