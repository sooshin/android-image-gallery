package com.soojeongshin.imagegallery.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * This data class defines a Hit which includes all information of an image.
 * The names of this data class are used by Moshi to match the names of values in JSON.
 */
@Parcelize
data class Hit(
    @Json(name = "largeImageURL") val largeImageUrl: String,
    @Json(name = "webformatHeight") val webFormatHeight: Int,
    @Json(name = "webformatWidth") val webFormatWidth: Int,
    val likes: Int,
    val imageWidth: Int,
    val id: Int,
    @Json(name = "user_id") val userId: Int,
    val views: Int,
    val comments: Int,
    @Json(name = "pageURL") val pageUrl: String,
    val imageHeight: Int,
    @Json(name = "webformatURL") val webFormatUrl: String,
    val type: String,
    val previewHeight: Int,
    val tags: String,
    val downloads: Int,
    val user: String,
    val favorites: Int,
    val imageSize: Int,
    val previewWidth: Int,
    @Json(name = "userImageURL") val userImageUrl: String,
    @Json(name = "previewURL") val previewUrl: String) : Parcelable