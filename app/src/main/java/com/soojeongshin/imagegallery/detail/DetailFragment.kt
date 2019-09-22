package com.soojeongshin.imagegallery.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.soojeongshin.imagegallery.databinding.FragmentDetailBinding

/**
 * This [Fragment] shows detailed information about a selected hit of Pixabay images.
 * It sets this information in the [DetailViewModel], which it gets as a Parcelable Hit
 * through Jetpack Navigation's SafeArgs.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        val hit = DetailFragmentArgs.fromBundle(arguments!!).selectedHit

        val viewModelFactory = DetailViewModelFactory(hit, application)

        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(DetailViewModel::class.java)

        return binding.root
    }
}
