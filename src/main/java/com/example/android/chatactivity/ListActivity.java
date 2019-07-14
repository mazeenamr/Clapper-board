package com.example.android.chatactivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satellite on 4/27/2018.
 */


    public class ListActivity extends AppCompatActivity
            implements LoaderManager.LoaderCallbacks<List<movieModel>>{

        /**
         * Constant value for the earthquake loader ID. We can choose any integer.
         * This really only comes into play if you're using multiple loaders.
         */
        private static final int MOVIES_LOADER_ID = 1;

        private movieAdapter mAdapter;

        private static final String URL = "https://yts.am/api/v2/list_movies.json?sort_by=year&genre=";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Find a reference to the {@link ListView} in the layout
            ListView movielist = (ListView) findViewById(R.id.list);
            TextView textview=(TextView)findViewById(R.id.movie_name);
            mAdapter = new movieAdapter(this, new ArrayList<movieModel>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            movielist.setAdapter(mAdapter);

            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {
                // Get a reference to the LoaderManager, in order to interact with loaders.
                LoaderManager loaderManager = getLoaderManager();

                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(MOVIES_LOADER_ID, null, this);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<movieModel>> loader) {
            // Loader reset, so we can clear out our existing data.
            mAdapter.clear();
        }
        @Override
        public Loader<List<movieModel>> onCreateLoader(int id, Bundle args) {
            Intent i =getIntent();
            String genre = i.getStringExtra("genre");
            String rate=i.getStringExtra("rate");
            //create new loader and pass to it the context
            return new movieAsyncTaskLoader(ListActivity.this,URL+genre+"&minimum_rating="+rate);

        }

        @Override
        public void onLoadFinished(Loader<List<movieModel>> loader, List<movieModel> moviemodel) {
            // If there is a valid {@link country}, then add it to the adapter's
            // data set. This will trigger the country to update.
            if (moviemodel != null  && !moviemodel.isEmpty()) {
                mAdapter.addAll(moviemodel);

            }
        }

    }
