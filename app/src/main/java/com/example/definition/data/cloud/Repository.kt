package com.example.definition.data.cloud

import com.example.definition.data.cloud.core.ApiResult

class Repository {

    suspend fun getDefinition(word: String) : ApiResult {
        val retrofit = RetrofitInstance.retrofit
        val result = retrofit.getDefinition(word)

        return if (result.isSuccessful) {
            val resultWord = result.body()
            if (resultWord != null) {
                ApiResult.Success(resultWord.first())
            } else {
                ApiResult.Error
            }
        } else {
            ApiResult.Error
        }
    }

}