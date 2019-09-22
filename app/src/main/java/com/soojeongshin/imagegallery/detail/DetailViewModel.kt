package com.soojeongshin.imagegallery.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.soojeongshin.imagegallery.network.Hit

/**
 * The [DetailViewModel] associated with the [DetailFragment], containing information about
 * the selected [Hit]
 */
class DetailViewModel(hit: Hit, app: Application) : AndroidViewModel(app) {

    private val _selectedHit = MutableLiveData<Hit>()

    // The external LiveData for the selectedHit
    val selectedHit: LiveData<Hit>
        get() = _selectedHit

    // Initialize the _selectedHit MutableLiveData
    init {
        _selectedHit.value = hit
    }

}