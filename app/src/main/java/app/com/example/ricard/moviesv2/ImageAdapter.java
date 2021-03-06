package app.com.example.ricard.moviesv2;

import android.content.Context;
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
    // references to our images
    String[] mThumbIds = stringArray();


    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 100;
    }

    public Object getItem(int position) {
        return 0;
    }

    public long getItemId(int position) {
        return 0;
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

     public String[] stringArray(){
     String[] returnArray = new String[100];
        Arrays.fill(returnArray, "http://i.imgur.com/DvpvklR.png");
        return returnArray;
    }

}
