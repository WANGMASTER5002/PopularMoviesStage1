package maddie.practice.popularmoviesstage1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rfl518 on 1/18/16.
 */
public class Movies {

    private static final Movies INSTANCE = new Movies();

    private static List<Movie> movies = new ArrayList();

    private Movies() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Instance of Movies already exists");
        }
    }

    public static Movies getInstance() {
        return INSTANCE;
    }

    public List<Movie> getMovies() { return movies; }

    public static void add(Movie movie) { movies.add(movie); }

    public static void clear() { movies.clear(); }

    public static int size() { return movies.size(); }

    public static Movie get(int position) { return movies.get(position); }

}
