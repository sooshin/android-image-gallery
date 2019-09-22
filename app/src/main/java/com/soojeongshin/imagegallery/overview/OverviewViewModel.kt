package com.soojeongshin.imagegallery.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soojeongshin.imagegallery.network.Hit
import com.soojeongshin.imagegallery.network.PixabayApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    private val _hit = MutableLiveData<Hit>()

    val hit: LiveData<Hit>
        get() = _hit

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
                if (listResult!!.isNotEmpty()) {
                    _hit.value = listResult[0]
                }
            } catch (e:Exception) {
                _status.value = "Failure: ${e.message}"
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