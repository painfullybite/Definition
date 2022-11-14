package com.example.definition.presentation.search

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definition.data.cloud.Repository
import com.example.definition.data.cloud.core.ApiResult
import com.example.definition.data.cloud.model.Word
import com.example.definition.presentation.definition.DefinitionActivity
import com.example.definition.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel: ViewModel() {
    private val repository = Repository()
    fun getDefinition (word: String,onResult: (ApiResult) -> Unit ) {
        viewModelScope.launch (Dispatchers.IO){

            val result = repository.getDefinition(word)
            withContext(Dispatchers.Main){
                onResult.invoke(result)
            }
        }
    }
}