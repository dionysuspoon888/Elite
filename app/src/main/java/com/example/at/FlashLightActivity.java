package com.example.at;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.noob.lumberjack.LogLevel;
import com.noob.noobcameraflash.managers.NoobCameraManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by D on 3/10/2018.
 */

public class FlashLightActivity extends BaseActivity {
    @BindView(R.id.b_back)
    de.hdodenhof.circleimageview.CircleImageView b_back;


    de.hdodenhof.circleimageview.CircleImageView b_flashOn;

    boolean setflash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
        ButterKnife.bind(this);


        try {
            NoobCameraManager.getInstance().init(this, LogLevel.Verbose);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        b_flashOn=findViewById(R.id.b_flashOn);
        b_flashOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!setflash) {
                    b_flashOn.setImageResource(R.drawable.b_flashoff);
                    setflash=true;
                    try {
                        NoobCameraManager.getInstance().turnOnFlash();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }else{
                    b_flashOn.setImageResource(R.drawable.b_flashon);
                    setflash=false;
                    try {
                        NoobCameraManager.getInstance().turnOffFlash();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        setflash=false;
        try {
            NoobCameraManager.getInstance().turnOffFlash();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.b_back)
    public void setB_back() {
        ActivityBack();
    }

    //Protect(Save) variables after orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        //Save variables into key (key,values)
        outState.putBoolean("setflash",setflash);
    }

    //Restore the vairables after orientation
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        setflash = savedInstanceState.getBoolean("setflash");
        if(!setflash) {
            b_flashOn.setImageResource(R.drawable.b_flashon);
        }else {
            b_flashOn.setImageResource(R.drawable.b_flashoff);
        }

    }

}
