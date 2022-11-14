package com.example.definition.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.definition.R
import com.example.definition.data.cloud.model.Word
import com.example.definition.utils.Utils
import kotlin.properties.Delegates

class FavoriteFragment : Fragment() {

    private lateinit var noWordsTextView: TextView
    private var isNoWords by Delegates.observable(true) { _, _, newValue ->
        if (newValue) {
            noWordsTextView.visibility = View.VISIBLE
        } else {
            noWordsTextView.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        noWordsTextView = rootView.findViewById(R.id.noWordTextView)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.favoriteRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )
        viewModel.observe(requireContext(), requireActivity())
        {
            val listOfWords = mutableListOf<Word>()
            it.forEach { wordDb ->
                listOfWords.add(Utils.convertStringIntoWord(wordDb.wordString))
            }
            isNoWords = listOfWords.isEmpty()
            val adapter = FavoriteAdapter(listOfWords)
            recyclerView.adapter = adapter
        }

        return rootView
    }

}