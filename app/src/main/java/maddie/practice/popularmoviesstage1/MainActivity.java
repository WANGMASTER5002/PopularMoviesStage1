package maddie.practice.popularmoviesstage1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment, new MoviesFragment())
//                .commit();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
//        String sortKey = getString(R.string.pref_sort_key);
//
//        switch (id) {
//            case R.id.action_sort_popularity:
//                prefs.edit().putString(sortKey, getString(R.string.pref_sort_popularity));
//            case R.id.action_sort_rating:
//                prefs.edit().putString(sortKey, getString(R.string.pref_sort_rating));
//            default:
//                prefs.edit().putString(sortKey, getString(R.string.pref_sort_default));
//        }
        return super.onOptionsItemSelected(item);
    }

}
