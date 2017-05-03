package com.onyecar.popularmovies;

import android.content.Context;
import android.os.Parcel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by onyekaanene on 28/04/2017.
 */

public final class MovieJsonUtil {

    public static Movie[] getMoviesStringsFromJson(Context context, String movieJsonStr)
            throws JSONException{
        /* All movies are children of the "results" object */
        final String MOVIE_RESULTS = "results";

        String POSTER_PATH = "poster_path";
        String ADULT = "adult";
        String OVERVIEW = "overview";
        String RELEASE_DATE = "release_date";
        String ID = "id";
        String ORIGINAL_TITLE = "original_title";
        String ORIGINAL_LANGUAGE = "original_language";
        String TITLE = "title";
        String BACK_DROP_PATH = "backdrop_path";
        String POPULARITY = "popularity";
        String VOTE_COUNT = "vote_count";
        String VIDEO = "video";
        String VOTE_AVERAGE = "vote_average";

        Movie[] parsedMovieData = null;


        JSONObject movieJson = new JSONObject(movieJsonStr);
        if(movieJson.has(MOVIE_RESULTS)){
            JSONArray moviesArray = movieJson.getJSONArray(MOVIE_RESULTS);
            parsedMovieData = new Movie[moviesArray.length()];
            for (int i=0; i<moviesArray.length();i++){
                JSONObject oneMovie = moviesArray.getJSONObject(i);
                Movie movie = new Movie(oneMovie.getString(POSTER_PATH),
                        oneMovie.getBoolean(ADULT), oneMovie.getString(OVERVIEW),
                        oneMovie.getString(RELEASE_DATE), oneMovie.getInt(ID),
                        oneMovie.getString(ORIGINAL_TITLE), oneMovie.getString(ORIGINAL_LANGUAGE),
                        oneMovie.getString(TITLE), oneMovie.getString(BACK_DROP_PATH),
                        oneMovie.getDouble(POPULARITY), oneMovie.getInt(VOTE_COUNT),
                        oneMovie.getBoolean(VIDEO), oneMovie.getDouble(VOTE_AVERAGE));
                movie.setPosterPath(oneMovie.getString(POSTER_PATH));
                movie.setAdult(oneMovie.getBoolean(ADULT));
                movie.setOverview(oneMovie.getString(OVERVIEW));
                movie.setReleaseDate(oneMovie.getString(RELEASE_DATE));
                movie.setId(oneMovie.getInt(ID));
                movie.setOriginalTitle(oneMovie.getString(ORIGINAL_TITLE));
                movie.setOriginalLanguage(oneMovie.getString(ORIGINAL_LANGUAGE));
                movie.setTitle(oneMovie.getString(TITLE));
                movie.setBackdropPath(oneMovie.getString(BACK_DROP_PATH));
                movie.setPopularity(oneMovie.getDouble(POPULARITY));
                movie.setVoteCount(oneMovie.getInt(VOTE_COUNT));
                movie.setVideo(oneMovie.getBoolean(VIDEO));
                movie.setVoteAverage(oneMovie.getDouble(VOTE_AVERAGE));
                parsedMovieData[i] = movie;
            }
        }
        return parsedMovieData;
    }
}
