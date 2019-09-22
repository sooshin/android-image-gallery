package com.soojeongshin.imagegallery.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soojeongshin.imagegallery.network.ImageResponse
import com.soojeongshin.imagegallery.network.PixabayApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    /**
     * Call getPixabayImages() on init so we can display status immediately.
     */
    init {
        getPixabayImages()
    }

    private fun getPixabayImages() {
        val imageResponseData = MutableLiveData<ImageResponse>();
        PixabayApi.retrofitService.getImageResponse("10961674-bf47eb00b05f514cdd08f6e11", 1)
            .enqueue(object: Callback<ImageResponse> {
                override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                    _response.value = "Success: ${response.body()?.hits?.size} Images retrieved"

                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }

            })

    }
}