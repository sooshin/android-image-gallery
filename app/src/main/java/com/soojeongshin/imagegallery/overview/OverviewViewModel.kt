package com.soojeongshin.imagegallery.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soojeongshin.imagegallery.network.PixabayApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getPixabayImages() on init so we can display status immediately.
     */
    init {
        getPixabayImages()
    }

    private fun getPixabayImages() {
        coroutineScope.launch {
            val getImagesSuspended  =
                PixabayApi.retrofitService.getImageResponse(
                    "10961674-bf47eb00b05f514cdd08f6e11",
                    1)
            try {
                val listResult = getImagesSuspended.hits
                _response.value = "Success: ${listResult!!.size} Images retrieved"
            } catch (e:Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}