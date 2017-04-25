package app.com.example.ricard.moviesv2;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ricard on 25/04/2017.
 */

public class posterGrid extends Fragment {

    public posterGrid() {
    }

    private GridView mgridview;
    private ImageAdapter mMoviesAdapter;
    private final String LOG_TAG = posterGrid.class.getSimpleName();
    private int numMoviesdef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        mMoviesAdapter =
                new ImageAdapter(
                        getActivity());


        mgridview = (GridView) rootView.findViewById(R.id.gridview_movies);
        mgridview.setAdapter(mMoviesAdapter);

        mgridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


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
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute();
        super.onStart();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //inflater.inflate(R.menu.forecastfragment, menu);

    }

    public class FetchMoviesTask extends AsyncTask<String,Void,String[]> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
        private int movies = 100;
        public String[] moviesInformationParsed;

        @Override
        protected String[] doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            final String SCHEME = "https";
            final String AUTHORITY = "api.themoviedb.org";
            final String SMODE = "movie";
            final String TYPE = "popular";
            final String QUERY_TYPE = "query";
            final String QUERY_VALUE = "Jack+Reacher";
            final String APPID = "api_key";
            final String APPID_VALUE = "";


            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                Uri.Builder builder = new Uri.Builder();
                builder.scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath("3")
                        .appendPath(SMODE)
                        .appendPath(TYPE)
                        .appendQueryParameter(APPID,APPID_VALUE);
                        //.appendQueryParameter(QUERY_TYPE,QUERY_VALUE);

                String myUrlString = builder.build().toString();

                URL url = new URL(myUrlString);

                // Create the request to themoviedb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input into an Array
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally{
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

                Log.e(LOG_TAG, movieJsonStr);
                try {
                    moviesInformationParsed = getMovieDataFromJson(movieJsonStr, movies);

                }catch (JSONException e){
                    Log.e(LOG_TAG, "Unable to get list!!!");
                }

            }

            return moviesInformationParsed;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if(strings != null) {

                for(int i = 0; i < numMoviesdef; i++) {
                    mMoviesAdapter.mThumbIds[i] = strings[i];
                }
                mMoviesAdapter.notifyDataSetChanged();




                //mMoviesAdapter.clear();
                //mMoviesAdapter.addAll(strings);
            }

        }

        private String[] getMovieDataFromJson(String forecastJsonStr, int numMovies)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.

            final String TMDB_RESULTS = "results";
            final String TMDB_POSTER_PATH = "poster_path";
            final String OWM_ADULT = "adult";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray moviesArray = forecastJson.getJSONArray(TMDB_RESULTS);
            int realnumbermovies = moviesArray.length();


            if(realnumbermovies>=numMovies) {
                numMoviesdef = numMovies;
            } else {
                numMoviesdef = realnumbermovies;
            }
            String[] resultStrs = new String[numMoviesdef];
            for(int i = 0; i < numMoviesdef; i++) {
                // For now, using the format "Poster Image, title
                //String posterPath;
                String description;
                String highAndLow;

               // Get the JSON object representing the day
                JSONObject moviei = moviesArray.getJSONObject(i);
                String posterPath = moviei.getString(TMDB_POSTER_PATH);


               // description = weatherObject.getString(OWM_DESCRIPTION);
                String posterURL="";
                String size = "w185";
                posterURL = posterURL.concat("http://image.tmdb.org/t/p/");
                posterURL = posterURL.concat(size);
                posterURL = posterURL.concat(posterPath);
               // Log.e(LOG_TAG, posterURL);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
              //  JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
             //   double high = temperatureObject.getDouble(OWM_MAX);
             //   double low = temperatureObject.getDouble(OWM_MIN);

              //  double highlow[] = convertUnits(high,low);
             //   highAndLow = formatHighLows(highlow[0], highlow[1]);
                resultStrs[i] = posterURL;
            }


            return resultStrs;

        }
    }


    }


