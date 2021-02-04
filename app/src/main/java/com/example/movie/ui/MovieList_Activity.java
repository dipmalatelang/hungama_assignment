package com.example.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;

import com.example.movie.R;

import com.example.movie.databinding.ActivityMovieListBinding;
import com.example.movie.model.response.MovieResponse;
import com.example.movie.model.response.MovieResult;
import com.example.movie.network.HttpClient;
import com.example.movie.ui.callbacks.BaseInterface;
import com.google.gson.Gson;

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
    public void getResult(String jsonResponse) {
        MovieResponse response = new Gson().fromJson(jsonResponse, MovieResponse.class);
        list.addAll(response.getResults());
        binding.setData(response.getResults());
        binding.setCallback((dataType, view, position) -> {
            MovieResult responseData = (MovieResult) dataType;
            Log.i(TAG, "getResult: "+responseData.getTitle());
            openDetailMovieScreen(responseData);
        });
    }

    private void openDetailMovieScreen(MovieResult result){
        Intent detailScreen = new Intent(this, MovieDetailActivity.class);
        detailScreen.putExtra("movie_response", new Gson().toJson(result));
        startActivity(detailScreen);
    }

    void filter(String text, ArrayList<MovieResult> tempList){
        List<MovieResult> temp = new ArrayList();
        for(MovieResult d: tempList){
            if(d.getTitle().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        binding.setData(temp);
    }
}

