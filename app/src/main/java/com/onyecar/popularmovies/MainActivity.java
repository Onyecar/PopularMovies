package com.onyecar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private final static String TAG = MainActivity.class.getSimpleName();
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    public static final String MOVIE_NAME = "MOVIE_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String POSTER_PATH = "POSTER_PATH";
    public static final String RELEASE_DATE = "RELEASE_DATE";
    public static final String VOTE_AVERAGE = "VOTE_AVERAGE";
    public static final String MOVIE_DETAILS = "MOVIE_DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mLoadingIndicator=(ProgressBar)findViewById(R.id.pb_loading_indicator);
        loadMovieData();
    }
    private void loadMovieData() {
        showWeatherDataView();

        new fetchMovieData().execute();
    }
    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = MovieDetail.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_NAME, movie.getTitle());
        bundle.putString(DESCRIPTION, movie.getOverview());
        bundle.putString(POSTER_PATH, movie.getPosterPath());
        bundle.putString(RELEASE_DATE, movie.getReleaseDate());
        bundle.putDouble(VOTE_AVERAGE, movie.getVoteAverage());
        intentToStartDetailActivity.putExtra(MOVIE_DETAILS, bundle);
        startActivity(intentToStartDetailActivity);
    }
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
    public class fetchMovieData extends AsyncTask<String, Void, Movie[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        @Override
        protected Movie[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String sortCriteria = params[0];
            URL weatherRequestUrl = APIInfo.buildUrl(sortCriteria);
            Log.d(TAG, "url is"+ weatherRequestUrl.toString());
            try {
                String jsonMovieResponse = APIInfo.getResponseFromHttpUrl(weatherRequestUrl);
                Log.d(TAG, jsonMovieResponse);
                Movie[] simpleJsonWeatherData = MovieJsonUtil
                        .getMoviesStringsFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Movie[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showWeatherDataView();
                mMovieAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }
}
