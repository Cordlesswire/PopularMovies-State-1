package com.udacity.android.tmdb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.android.tmdb.adapter.MovieAdapter;
import com.udacity.android.tmdb.pojo.Movie;
import com.udacity.android.tmdb.remote.ApiClient;
import com.udacity.android.tmdb.remote.ApiEndpointInterface;
import com.udacity.android.tmdb.remote.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean mTwoPane;
    // UI
    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    //
    private ApiEndpointInterface mApiService;


    @SuppressWarnings("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupSnackbar(mToolbar);

        ImageView imageBackgroud = (ImageView) findViewById(R.id.image_view);
        showHeaderBackgroud("http://www.streamingobserver.com/wp-content/uploads/2016/08/2016-06-23-1466705986-1144339-netflix31200x630c.jpg", imageBackgroud);

        mRecyclerView = (RecyclerView) findViewById(R.id.item_list);
        mApiService = ApiClient.getClient().create(ApiEndpointInterface.class);
        Call<MovieResponse> call = mApiService.getPopularMovies(ApiClient.API_KEY);
        setupRecyclerView(mRecyclerView, call);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }


    private void setupSnackbar(Toolbar toolbar) {
        Snackbar snackbar = Snackbar.make(
                toolbar,
                R.string.main_snackbar_message,
                Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setAction("SEE", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.openDrawer(GravityCompat.END);
            }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }


    private void setupRecyclerView(@NonNull final RecyclerView recyclerView, Call<MovieResponse> call) {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        call.enqueue(
                new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        List<Movie> movies = response.body().getResults();
                        MovieAdapter adapter = new MovieAdapter(mRecyclerView.getContext(), movies);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                        Log.e(TAG, throwable.toString());
                    }
                }
        );
    }


    private void setupToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }


    public void showHeaderBackgroud(String urlString, ImageView imageView) {
        try {
            Picasso.with(this)
                    .load(urlString)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Call<MovieResponse> call;
        if (id == R.id.nav_popular) {
            Toast.makeText(this, R.string.most_popular, Toast.LENGTH_SHORT).show();
            call = mApiService.getPopularMovies(ApiClient.API_KEY);
            setupRecyclerView(mRecyclerView, call);
            setupToolbar(mToolbar, getString(R.string.most_popular));
        }
        else
        if (id == R.id.nav_rated) {
            Toast.makeText(this, "Top Rated", Toast.LENGTH_SHORT).show();
            call = mApiService.getTopRatedMovies(ApiClient.API_KEY);
            setupRecyclerView(mRecyclerView, call);
            setupToolbar(mToolbar, "Top Rated");
        }
        mDrawer.closeDrawer(GravityCompat.END);
        return true;
    }


}
