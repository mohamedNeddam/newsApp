package com.example.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.db.ArticleDataBase
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    val newsRepository =  NewsRepository(ArticleDataBase(application))

    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
//    var countryCode = "us"

    init {
        getBreakingNews("ma")
    }
    private fun getBreakingNews(countryCode: String) =
        viewModelScope.launch(Dispatchers.IO) {
            breakingNews.postValue(Resource.Loading())
            val response  = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
            breakingNews.postValue(handleBreakingNewsResponse(response))
        }

    fun searchNews(searchQuery: String) =
        viewModelScope.launch(Dispatchers.IO) {
            searchNews.postValue(Resource.Loading())
            val response  = newsRepository.searchNews(searchQuery,searchNewsPage)
            searchNews.postValue(handleSearchNewsResponse(response))
    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}