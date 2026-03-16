package ru.abdulkhalikov.core_network.model

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("alternativeName") val name: String?,
    @SerializedName("genres") val genres: List<GenreDto>?,
    @SerializedName("movieLength") val length: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("rating") val rating: RatingDto?,
)