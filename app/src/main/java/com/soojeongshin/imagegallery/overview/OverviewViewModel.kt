package com.soojeongshin.imagegallery.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.soojeongshin.imagegallery.INITIAL_LOAD_SIZE_HINT
import com.soojeongshin.imagegallery.PAGE_SIZE
import com.soojeongshin.imagegallery.data.HitsDataSource
import com.soojeongshin.imagegallery.network.Hit

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    // Internally, we use a MutableLiveData to handle navigation to the selected hit
    private val _navigateToSelectedHit = MutableLiveData<Hit>()

    // The external immutable LiveData for the navigation hit
    val navigateToSelectedHit: LiveData<Hit>
        get() =_navigateToSelectedHit

    //  The PagedList of Hit which loads images in chunks from a DataSource
    var pagedHits : LiveData<PagedList<Hit>>

    /**
     * Initializes the pagedList of Hit on init.
     */
    init {
        // Configure how a PagedList loads contents from the DataSource
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE_HINT)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        pagedHits= initializedPagedListBuilder(config).build()
    }

    fun getHits(): LiveData<PagedList<Hit>> = pagedHits

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Hit> {
        val dataSourceFactory = object: DataSource.Factory<Int, Hit>() {
            override fun create(): DataSource<Int, Hit> {
                return HitsDataSource()
            }
        }
        return LivePagedListBuilder<Int, Hit>(dataSourceFactory, config)
    }

    /**
     * When the hit is clicked, set the [_navigateToSelectedHit] [MutableLiveData]
     * @param hit The [Hit] that was clicked on
     */
    fun displayHitDetails(hit: Hit) {
        _navigateToSelectedHit.value = hit
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedHit is set to null
     */
    fun displayHitDetailsComplete() {
        _navigateToSelectedHit.value = null
    }
}