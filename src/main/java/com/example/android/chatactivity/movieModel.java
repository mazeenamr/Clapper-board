package com.example.android.chatactivity;

/**
 * Created by Satellite on 4/27/2018.
 */

    public class movieModel {
        private String name;
        private String year;
        private String rating;
        private String genre;
        private String url;
        private String imageUrl;
        private String highQuality;
        private String LowQuality;
        private String runtime;

    public movieModel(String name, String year, String rating, String genre, String url, String imageUrl, String highQuality, String lowQuality ,String runtime) {
        this.name = name;
        this.year = year;
        this.rating = rating;
        this.genre = genre;
        this.url = url;
        this.imageUrl = imageUrl;
        this.highQuality = highQuality;
        this.LowQuality = lowQuality;
        this.runtime=runtime;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getHighQuality() {
        return highQuality;
    }

    public String getLowQuality() {
        return LowQuality;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getYear() {
            return year;
        }


        public String getRating() {
            return rating;
        }

        public String getGenre() {
            return genre;
        }

        public String getUrl() {
            return url;
        }

    }