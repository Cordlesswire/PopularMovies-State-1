package com.udacity.android.tmdb.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.android.tmdb.R;
import com.udacity.android.tmdb.adapter.MovieAdapter;
import com.udacity.android.tmdb.pojo.Movie;
import com.udacity.android.tmdb.remote.ApiClient;


public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shot);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && getIntent().hasExtra(MovieAdapter.EXTRA_MOVIE)) {
            Movie movie = bundle.getParcelable(MovieAdapter.EXTRA_MOVIE);
            onBindView(movie);
            setTitleInToolbar(toolbar, movie);
            fabToShare(fab, movie);
        }

    }


    private void onBindView(Movie movie) {
        ImageView moviePosterImage = (ImageView) findViewById(R.id.moviePosterImage);
        TextView plotSynopsisText  = (TextView) findViewById(R.id.plotSynopsisText);
        TextView voteAverageText   = (TextView) findViewById(R.id.voteAverageText);
        TextView releaseDataText   = (TextView) findViewById(R.id.dataText);
        //
        Picasso.with(this)
                .load(ApiClient.BASE_IMAGE + movie.getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(android.R.drawable.stat_notify_error)
                .into(moviePosterImage);
        plotSynopsisText.setText(movie.getOverview());
        voteAverageText.setText(String.valueOf(movie.getVoteAverage())); // User rating
        releaseDataText.setText(movie.getReleaseDate());
    }


    private void fabToShare(FloatingActionButton fab, final Movie movie) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText("Assista " + movie.getOriginalTitle(), "Estou adorando!!!\n" + movie.getOverview() + ":)");
            }
        });
    }


    private void setTitleInToolbar(Toolbar toolbar, Movie movie) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(movie.getOriginalTitle());
        }
    }


    public void shareText(String title, String textToShare) {
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder.from(this)
                .setChooserTitle(title)
                .setType(mimeType)
                .setText(textToShare)
                .startChooser();
    }


}
