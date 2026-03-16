package ru.abdulkhalikov.core_network.model

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("imdb") val imdb: Double?
)
