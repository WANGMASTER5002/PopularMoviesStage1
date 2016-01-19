package maddie.practice.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Fragment holding GridView of movies returned from The Movie Database API
 */
public class MoviesFragment extends Fragment {

    private final String LOG_TAG = MoviesFragment.class.getSimpleName();

    private MoviesAdapter mAdapter;

    private GridView mMovieGrid;

    private View mPageLoading;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        prefs.registerOnSharedPreferenceChangeListener(
            new SharedPreferences
                .OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    mAdapter.clear();
                    updateMovies();
                }
            });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        mAdapter = new MoviesAdapter(getContext());

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        mPageLoading = rootView.findViewById(R.id.page_loading);
//        mPageLoading.bringToFront();

        // Get a reference to the GridView, and attach this adapter to it.
        mMovieGrid = (GridView) rootView.findViewById(R.id.gridview_movies);
        mMovieGrid.setAdapter(mAdapter);
        mMovieGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                    .putExtra("movie Id", movie.getId());
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }


    public void updateMovies() {

        mMovieGrid.setClickable(false);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortPref = prefs.getString(getString(R.string.pref_sort_key),
            getString(R.string.pref_sort_default));

        MovieEndpointInterface endpoints = retrofit.create(MovieEndpointInterface.class);
        Call<MovieResponse> call = endpoints.getMovies(sortPref);

        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Response response) {

                if (response == null && response.isSuccess()) {
                    return;
                }

                MovieResponse movieResponse = (MovieResponse) response.body();

                Movies.clear(); // clear before populating

                if (movieResponse == null) return;

                Movies.addAll(movieResponse);

                mAdapter.clear();
                mAdapter.addAll(movieResponse);
                mAdapter.notifyDataSetChanged();
                mMovieGrid.setAdapter(mAdapter);

                mPageLoading.setVisibility(View.GONE);
                mMovieGrid.setClickable(true);

            }


            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_LONG).show();
                mPageLoading.setVisibility(View.GONE);
                Log.e(LOG_TAG, t.getMessage() + t.getCause());
            }
        });


    }


//
//        protected Movie[] sortMovies(Movie[] originalMovies, String sortOrder) {
//
//            //TODO take out hard coding
//            switch (sortOrder) {
//                case "popularity.desc":
//                    Arrays.sort(originalMovies, new Comparator<Movie>() {
//                        @Override
//                        public int compare(Movie lhs, Movie rhs) {
//                            if (lhs.getPopularity() > rhs.getPopularity()) {
//                                return -1;
//                            } else {
//                                return 1;
//                            }
//                        }
//                    });
//                    break;
//                case "vote_average.desc":
//                    Arrays.sort(originalMovies, new Comparator<Movie>() {
//                        @Override
//                        public int compare(Movie lhs, Movie rhs) {
//                            if (lhs.getVoteAverage() > rhs.getVoteAverage()) {
//                                return -1;
//                            } else {
//                                return 1;
//                            }
//                        }
//                    });
//                    break;
//                default:
//                    break;
//            }
//
//            return originalMovies;
//        }
//
//        @Override
//        protected void onPostExecute(Movie[] result) {
//            if (result != null) {
//                mAdapter.clear();
//                Log.v(LOG_TAG, result.toString());
//                for (Movie movie : result) {
//                    mAdapter.add(movie);
//                }
//                mMovieGrid.setAdapter(mAdapter);
//            }
//            mPageLoading.setVisibility(View.GONE);
//            mMovieGrid.setClickable(true);
//
//        }
//    }


    private class MoviesAdapter extends BaseAdapter {

        private Context context;

        private List<Movie> adapterMovies;

        MoviesAdapter(Context context) {
            this.context = context;
            adapterMovies = new ArrayList();
        }

        @Override
        public int getCount() {
            return adapterMovies.size();
        }

        @Override
        public Movie getItem(int position) {
            return adapterMovies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View gridItem;

            Movie tempMovie = adapterMovies.get(position);
            if (convertView == null) {
                gridItem = LayoutInflater.from(context).inflate(R.layout.grid_item_movie, parent, false);
            } else {
                gridItem = convertView;
            }
            ImageView moviePoster = (ImageView) gridItem.findViewById(R.id.grid_item_movie_poster);


            moviePoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
            moviePoster.setImageBitmap(tempMovie.getPosterBitmap());

            return gridItem;
        }

        public void clear() {
            adapterMovies.clear();
        }

        public void add(Movie movie) {
            adapterMovies.add(movie);
        }

        public void addAll(MovieResponse inMovies) {
            for (Movie movie : inMovies.getMovies()) {
                add(movie);
            }
        }

    }

    public interface MovieEndpointInterface {

        @GET(Constants.MOVIES_URL)
        Call<MovieResponse> getMovies(@Query("sort_by") String sort);
    }

}