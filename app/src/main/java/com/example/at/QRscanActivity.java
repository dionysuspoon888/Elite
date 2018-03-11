package com.example.at;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * Created by D on 3/10/2018.
 */

public class QRscanActivity extends BaseActivity {
    de.hdodenhof.circleimageview.CircleImageView b_backQr;

    //QR
    private Activity qrscanactivity;
    private TextView scan_content;
    private TextView scan_format;

    private String scanContent,scanFormat;
    private ImageView scan_btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qrscan);

        b_backQr=findViewById(R.id.b_backQr);
        b_backQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityBack();
                Log.i("QR B","SS");
            }
        });


        init_view();


        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator scanIntegrator = new IntentIntegrator(qrscanactivity);
                scanIntegrator.setScanningRectangle(900,900);

                scanIntegrator.initiateScan();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult!=null){
             scanContent=scanningResult.getContents();
            scanFormat=scanningResult.getFormatName();
            messageBox();

        }else{
            Toast.makeText(getApplicationContext(),"nothing",Toast.LENGTH_SHORT).show();
        }
    }
    private void init_view(){

        this.qrscanactivity=this;
        this.scan_btn = (ImageView) findViewById(R.id.scan_btn);
    }

    public void messageBox(){



        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Do you open the link?");
        dlgAlert.setMessage(scanContent);

        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startURL();

            }
        });
        dlgAlert.setNegativeButton("No",null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }


    public void startURL(){
        Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(scanContent));
        startActivity(i);

    }

}

