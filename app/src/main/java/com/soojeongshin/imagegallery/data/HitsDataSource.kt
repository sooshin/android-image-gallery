package com.soojeongshin.imagegallery.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.soojeongshin.imagegallery.API_KEY
import com.soojeongshin.imagegallery.NEXT_PAGE_KEY
import com.soojeongshin.imagegallery.PAGE
import com.soojeongshin.imagegallery.PREVIOUS_PAGE_KEY
import com.soojeongshin.imagegallery.network.Hit
import com.soojeongshin.imagegallery.network.PixabayApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HitsDataSource : PageKeyedDataSource<Int, Hit>() {

    private val apiService = PixabayApi.retrofitService

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Hit>) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getImageResponse(
                    API_KEY, PAGE)
                when {
                    isActive -> {
                        val listResult = response.hits
                        callback.onResult(listResult?: listOf(), PREVIOUS_PAGE_KEY, NEXT_PAGE_KEY)
                    }
                }
            } catch (e: Exception) {
                Log.e("HitsDataSource", "Failed to load initial data")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Hit>) {
        val currentPage = params.key
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = apiService.getImageResponse(
                    API_KEY, currentPage)
                when {
                    isActive -> {
                        val nextPage = currentPage + 1
                        val listResult = response.hits
                        callback.onResult(listResult?: listOf(), nextPage)
                    }
                }
            } catch (e: Exception) {
                Log.e("HitsDataSource", "Failed to append page")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Hit>) {

    }
}