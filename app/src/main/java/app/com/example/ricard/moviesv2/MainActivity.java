package app.com.example.ricard.moviesv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    }
}