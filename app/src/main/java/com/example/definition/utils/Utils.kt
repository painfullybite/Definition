package com.example.definition.utils

import com.example.definition.data.cloud.model.Word
import com.google.gson.Gson

object Utils {

    fun convertWordIntoString(word: Word): String {
        return Gson().toJson(word)
    }

    fun convertStringIntoWord(wordString: String): Word {
        return Gson().fromJson(wordString, Word::class.java)
    }

}