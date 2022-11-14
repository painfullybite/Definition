package com.example.definition.presentation.definition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.definition.data.cache.WordDao
import com.example.definition.data.cache.WordDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefinitionViewModel : ViewModel() {

     fun getWord(word:String,wordDao:WordDao,onResult:(WordDb?)-> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val result = wordDao.findWord(word)
            withContext(Dispatchers.Main){
                onResult.invoke(result)
            }
        }

    }

}