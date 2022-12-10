package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    val TAG = "SearchNewsFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setUpRecycleView()

        var job: Job? = null
        etSearch.addTextChangedListener {   editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
                response ->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    response.message?.let{
                        Log.e(TAG, "an error $it")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }




    private fun setUpRecycleView(){
        newsAdapter = NewsAdapter()
        rvSearchNews.adapter = newsAdapter
        rvSearchNews.layoutManager = LinearLayoutManager(activity)


    }
}