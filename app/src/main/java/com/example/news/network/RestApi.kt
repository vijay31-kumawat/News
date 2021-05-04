package com.example.news.network

import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RestApi {

    @GET("api/v2/articles")
    fun getArticlesList(): Observable<JsonElement>

    @GET("api/v2/articles")
    fun getArticlesDetail(@Query("id") id: String?): Observable<JsonElement>

}