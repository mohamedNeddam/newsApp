package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.db.ArticleDataBase
import com.example.newsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        //val repository = NewsRepository(ArticleDataBase(this))
        //val viewModelProviderFactory = NewsViewModelProviderFactory(repository)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)


        val navHostFragment= supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController= navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)
    }
}