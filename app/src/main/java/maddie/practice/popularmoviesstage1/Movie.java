package maddie.practice.popularmoviesstage1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Madeline Beyl
 * Movie class representing a movie in The Movie Database
 */
public class Movie {

    private final String LOG_TAG = Movie.class.getSimpleName();

    private long id;
    private String title;
    private String poster_path;
    private double vote_average;
    private double popularity;
    private String overview;
    private String release_date;
    private Bitmap mPosterBitmap;

    public Movie(long id, String title, String poster_path, double vote_average, long popularity, String overview, String release_date) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.overview = overview;
        this.release_date = release_date;
        mPosterBitmap = setPosterBitmapFromString(poster_path);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Bitmap setPosterBitmapFromString(String path){

        if(path == null) {
            return null;
        } else {
            String url = "http://image.tmdb.org/t/p/w185/" + path;
            Bitmap poster = null;

            try {
                poster = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }

            return poster;
        }
    }

    public Bitmap getPosterBitmap() {
        return mPosterBitmap;
    }

    public String toString() {
        return getTitle() + " " + getVoteAverage() + " " + getPopularity();
    }
}
