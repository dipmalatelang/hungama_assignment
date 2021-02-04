package com.example.movie.ui;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movie.model.ResourceModel;
import com.example.movie.model.response.MovieResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

import static com.example.movie.utils.Constants.API_KEY;
import static com.example.movie.utils.Constants.BASE_URL;

public class MovieListViewModel extends AndroidViewModel {

    private final MovieListLiveData movieListLiveData;
    private static final String TAG = "MovieListViewModel";
    public String jsonResponse;
    public MutableLiveData<Boolean> check = new MutableLiveData<>();

    public MovieListViewModel(Application application) {
        super(application);

        movieListLiveData = new MovieListLiveData(application);
    }

//    public LiveData<ResourceModel<List<MovieResponse>>> getMovieData() {
//        if (movieListLiveData.getValue() == null) {
//            movieListLiveData.load();
//        } else {
//            if (!movieListLiveData.getValue().status.equals(ResourceModel.Status.LOADING)) {
//                movieListLiveData.load();
//            }
//        }
//        return movieListLiveData;
//    }

    public class MovieListLiveData extends LiveData<ResourceModel<List<MovieResponse>>> {
        private final Context context;

        public MovieListLiveData(Context context) {
            this.context = context;
        }

        @Override
        protected void onActive() {

        }

        @Override
        protected void onInactive() {

        }

//        public void load() {
//            setValue(ResourceModel.loading(null));
//
//            String CALL_URL = BASE_URL + "movie/popular?api_key=" + API_KEY;
//
////            new GetMovieData().execute(CALL_URL);
//
//            List<MovieResponse> list = new ArrayList<>();
//            list.add(new Gson().fromJson(jsonResponse, MovieResponse.class));
//            setValue(ResourceModel.success(list));
//        }
    }

//

    public void getData(String s){
        try {
            HttpURLConnection httpURLConnection;

            URL url = new URL(s);

            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == 200) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    jsonResponse = stringBuilder.toString();
                    check.setValue(true);
                }
            } else {
                check.setValue(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public class GetMovieData extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                HttpURLConnection httpURLConnection;
//
//                URL url = new URL(strings[0]);
//
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                httpURLConnection.setConnectTimeout(10000);
//                httpURLConnection.setReadTimeout(10000);
//                httpURLConnection.connect();
//
//                int responseCode = httpURLConnection.getResponseCode();
//
//                if (responseCode == 200) {
//                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
//                        StringBuilder stringBuilder = new StringBuilder();
//                        String line;
//                        while ((line = bufferedReader.readLine()) != null) {
//                            stringBuilder.append(line);
//                        }
//                        jsonResponse = stringBuilder.toString();
//                    }
//                    return jsonResponse;
//                } else {
//                    return "";
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.getMessage();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Log.d(TAG, "onPostExecute: " + s);
//        }
//    }
}