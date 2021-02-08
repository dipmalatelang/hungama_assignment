package com.example.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;

import com.example.movie.R;

import com.example.movie.databinding.ActivityMovieListBinding;

import com.example.movie.model.MovieResult;
import com.example.movie.network.HttpClient;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.movie.utils.Constants.API_KEY;
import static com.example.movie.utils.Constants.BASE_URL;

public class MovieList_Activity extends AppCompatActivity implements HttpClient.MovieDataListener {

    private ActivityMovieListBinding binding;

    private static final String TAG = "MovieList_Activity";
    ArrayList<MovieResult> list = new ArrayList<>();

    String CALL_URL = BASE_URL + "movie/popular?api_key=" + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);

        iniView();
        new HttpClient.GetMovieData(this).execute(CALL_URL);
    }

    private void iniView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query, list);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText, list);
                return false;
            }
        });
    }

    @Override
    public void getResult(String result) throws JSONException {

        ArrayList<MovieResult> listResults = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(result);
        String arrayResult = jsonObject.getString("results");
        JSONArray mArray = new JSONArray(arrayResult);

        for (int i = 0; i < mArray.length(); i++) {
            JSONObject key = mArray.getJSONObject(i);
            Boolean adult = (boolean) key.get("adult");
            String backdropPath = (String) key.get("backdrop_path");
            long id = (int) key.get("id");
            String originalLanguage = (String) key.get("original_language");
            String originalTitle = (String) key.get("original_title");
            String overview = (String) key.get("overview");
            double popularity = (double) key.get("popularity");
            String posterPath = (String) key.get("poster_path");
            String releaseDate = (String) key.get("release_date");
            String title = (String) key.get("title");
            Boolean video = (boolean) key.get("video");
            String voteAverage = (key.get("vote_average")).toString();
            long voteCount = Long.valueOf((Integer) key.get("vote_count"));

            MovieResult movieData = new MovieResult(
                    adult,
                    backdropPath,
                    null,
                    id,
                    originalLanguage,
                    originalTitle,
                    overview,
                    popularity,
                    posterPath,
                    releaseDate,
                    title,
                    video,
                    voteAverage,
                    voteCount
            );
            listResults.add(movieData);
        }

        list.addAll(listResults);
        binding.setData(listResults);
        binding.setCallback((dataType, view, position) -> {
            MovieResult responseData = (MovieResult) dataType;
            Log.i(TAG, "getResult: " + responseData.getTitle());
            openDetailMovieScreen(responseData);
        });
    }

    private void openDetailMovieScreen(MovieResult result) {
        Intent detailScreen = new Intent(this, MovieDetailActivity.class);
        detailScreen.putExtra("movie_response", result);
        startActivity(detailScreen);
    }

    void filter(String text, ArrayList<MovieResult> listData) {
        List<MovieResult> dataList = new ArrayList();
        for (MovieResult d : listData) {
            if (d.getTitle().toLowerCase().contains(text.toLowerCase())) {
                dataList.add(d);
            }
        }
        binding.setData(dataList);
    }
}

