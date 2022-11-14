package com.example.definition.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.definition.R
import com.example.definition.data.cloud.core.ApiResult
import com.example.definition.presentation.definition.DefinitionActivity
import com.example.definition.utils.Utils

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        val definitionButton = rootView.findViewById<Button>(R.id.definitionButton)
        val wordEditText = rootView.findViewById<EditText>(R.id.wordEditText)
        val progressBar = rootView.findViewById<ProgressBar>(R.id.progressBar)



        definitionButton.setOnClickListener {
            if (isWordValid(wordEditText.text.toString())) {

                progressBar.visibility = View.VISIBLE
                viewModel.getDefinition(wordEditText.text.toString().trim()){ result ->
                    progressBar.visibility = View.GONE

                    if (result is ApiResult.Success) {

                        val intent = Intent(requireContext(), DefinitionActivity::class.java)
                        intent.putExtra(DefinitionActivity.WORD_KEY, Utils.convertWordIntoString(result.word))
                        requireContext().startActivity(intent)
                    } else {
                        showToast("Unable to find definition!")
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