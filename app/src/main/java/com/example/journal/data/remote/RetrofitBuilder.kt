package com.example.journal.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val baseUrl = "https://gossip-central-dev.herokuapp.com/api/v1/"

    private fun getInstance() : Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val gossipCentralApi: GossipCentralApi = getInstance().create(GossipCentralApi::class.java)

}