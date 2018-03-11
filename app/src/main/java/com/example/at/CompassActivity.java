package com.example.at;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


/**
 * Created by D on 3/11/2018.
 */

public class CompassActivity extends BaseActivity {
    de.hdodenhof.circleimageview.CircleImageView b_backCompass;

    //compass
    private ImageView compass;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        b_backCompass=findViewById(R.id.b_backCompass);
        b_backCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ActivityBack();
            }
        });

        compass = (ImageView) findViewById(R.id.compass);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor AccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(sensorEventListener,magneticSensor,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorEventListener,AccelerometerSensor,SensorManager.SENSOR_DELAY_GAME);

    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        float[] accelerometerValues = new float[3];
        float[] maneticValues = new float[3];
        private float lastDegree;
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                accelerometerValues = sensorEvent.values.clone();
            }else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                maneticValues = sensorEvent.values.clone();
            }
            float[] R = new float[9];
            float[] values = new float[3];
            SensorManager.getRotationMatrix(R,null,accelerometerValues,maneticValues);
            SensorManager.getOrientation(R,values);
            float rotateDegree = -(float) Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree - lastDegree) > 1){
                RotateAnimation animation = new RotateAnimation(lastDegree,rotateDegree, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setFillAfter(true);
                compass.startAnimation(animation);
                lastDegree = rotateDegree;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    @Override
    protected void onDestroy() {
        if (sensorManager != null){
            sensorManager.unregisterListener(sensorEventListener);
        }
        super.onDestroy();
    }




}
