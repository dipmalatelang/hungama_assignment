package com.example.movie.network;

import android.os.AsyncTask;

import com.example.movie.utils.CommonHelpers;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {

    public static String response = "";

    public static class GetMovieData extends AsyncTask<String, Void, String> {

        MovieDataListener listener;

        public GetMovieData(MovieDataListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    response = CommonHelpers.readStream(urlConnection.getInputStream());
                    listener.getResult(response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    public interface MovieDataListener {
        void getResult(String jsonResponse) throws JSONException;
    }
}
