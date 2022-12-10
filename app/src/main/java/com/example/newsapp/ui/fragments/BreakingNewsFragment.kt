package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    val TAG = "BreakingNewsFragment"
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setUpRecycleView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {
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
        rvBreakingNews.adapter = newsAdapter
        rvBreakingNews.layoutManager = LinearLayoutManager(activity)


    }
}