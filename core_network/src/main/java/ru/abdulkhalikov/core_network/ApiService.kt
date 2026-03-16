package ru.abdulkhalikov.core_network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.abdulkhalikov.core_network.model.MovieDto
import ru.abdulkhalikov.core_network.model.MoviesDto
import ru.abdulkhalikov.core_network.model.SearchMoviesDto

interface ApiService {

    @GET("/v1.4/movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): MovieDto

    @GET("/v1.4/movie")
    suspend fun getMovies(): MoviesDto

    @GET("/v1.4/movie/search?")
    suspend fun search(
        @Query("query") query: String
    ): SearchMoviesDto
}