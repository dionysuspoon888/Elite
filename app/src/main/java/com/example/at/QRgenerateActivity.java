package com.example.at;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by D on 3/10/2018.
 */

public class QRgenerateActivity extends BaseActivity {
    de.hdodenhof.circleimageview.CircleImageView b_backQRG;
    de.hdodenhof.circleimageview.CircleImageView b_submit;

    //Animation Background
    private RelativeLayout mainLayout2;
    private AnimationDrawable animDrawable;

    EditText et_1;
    String tmp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerate);
        startFrag(R.id.qrg_container,new QRgenerateFragment(),getFragmentManager());

        //Animation Background
        mainLayout2 =  findViewById(R.id.rl_qrg);
        animDrawable = (AnimationDrawable) mainLayout2.getBackground();
        animDrawable.setEnterFadeDuration(5500);
        animDrawable.setExitFadeDuration(5500);
        animDrawable.start();

        et_1=findViewById(R.id.et_1);
        b_submit=findViewById(R.id.b_submit);


        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp=et_1.getText().toString().trim();
                GlobalConstants.inputText=tmp;
                et_1.setText("");
                startFrag(R.id.qrg_container,new QRgenerateFragment(),getFragmentManager());
                Log.i("QRG Submit","SS");

            }
        });

        b_backQRG=findViewById(R.id.b_backQRG);
        b_backQRG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBack();
                Log.i("QRG B","SS");
            }
        });






    }


}
