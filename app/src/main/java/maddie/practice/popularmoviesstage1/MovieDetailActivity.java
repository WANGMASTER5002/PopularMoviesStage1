package maddie.practice.popularmoviesstage1;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MovieDetailActivity extends ActionBarActivity {

    private final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    private Movie mMovie;

    private String url;

    final String MDB_RESULTS = "results";

    final String MDB_ID = "id";

    final String MDB_TITLE = "title";

    final String MDB_SYNOPSIS = "overview";

    final String MDB_POPULARITY = "popularity";

    final String MDB_RATING = "vote_average";

    final String MDB_RELEASE_DATE = "release_date";

    final String MDB_POSTER_PATH = "poster_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }


    public static class MovieDetailFragment extends Fragment {

        public MovieDetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);
                makeDetailsCall(movieId);
            }

            return rootView;
        }

        public void makeDetailsCall(String id) {
            

        }

    }


}