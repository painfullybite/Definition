package com.example.definition.data.cloud

import com.example.definition.data.cloud.model.Word
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface DictionaryApi {

    @Headers("Content-Type: application/json")
    @GET("/api/v2/entries/en/{word}")
    suspend fun getDefinition(@Path("word") word: String): Response<List<Word>>

}