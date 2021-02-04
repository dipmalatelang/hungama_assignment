package com.example.movie.network;

import com.example.movie.model.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//@GET("/3/movie/latest?api_key=2d5d21f393818fa327e538d7d6f754cf&language=en-US")

public interface ApiInterface {
    @GET("/3/movie/{category}")
    Call<MovieResults> getMovies(
            @Path("category") String category,
            @Query("api_key") String apikey,
            @Query("language") String language,
            @Query("page") int page
    );
}
