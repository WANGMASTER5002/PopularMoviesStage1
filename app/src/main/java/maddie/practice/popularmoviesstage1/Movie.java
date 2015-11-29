package maddie.practice.popularmoviesstage1;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by rfl518 on 11/28/15.
 */
public class Movie {

    private long mId;
    private String mTitle;
    private String mPosterPath;
    private double mRating;
    private double mPopularity;
    private String mSynopsis;
    private Date mReleaseDate;
    private Bitmap mPosterBitmap;

    public Movie(long id, String title, String poster, double rating, double popularity, String synopsis, Date releaseDate) {
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

    public String getPoster() {
        return mPosterPath;
    }

    public void setPoster(String poster) {
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

    public void setPopularity(double popularity) {
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
           // Bitmap poster = new Bitmap();
        }
        return null;
    }
}
