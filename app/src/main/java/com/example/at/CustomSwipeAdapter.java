package com.example.at;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by D on 3/9/2018.
 */

public class CustomSwipeAdapter extends PagerAdapter {
    private int[] image_resources={R.drawable.ads1,R.drawable.ads2};
    private LayoutInflater layoutInflater;
    private Context ctx;

    public CustomSwipeAdapter(Context ctx) {

        this.ctx = ctx;

    }
    @Override
    public int getCount() {

        if(GlobalConstants.isGCE==true) {
            return GlobalConstants.Jlist.size();
        }else if(GlobalConstants.isGCE==false) {
            return image_resources.length;
        }
             return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //PageAdapter Basic Setting
        layoutInflater = layoutInflater.from(ctx);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);

        //Define the data

        ImageView imageView = item_view.findViewById(R.id.imageViewSwipe);

        Log.i("Adapter isGCE","isGCE"+GlobalConstants.isGCE);


        if(GlobalConstants.isGCE==true) {
            Picasso.with(ctx).load(GlobalConstants.Jlist.get(position).getImageUrl()).into(imageView);
        }else if(GlobalConstants.isGCE==false){
            imageView.setImageResource(image_resources[position]);
        }



        container.addView(item_view);

        return item_view;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
