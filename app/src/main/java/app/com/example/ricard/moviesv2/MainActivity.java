package app.com.example.ricard.moviesv2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Ricard on 25/04/2017.
 */

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new posterGrid())
                    .commit();
        }

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, true);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = sharedPref.getString("Sort_by","1");
        Log.e(LOG_TAG, sortOrder);
    }
}