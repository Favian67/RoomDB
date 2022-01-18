package com.example.kotlinroom_favian_14

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.withContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinroom_favian_14.room.Movie
import com.example.kotlinroom_favian_14.room.MovieDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    val db by lazy { MovieDb (this) }
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val movies = db.movieDao().getMovies()
            Log.d("MainActivity", "dbresponse : $movies ")
            withContext(Dispatchers.Main) {
                movieAdapter.setData(movies)
            }
        }
    }

    fun setupListener(){
        add_movie.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }
    private fun setupRecycleView() {
        movieAdapter = MovieAdapter(arrayListOf())
        rv_movie.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = movieAdapter
        }
    }
}