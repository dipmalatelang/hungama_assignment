package com.example.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.movie.R;
import com.example.movie.databinding.ActivityMovieDetailBinding;
import com.example.movie.model.response.MovieResponse;
import com.example.movie.model.response.MovieResult;
import com.example.movie.network.HttpClient;
import com.google.gson.Gson;

import static com.example.movie.utils.Constants.API_KEY;
import static com.example.movie.utils.Constants.BASE_URL;

public class MovieDetailActivity extends AppCompatActivity implements HttpClient.MovieDataListener{
    private ActivityMovieDetailBinding binding;
    private static final String TAG = "MovieDetailActivity";

    String SIMILAR_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_detail);

        String jsonMyObject = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("movie_response");
        }
        MovieResult result = new Gson().fromJson(jsonMyObject, MovieResult.class);
        binding.setData(result);
        SIMILAR_URL = BASE_URL + "movie/"+result.getId()+"/similar?api_key=" + API_KEY;
        new HttpClient.GetMovieData(this).execute(SIMILAR_URL);
    }

    @Override
    public void getResult(String jsonResponse) {
        MovieResponse response = new Gson().fromJson(jsonResponse, MovieResponse.class);
        binding.TvSimilarMovie.setVisibility(response.getResults().size() > 0 ? View.VISIBLE : View.INVISIBLE);
        binding.setSimilarList(response.getResults());
        binding.setCallback((dataType, view, position) -> {
            MovieResult responseData = (MovieResult) dataType;
            openDetailMovieScreen(responseData);
        });
    }

    private void openDetailMovieScreen(MovieResult result){
        Intent detailScreen = new Intent(this, MovieDetailActivity.class);
        detailScreen.putExtra("movie_response", new Gson().toJson(result));
        startActivity(detailScreen);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MovieList_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}