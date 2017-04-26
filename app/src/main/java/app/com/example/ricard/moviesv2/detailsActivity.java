package app.com.example.ricard.moviesv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ricard on 26/04/2017.
 */

public class detailsActivity extends AppCompatActivity

    {

    private final String LOG_TAG = detailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerDetails, new detailsView())
                    .commit();
        }

    }
}
