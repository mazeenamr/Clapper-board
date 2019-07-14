package com.example.android.chatactivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.loopj.android.image.SmartImageView;
import java.util.List;

/**
 * Created by Satellite on 4/27/2018.
 */


    public class movieAdapter extends ArrayAdapter<movieModel> {

    public movieAdapter(@NonNull Context context, List<movieModel> moviemodel) {
            super(context,0, moviemodel);

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.movie_list_item, parent, false);
            }
            final movieModel currentmovie= getItem(position);

            TextView movieName =  listItemView.findViewById(R.id.movie_name);
            movieName.setText(currentmovie.getName());

            TextView language =  listItemView.findViewById(R.id.genre);
            language.setText(currentmovie.getGenre());

            TextView year=  listItemView.findViewById(R.id.year);
            year.setText("Year: "+currentmovie.getYear());

            TextView rating =  listItemView.findViewById(R.id.rating);
            rating.setText("Rating: "+currentmovie.getRating());

            SmartImageView profileImage = listItemView.findViewById(R.id.profileImage);
            profileImage.setImageUrl(currentmovie.getImageUrl());

            TextView runtime =listItemView.findViewById(R.id.runtime);
            runtime.setText("Minutes: "+currentmovie.getRuntime());
            Button high = listItemView.findViewById(R.id.high_quality);
            high.setOnClickListener(new View.OnClickListener(){
                @Override
                //On click function
                public void onClick(View view) {
                    // Find the current earthquake that was clicked on
//                    movieModel currentMovie= .getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri movieUrl = Uri.parse(currentmovie.getHighQuality());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, movieUrl);

                    // Send the intent to launch a new activity
                    getContext().startActivity(websiteIntent);
                }
            });

            Button low = listItemView.findViewById(R.id.low_quality);
            low.setOnClickListener(new View.OnClickListener(){
                @Override
                //On click function
                public void onClick(View view) {
                    // Find the current earthquake that was clicked on
//                    movieModel currentMovie= .getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri movieUrl = Uri.parse(currentmovie.getLowQuality());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, movieUrl);

                    // Send the intent to launch a new activity
                    getContext().startActivity(websiteIntent);
                }
            });
            TextView nameView=listItemView.findViewById(R.id.movie_name);
            nameView.setOnClickListener(new View.OnClickListener(){
                @Override
                //On click function
                public void onClick(View view) {
                    Uri movieUrl = Uri.parse(currentmovie.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, movieUrl);

                    // Send the intent to launch a new activity
                    getContext().startActivity(websiteIntent);
                }
            });
            SmartImageView imageView=listItemView.findViewById(R.id.profileImage);
            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                //On click function
                public void onClick(View view) {
                    Uri movieUrl = Uri.parse(currentmovie.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, movieUrl);

                    // Send the intent to launch a new activity
                    getContext().startActivity(websiteIntent);
                }
            });
            return listItemView;

        }

    }
