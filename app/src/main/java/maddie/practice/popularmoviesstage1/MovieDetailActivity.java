package maddie.practice.popularmoviesstage1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Madeline Beyl
 * MovieDetailActivity shows the user the poster, rating, release date, and synopsis
 * Inspired by code from Sunshine project
 */
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.movies_fragment_container, new MovieDetailFragment())
                .commit();
        }
    }

    public static class MovieDetailFragment extends Fragment {

        private final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

        private Movie mMovie;
        private View rootView;
        private ImageView moviePoster;
        private TextView movieRating;
        private TextView movieReleaseDate;
        private TextView movieSynopsis;

        public MovieDetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

            Intent intent = getActivity().getIntent();
            long defaultId = 0;
            if (intent != null && intent.hasExtra("movie Id")) {
                Long movieId = intent.getLongExtra("movie Id", defaultId);
                makeDetailsCall(movieId);
            }

            return rootView;
        }

        public void setUpDetailsUI() {

            getActivity().setTitle(mMovie.getTitle() + " " + getString(R.string.details));

            movieSynopsis = (TextView) rootView.findViewById(R.id.movie_details_synopsis_textview);
            movieSynopsis.setText(mMovie.getSynopsis());

            moviePoster = (ImageView) rootView.findViewById(R.id.movie_details_poster_imageview);
            moviePoster.setImageBitmap(mMovie.getPosterBitmap());


            movieRating = (TextView) rootView.findViewById(R.id.movie_details_vote_average_textview);
            movieRating.setText(Double.toString(mMovie.getRating()));

            movieReleaseDate = (TextView) rootView.findViewById(R.id.movie_details_release_date_textview);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
            movieReleaseDate.setText(dateFormat.format(mMovie.getReleaseDate()).toString());

            movieSynopsis = (TextView) rootView.findViewById(R.id.movie_details_synopsis_textview);
            movieSynopsis.setText(mMovie.getSynopsis());
        }

        public void makeDetailsCall(Long id) {
            FetchMovieDetailTask movieDetailTask = new FetchMovieDetailTask();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                movieDetailTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
            } else {
                movieDetailTask.execute(id);
            }
        }


        public class FetchMovieDetailTask extends AsyncTask<Long, Void, Movie> {

            private final String LOG_TAG = FetchMovieDetailTask.class.getSimpleName();

            /**
             * Take the String representing the complete movie info in JSON Format and pull out the data we need to construct the Strings
             * needed for the UI.
             */
            private Movie getMovieDetailsFromJson(String movieJsonStr)
                throws JSONException {

                // These are the names of the JSON objects that need to be extracted.
                final String MDB_ID = getString(R.string.id_json);
                final String MDB_POPULARITY = getString(R.string.popularity_json);
                final String MDB_TITLE = getString(R.string.title_json);
                final String MDB_SYNOPSIS = getString(R.string.synopsis_json);
                final String MDB_RATING = getString(R.string.rating_json);
                final String MDB_RELEASE_DATE = getString(R.string.release_date_json);
                final String MDB_POSTER_PATH = getString(R.string.poster_path_json);

                JSONObject moviesJson = new JSONObject(movieJsonStr);

                long id = moviesJson.getLong(MDB_ID);
                String title = moviesJson.getString(MDB_TITLE);
                String synopsis = moviesJson.getString(MDB_SYNOPSIS);
                String posterPath = moviesJson.getString(MDB_POSTER_PATH);
                Date releaseDate = getDateFromJson(moviesJson.getString(MDB_RELEASE_DATE));
                Long popularity = moviesJson.getLong(MDB_POPULARITY);
                Double rating = moviesJson.getDouble(MDB_RATING);

                Movie currentMovie = new Movie(id, title, posterPath, rating, popularity, synopsis, releaseDate);

                return currentMovie;
            }

            protected Date getDateFromJson(String json) {
                if (json == null) {
                    return null;
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = dateFormat.parse(json);
                        return date;
                    } catch (Exception e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }
                }
                return null;
            }

            @Override
            protected Movie doInBackground(Long... params) {

                Long id;
                if(params != null) {
                    id = params[0];
                } else {
                    id = null;
                }

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                // Will contain the raw JSON response as a string.
                String movieJsonStr = null;

                try {
                    String urlBase = "http://api.themoviedb.org/3/movie/";
                    final String API_KEY_PARAM = "api_key";

                    Uri builtUri = Uri.parse(urlBase + id).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, BuildConfig.MY_MOVIE_DB_API_KEY)
                        .build();
                    URL url = new URL(builtUri.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    movieJsonStr = buffer.toString();
                    Log.v(LOG_TAG, movieJsonStr);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error ", e);
                    // If the code didn't successfully get the movie data, there's no point in attempting
                    // to parse it.
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e(LOG_TAG, "Error closing stream", e);
                        }
                    }
                }

                try {
                    return getMovieDetailsFromJson(movieJsonStr);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }

                // This will only happen if there was an error getting or parsing the forecast.
                return null;
            }

            @Override
            protected void onPostExecute(Movie result) {
                if (result != null) {
                    Log.v(LOG_TAG, result.toString());
                    mMovie = result;
                    setUpDetailsUI();
                }
            }
        }

    }

}