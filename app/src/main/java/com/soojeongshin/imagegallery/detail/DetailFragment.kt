package com.soojeongshin.imagegallery.detail


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.soojeongshin.imagegallery.databinding.FragmentDetailBinding

/**
 * This [Fragment] shows detailed information about a selected hit of Pixabay images.
 * It sets this information in the [DetailViewModel], which it gets as a Parcelable Hit
 * through Jetpack Navigation's SafeArgs.
 */
class DetailFragment : Fragment() {

    val STORAGE_PERMISSION_CODE: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        val hit = DetailFragmentArgs.fromBundle(arguments!!).selectedHit

        val viewModelFactory = DetailViewModelFactory(hit, application)

        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(DetailViewModel::class.java)

        binding.downloadButton.setOnClickListener {
            // Handle runtime permission for WRITE_EXTERNAL_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    // Permission denied, request it
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                } else {
                    // Permission already granted, download images
                    DownloadImageTask(requireContext()).execute(hit.webFormatUrl)
                }
            } else {
                // System OS is less than marshmallow, runtime permission is not required, perform download
                DownloadImageTask(requireContext()).execute(hit.webFormatUrl)
            }
        }
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, download images
                    val hit = DetailFragmentArgs.fromBundle(arguments!!).selectedHit
                    DownloadImageTask(requireContext()).execute(hit.webFormatUrl)
                } else {
                    // Permission denied, show error message
                    Toast.makeText(context, "Permission denied!", Toast.LENGTH_LONG).show()
                }
             }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
