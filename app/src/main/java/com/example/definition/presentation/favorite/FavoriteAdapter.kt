package com.example.definition.presentation.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.definition.R
import com.example.definition.data.cloud.model.Word
import com.example.definition.presentation.definition.DefinitionActivity
import com.example.definition.utils.Utils

class FavoriteAdapter(private val words: List<Word>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val wordTextView: TextView
        val transcriptionTextView: TextView
        val favoriteLayout: LinearLayout

        init {
            wordTextView = view.findViewById(R.id.wordTextView)
            transcriptionTextView = view.findViewById(R.id.transcriptionTextView)
            favoriteLayout = view.findViewById(R.id.favoriteLayout)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.favorite_word_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val word = words[position]

        viewHolder.wordTextView.text = word.word
        viewHolder.transcriptionTextView.text = word.phonetics.last().text
        viewHolder.favoriteLayout.setOnClickListener {
            val context = viewHolder.favoriteLayout.context
            val intent = Intent(context, DefinitionActivity::class.java)
            intent.putExtra(DefinitionActivity.WORD_KEY, Utils.convertWordIntoString(word))
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = words.size

}