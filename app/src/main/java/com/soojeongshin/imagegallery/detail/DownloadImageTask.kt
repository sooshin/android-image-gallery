package com.soojeongshin.imagegallery.detail

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.soojeongshin.imagegallery.R
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

/**
 * Downloads an Image from a url and save it to the internal storage using a background thread.
 */
class DownloadImageTask(context: Context) : AsyncTask<String, Int, Unit>() {
    private var mContext: WeakReference<Context> = WeakReference(context)
    private var notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)
    private lateinit var notificationManager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder
    val NOTIFICATION_ID = 2

    private fun buildNotification(context: Context) {
        val progressMax = 100
        val progressCurrent = 0
        builder = NotificationCompat.Builder(context, "download_channel").apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle("Download in progress")
            priority = NotificationCompat.PRIORITY_LOW
            setProgress(progressMax, progressCurrent, false)
        }
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    /**
     * Creates a notification channel for Android O devices
     */
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("download_channel", name, importance)
            // Register the channel with the system
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
        buildNotification(mContext.get()!!)
        createNotificationChannel(mContext.get()!!)

        builder.setProgress(100, 0, false)
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    override fun doInBackground(vararg params: String?) {
        val url = params[0]
        val requestOptions = RequestOptions().override(10000)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        mContext.get()?.let {
            val bitmap = Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()

            try {
                var file = File(it.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString())
                if (!file.exists()) {
                    file.mkdir()
                }
                file = File(file, "img.jpg")
                val out = FileOutputStream(file)

                // TODO: Update progress values
                publishProgress(20)

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                Log.i("DownloadImageTask", "Image saved.")
            } catch (e: Exception) {
                Log.i("DownloadImageTask", "Failed to save image.")
            }
        }
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        builder.setProgress(100, values[0]!!, false)

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        builder.setContentTitle("Download complete")
        builder.setProgress(0, 0, false)
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
    }
}