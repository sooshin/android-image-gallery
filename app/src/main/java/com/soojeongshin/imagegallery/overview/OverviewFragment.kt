package com.soojeongshin.imagegallery.overview


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.soojeongshin.imagegallery.databinding.FragmentOverviewBinding

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel]
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Giving the binding access to the OverviewModel
        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }


}
