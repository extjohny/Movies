package ru.abdulkhalikov.core_network.model

import com.google.gson.annotations.SerializedName

data class SearchMoviesDto(
    @SerializedName("docs") val movies: SearchMovieDto
)
