package com.example.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.movie.R;
import com.example.movie.databinding.ActivityMovieDetailBinding;
import com.example.movie.model.MovieResult;
import com.example.movie.network.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static com.example.movie.utils.Constants.API_KEY;
import static com.example.movie.utils.Constants.BASE_URL;

public class MovieDetailActivity extends AppCompatActivity implements HttpClient.MovieDataListener {

    private ActivityMovieDetailBinding binding;

    String SIMILAR_MOVIE_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        Intent intent = getIntent();
        MovieResult result = (MovieResult) intent.getSerializableExtra("movie_response");
        //set selected data
        binding.setData(result);

        //calling api
        SIMILAR_MOVIE_URL = BASE_URL + "movie/" + result.getId() + "/similar?api_key=" + API_KEY;
        new HttpClient.GetMovieData(this).execute(SIMILAR_MOVIE_URL);
    }

    @Override
    public void getResult(String result) throws JSONException {

        ArrayList<MovieResult> listResults = new ArrayList<>();
        JSONObject json = new JSONObject(result);
        String sArray = json.getString("results");
        JSONArray mArray = new JSONArray(sArray);
        binding.TvSimilarMovie.setVisibility(mArray.length() > 0 ? View.VISIBLE : View.INVISIBLE);
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

            //populating data object
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

        binding.setSimilarList(listResults);
        binding.setCallback((dataType, view, position) -> {
            MovieResult responseData = (MovieResult) dataType;
            //set similar movie selected data
            binding.setData(responseData);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MovieList_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}