package com.soojeongshin.imagegallery.network

import com.squareup.moshi.Json

data class ImageResponse(
    @Json(name = "totalHits") val totalHits: Int,
    @Json(name = "hits") val hits: List<Hit>?,
    @Json(name= "total") val total: Int = 0
)



