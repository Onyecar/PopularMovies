package com.onyecar.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {

    TextView txtMovieTitle;
    TextView txtMovieDescription;
    TextView txtVoteAverage;
    TextView txtReleaseDate;
    ImageView imgMovieIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        txtMovieDescription = (TextView)findViewById(R.id.movie_description);
        txtMovieTitle = (TextView)findViewById(R.id.movie_title);
        txtVoteAverage = (TextView)findViewById(R.id.vote_average);
        txtReleaseDate = (TextView)findViewById(R.id.release_date);
        imgMovieIcon = (ImageView) findViewById(R.id.img_movie_icon);


        Intent callingIntent = getIntent();

        if(callingIntent!=null){
            if(callingIntent.hasExtra(MainActivity.MOVIE_DETAILS)){
                Movie movie = callingIntent.getParcelableExtra(MainActivity.MOVIE_DETAILS);
                txtMovieTitle.setText(movie.getTitle());
                txtMovieDescription.setText(movie.getOverview());
                txtVoteAverage.setText(movie.getVoteAverage().toString()+"/10.0");
                txtReleaseDate.setText(movie.getReleaseDate());
                String imageUrl = APIInfo.IMAGE_BASE_URL+APIInfo.IMAGE_SIZE+movie.getPosterPath();
                Picasso.with(this).load(imageUrl).into(imgMovieIcon);
            }
        }
    }
}
