package com.soojeongshin.imagegallery.detail


import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.soojeongshin.imagegallery.R
import com.soojeongshin.imagegallery.STORAGE_PERMISSION_CODE
import com.soojeongshin.imagegallery.databinding.FragmentDetailBinding
import com.soojeongshin.imagegallery.network.Hit

/**
 * This [Fragment] shows detailed information about a selected hit of Pixabay images.
 * It sets this information in the [DetailViewModel], which it gets as a Parcelable Hit
 * through Jetpack Navigation's SafeArgs.
 */
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var hit: Hit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        binding = FragmentDetailBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        hit = DetailFragmentArgs.fromBundle(arguments!!).selectedHit

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
                    startDownloading(context!!)
                }
            } else {
                // System OS is less than marshmallow, runtime permission is not required, perform download
                startDownloading(context!!)
            }
        }

        addChipGroup()

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
                    startDownloading(context!!)
                } else {
                    // Permission denied, show error message
                    Toast.makeText(context, "Permission denied!", Toast.LENGTH_LONG).show()
                }
             }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun addChipGroup() {
        val chipGroup = binding.tagsList
        val tagsList = hit.tags.split(getString(R.string.split_delimiter))
        for (tag in tagsList) {
            val chip = Chip(context)
            chip.text = tag
            chipGroup.addView(chip)
        }
    }

    private fun startDownloading(context: Context) {
        // Get an image url
        val webFormatUrl = hit.webFormatUrl

        // Create a DownloadManager.Request with all the details of the download and conditions
        // to start with
        val request = DownloadManager.Request(Uri.parse(webFormatUrl))
        // Allow type of networks to download files by default both are allowed
        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(context.getString(R.string.download))
        request.setDescription("Download in progress...")

        request.setNotificationVisibility(
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}.jpg")

        // Get download service, and enqueue file
        val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }
}
