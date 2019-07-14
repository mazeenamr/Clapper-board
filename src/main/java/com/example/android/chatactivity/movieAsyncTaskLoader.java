package com.example.android.chatactivity;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Satellite on 4/27/2018.
 */

    public class movieAsyncTaskLoader extends AsyncTaskLoader<List<movieModel>> {



        private String url;

        public movieAsyncTaskLoader(Context context, String url) {
            super(context);
            this.url = url;
        }

        @Override
        public List<movieModel> loadInBackground() {
            if (url == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.
            List<movieModel> moviemodel = movieQueryUtils.fetchmovieData(url);
            return moviemodel;
        }
        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
