package maddie.practice.popularmoviesstage1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rfl518 on 1/14/16.
 */
public class MovieResponse {

    private String LOG_TAG = MovieResponse.class.getSimpleName();

    private List<Movie> results;

    // public constructor is necessary for collections
    public MovieResponse() {
        results = new ArrayList();
    }

    public List<Movie> getMovies() {
        return results;
    }

    public String toString() {
        String result = "";
        for (Movie movie : results) {
            result += movie.toString();
        }
        return result;
    }

    public static MovieResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();

        try
        {
            MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
            return movieResponse;

        }
        catch (IllegalStateException | JsonSyntaxException exception) {
            Log.e(MovieResponse.class.getSimpleName(), exception.getMessage());
            return new MovieResponse();
        }
    }
}
