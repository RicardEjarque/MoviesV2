package app.com.example.ricard.moviesv2;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;


/**
 * Created by Ricard on 25/04/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String LOG_TAG = posterGrid.class.getSimpleName();


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 100;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }



    //Create a new ImageView for each item referenced by the Adapter based on the input source
    public View getViewBySource(String[] sourceURL) {
        ImageView imageView;

            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        Log.e(LOG_TAG, sourceURL[1]);
        Picasso.with(mContext)
                .load(sourceURL[1])
                .into(imageView);
        //imageView.setImageResource(mThumbIds[position]);
        notifyDataSetChanged();
        return imageView;
    }



    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }


        Picasso.with(mContext)
                .load(mThumbIds[position])
                .into(imageView);
        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    String[] mThumbIds = stringArray();

    public String[] stringArray(){
     String[] returnArray = new String[100];
        Arrays.fill(returnArray, "http://i.imgur.com/DvpvklR.png");
        return returnArray;
    }

}
