package com.example.definition.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.definition.R
import com.example.definition.data.cloud.Repository
import com.example.definition.data.cloud.core.ApiResult
import com.example.definition.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        val definitionButton = rootView.findViewById<Button>(R.id.definitionButton)
        val wordEditText = rootView.findViewById<EditText>(R.id.wordEditText)
        val progressBar = rootView.findViewById<ProgressBar>(R.id.progressBar)

        val repository = Repository()

        definitionButton.setOnClickListener {
            if (isWordValid(wordEditText.text.toString())) {

                progressBar.visibility = View.VISIBLE

                CoroutineScope(Dispatchers.IO).launch {

                    val result = repository.getDefinition(wordEditText.text.toString().trim())

                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE

                        if (result is ApiResult.Success) {

                            val intent = Intent(requireContext(), DefinitionActivity::class.java)
                            intent.putExtra(DefinitionActivity.WORD_KEY, Utils.convertWordIntoString(result.word))
                            requireContext().startActivity(intent)
                        } else {
                            showToast("Unable to find definition!")
                        }
                    }
                }

            } else {
                showToast("Word is not valid!")
            }
        }

        return rootView
    }

    private fun isWordValid(word: String): Boolean {
        return word.trim().isNotEmpty()
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

}