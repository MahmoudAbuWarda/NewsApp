package com.example.newsapp.newsapi

import retrofit2.Call
import retrofit2.http.*

interface NewsApiInterface {

    @GET("61272b27f11f78361421790261272ab26efd821b3c6d0422.xml")
    public fun getPalestineRss(): Call<RSS>
    @GET("world.xml")
    public fun getWorldRss(): Call<RSS>
    @GET("islam.xml")
    public fun getIslamicRss(): Call<RSS>
    @GET("entertainment.xml")
    public fun getEntertainmentRss(): Call<RSS>


}