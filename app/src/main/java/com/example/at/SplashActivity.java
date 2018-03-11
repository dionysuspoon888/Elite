package com.example.at;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by D on 3/9/2018.
 */

public class SplashActivity extends AppCompatActivity {
    //DATE TRANSMITTED KEY
    public static final String EXTRA_link = "link";
    public static final String EXTRA_displayLink = "displayLink";

    private InterstitialAd mInterstitialAd;
    private Handler handler;
    private Thread thread1,thread2;

    //Volley for JSON
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.splash_container,new SplashFragment())
                .commit();

        //Volley queue
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();



        //Ads Mob id for apps
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        //Ads Page
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        handler=new Handler();


         thread1=new Thread(adsTask);
         thread2=new Thread(MainActTask);

        handler.postDelayed(thread1,2000);


         handler.postDelayed(thread2, 6000);



//        minterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                // Load the next interstitial.
//                minterstitialAd.loadAd(new AdRequest.Builder().build());
//               // startActivity(new Intent(SplashActivity.this,MainActivity.class));
//            }
//
//        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("AdsLoad","SS");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("AdsLoad","F");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("AdsLoad","Open");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("AdsLoad","Leave");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.i("AdsLoad","Close");
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                handler.removeCallbacks(thread2);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        });




    }


    public void OpenAds() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(getBaseContext(),"SS",Toast.LENGTH_SHORT).show();
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

    }

    public Runnable adsTask=new Runnable() {
        @Override
        public void run() {
            OpenAds();
            Log.i("Ads","SS");

    }
    };

    public Runnable MainActTask=new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
            Log.i("MainAct","SS");

        }
    };

    private void parseJSON() {
        //JSON Link,q for search,type=photo
        String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyBWieNyVmRNq46Kmy-1rgEU__-I-MVP6SM&cx=005254515861270210630:17risigovei&q=ads&num=2&searchType=image";
        //JSONRequest for later use
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //array that stores all the object in API Documentation
                            JSONArray jsonArray = response.getJSONArray("items");
                            Log.i("JsonArray","SS");
                            //Loop all the object of items array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Log.i("JsonItem","SS");

                                //Extrieve what we want by Keys

                                String creatorName = item.getString(EXTRA_displayLink);
                                String imageUrl = item.getString(EXTRA_link);




                                Log.i("JsonValue","Value: "+imageUrl);

                                GlobalConstants.Jlist.add(new JSONItem(imageUrl, creatorName));

                                JSONItem it= GlobalConstants.Jlist.get(i);
                                Log.i("JsonValue added?","Value: "+it.getCreator());



                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        //Start the request through Volley
        requestQueue.add(request);

    }
}
