package maddie.practice.popularmoviesstage1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * Created by Madeline Beyl
 * Movie class representing a movie in The Movie Database
 */
public class Movie {

    private final String LOG_TAG = Movie.class.getSimpleName();

    private long mId;
    private String mTitle;
    private String mPosterPath;
    private double mRating;
    private long mPopularity;
    private String mSynopsis;
    private Date mReleaseDate;
    private Bitmap mPosterBitmap;

    public Movie(long id, String title, String poster, double rating, long popularity, String synopsis, Date releaseDate) {
        mId = id;
        mTitle = title;
        mPosterPath = poster;
        mRating = rating;
        mPopularity = popularity;
        mSynopsis = synopsis;
        mReleaseDate = releaseDate;
        mPosterBitmap = setPosterBitmapFromString(mPosterPath);
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String poster) {
        this.mPosterPath = poster;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        this.mRating = rating;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(long popularity) {
        this.mPopularity = popularity;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
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
        return getTitle() + " " + getRating() + " " + getPopularity();
    }
}
