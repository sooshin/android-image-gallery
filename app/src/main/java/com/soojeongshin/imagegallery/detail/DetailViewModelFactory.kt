package com.soojeongshin.imagegallery.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soojeongshin.imagegallery.network.Hit

/**
 * Simple ViewModel factory that provides the Hit and context to the ViewModel.
 */
class DetailViewModelFactory(
    private val hit: Hit,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(hit, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
