package com.example.zad_11_02_physicistsapp.model.remote

import com.example.zad_11_02_physicistsapp.model.Physicist
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhysicistsService {

    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(PhysicistAPI::class.java)

    fun getPhysicists(): Single<List<Physicist>> {
        return api.getPhysicists()
    }
}