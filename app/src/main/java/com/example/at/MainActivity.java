package com.example.at;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    @BindView(R.id.b_1)
    de.hdodenhof.circleimageview.CircleImageView b_1;

    @BindView(R.id.b_2)
    de.hdodenhof.circleimageview.CircleImageView b_2;

    @BindView(R.id.b_3)
    de.hdodenhof.circleimageview.CircleImageView b_3;

    @BindView(R.id.b_4)
    de.hdodenhof.circleimageview.CircleImageView b_4;




    //Animation Background
    private RelativeLayout mainLayout;
    private AnimationDrawable animDrawable;

    //Image Slider
    private CustomSwipeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GlobalConstants.viewPager=findViewById(R.id.view_pager);



        //Animation Background
        mainLayout =  findViewById(R.id.main_layout);
        animDrawable = (AnimationDrawable) mainLayout.getBackground();
        animDrawable.setEnterFadeDuration(5500);
        animDrawable.setExitFadeDuration(5500);
        animDrawable.start();



        //Ads Mob id for apps
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
//
//      //Ads Banner
//        startFrag(R.id.bar_container,new BaseFragment(),getFragmentManager());






        new Handler().postDelayed(checkJsonFinish,100);


    }

      @OnClick(R.id.b_1)
      public void setB_1(){
      startActivity(new Intent(MainActivity.this,FlashLightActivity.class));
   }

    @OnClick(R.id.b_2)
    public void setB_2(){
        startActivity(new Intent(MainActivity.this,QRscanActivity.class));
    }

    @OnClick(R.id.b_3)
    public void setB_3(){
        startActivity(new Intent(MainActivity.this,QRgenerateActivity.class));
    }

    @OnClick(R.id.b_4)
    public void setB_4(){
        startActivity(new Intent(MainActivity.this,CompassActivity.class));
    }


    public Runnable checkJsonFinish=new Runnable() {
        @Override
        public void run() {
         if(GlobalConstants.Jlist.size()==2){
             Log.i("Task JList","SS");

             GlobalConstants.isGCE=true;
             setImageSlider();

         } else{
             Log.i("Task JList","F");

             //new Handler().postDelayed(checkJsonFinish,100);
             GlobalConstants.isGCE=false;
             setImageSlider();

         }

        }
    };

    public void setImageSlider(){
        //Image Slider

        adapter = new CustomSwipeAdapter(MainActivity.this);
        GlobalConstants.viewPager.setAdapter(adapter);

    }




}

