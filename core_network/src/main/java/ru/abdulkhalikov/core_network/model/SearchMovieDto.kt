package ru.abdulkhalikov.core_network.model

import com.google.gson.annotations.SerializedName

data class SearchMovieDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val poster: PosterDto?,
    @SerializedName("rating") val rating: RatingDto?
)
