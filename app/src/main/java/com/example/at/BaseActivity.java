package com.example.at;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by D on 3/10/2018.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onResume() {
        super.onResume();
        //Ads Banner
        startFrag(R.id.bar_container,new BaseFragment(),getFragmentManager());

    }

    public static void startFrag(int container, Fragment fragment, FragmentManager fm) {

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container,fragment)
                .commit();

    }


    public static void startFragBackState(int container, Fragment fragment, FragmentManager fm) {

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container,fragment)
                .addToBackStack(null)
                .commit();

    }

    public void onBackPressed()
    {
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)
        if (getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }
    }

    public void ActivityBack(){
        startActivity(new Intent(getBaseContext(),MainActivity.class));
        finish();
    }



}
