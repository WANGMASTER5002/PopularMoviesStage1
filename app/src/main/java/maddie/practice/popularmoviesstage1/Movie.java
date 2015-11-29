package maddie.practice.popularmoviesstage1;

import android.graphics.Bitmap;
import java.util.Date;

/**
 * Created by rfl518 on 11/28/15.
 */
public class Movie {

    private String mTitle;
    private Bitmap mPoster;
    private float mRating;
    private String mSynopsis;
    private Date mReleaseDate;

    public Movie(String title, Bitmap poster, float rating, String synopsis, Date releaseDate) {
        mTitle = title;
        mPoster = poster;
        mRating = rating;
        mSynopsis = synopsis;
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Bitmap getPoster() {
        return mPoster;
    }

    public void setPoster(Bitmap poster) {
        this.mPoster = poster;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        this.mRating = rating;
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

}
