package com.soojeongshin.imagegallery.overview


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.soojeongshin.imagegallery.databinding.FragmentOverviewBinding

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel]
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewModel
        binding.viewModel = viewModel

        // Sets the adapter of the photoStaggeredGrid RecyclerView with clickHandler lambda that tells
        // the viewModel when our hit is clicked
        binding.photosStaggeredGrid.adapter = PhotoStaggeredGridAdapter(
                PhotoStaggeredGridAdapter.OnClickListener {
            viewModel.displayHitDetails(it)
        })

        // Observe the navigateToSelectedHit LiveData and Navigate when it isn't null.
        // After navigation, call displayHitDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedHit.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigation call to prevent multiple navigation
                viewModel.displayHitDetailsComplete()
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }


}
