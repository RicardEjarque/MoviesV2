package app.com.example.ricard.moviesv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by Ricard on 26/04/2017.
 */


    public class detailsView extends Fragment  {


        public detailsView(){

        }

        private final String LOG_TAG = detailsView.class.getSimpleName();
        private String MoviePosterStr;
        private String movieTitleStr;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_details, container, false);

            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                MoviePosterStr = intent.getStringExtra(Intent.EXTRA_TEXT);

                Picasso.with(getActivity())
                        .load(MoviePosterStr)
                        .into((ImageView) rootView.findViewById(R.id.imageViewPoster));

            }

            if (intent != null && intent.hasExtra("MovieTitle")) {
                ((TextView) rootView.findViewById(R.id.movie_title)).setText(intent.getStringExtra("MovieTitle"));
            }
            if (intent != null && intent.hasExtra("MovieRating")) {
                ((TextView) rootView.findViewById(R.id.movie_rating)).setText(intent.getStringExtra("MovieRating"));
            }
            if (intent != null && intent.hasExtra("MovieReleaseDate")) {
                ((TextView) rootView.findViewById(R.id.release_date)).setText(intent.getStringExtra("MovieReleaseDate"));
            }
            if (intent != null && intent.hasExtra("MovieSynopsis")) {
                ((TextView) rootView.findViewById(R.id.synopsis)).setText(intent.getStringExtra("MovieSynopsis"));
            }


            return rootView;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Add this line in order for this fragment to handle menu events.
            setHasOptionsMenu(true);

        }

        @Override
        public void onStart(){

            super.onStart();
        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.main, menu);

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            switch(id) {
                case R.id.action_settings:
                    Intent settingsViewAct = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(settingsViewAct);
                    break;
            }

            return super.onOptionsItemSelected(item);
        }

    }


