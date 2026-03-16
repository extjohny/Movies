package ru.abdulkhalikov.core_network.model

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("url") val url: String?,
    @SerializedName("previewUrl") val preview: String?
)
