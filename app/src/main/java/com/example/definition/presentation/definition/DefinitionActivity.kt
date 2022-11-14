package com.example.definition.presentation.definition

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.definition.R
import com.example.definition.data.cache.WordDatabase
import com.example.definition.data.cache.WordDb
import com.example.definition.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class DefinitionActivity : AppCompatActivity() {

    private var isWordFavorite = false

    companion object {
        const val WORD_KEY = "WORD_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_definition)
        val viewModel = ViewModelProvider(this).get(DefinitionViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val wordDao = WordDatabase.getDatabase(this).wordDao()
        val toast = Toast(this).apply {
            duration = Toast.LENGTH_SHORT
        }

        Glide.with(this).asBitmap().load("https://php-noise.com/noise.php?hex=%${getRandomHexString()}")
            .into(object : SimpleTarget<Bitmap?>(500, 500) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    val drawable: Drawable = BitmapDrawable(this@DefinitionActivity.resources, resource)
                    findViewById<LinearLayout>(R.id.layout).background = drawable
                }
            })

        val favoriteImageView = findViewById<ImageView>(R.id.favoriteImageView)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val wordString = intent.extras?.getString(WORD_KEY)

        if (wordString != null) {
            viewModel.getWord(wordString,wordDao){
                if (it == null) {
                    isWordFavorite = false
                    updateFavoriteImage(favoriteImageView)
                } else {
                    isWordFavorite = true
                    updateFavoriteImage(favoriteImageView)
                }
            }


            favoriteImageView.setOnClickListener {
                if (isWordFavorite) {
                    CoroutineScope(Dispatchers.IO).launch {
                        wordDao.deleteWord(wordString)
                    }
                    toast.setText("Removed from favorite!")
                    toast.show()
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        wordDao.addWord(WordDb(Random().nextInt(), wordString))
                    }
                    toast.setText("Added to favorite!")
                    toast.show()
                }
                isWordFavorite = !isWordFavorite
                updateFavoriteImage(favoriteImageView)
            }

            val word = Utils.convertStringIntoWord(wordString)

            findViewById<TextView>(R.id.titleWordTextView).text = word.word
            findViewById<TextView>(R.id.transcriptionTextView).text = word.phonetics.last().text

            val adapter = MeaningAdapter(word.meanings)
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
            )
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateFavoriteImage(imageView: ImageView) {
        if (isWordFavorite) {
            imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun getRandomHexString(): String {
        val r = Random()
        val sb = StringBuffer()
        while (sb.length < 7) {
            sb.append(Integer.toHexString(r.nextInt()))
        }
        return sb.toString().substring(0, 7)
    }

}