package maddie.practice.popularmoviesstage1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    private final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }


    final String MDB_RESULTS = "results";
    final String MDB_ID = "id";
    final String MDB_TITLE = "title";
    final String MDB_SYNOPSIS = "overview";
    final String MDB_POPULARITY = "popularity";
    final String MDB_RATING = "vote_average";
    final String MDB_RELEASE_DATE = "release_date";
    final String MDB_POSTER_PATH = "poster_path";

}
