package ru.abdulkhalikov.core_network.model

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("docs") val movies: List<MovieDto>,
    @SerializedName("next") val next: String,
    @SerializedName("prev") val prev: String,
    @SerializedName("hasNext") val hasNext: Boolean,
    @SerializedName("hasPrev") val hasPrev: Boolean
)
