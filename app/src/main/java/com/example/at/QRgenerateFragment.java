package com.example.at;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.orhanobut.hawk.Hawk;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by D on 3/10/2018.
 */

public class QRgenerateFragment extends Fragment{
    @BindView(R.id.qrcode)
    ImageView qrcode;
    @BindView(R.id.barcode)
    ImageView barcode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qrgenerate,container,false);
        ButterKnife.bind(this,v);

        if(GlobalConstants.inputText==""||GlobalConstants.inputText==null) {
            //set QR code

            Bitmap bm = generateQRCode(" ");
            qrcode.setImageBitmap(bm);

            Log.i("RedmpFrag", " ");

            //set Bar cdoe

            Bitmap bm2 = getBarCode(" ");
            barcode.setImageBitmap(bm2);
        }else{
            //set QR code

            Bitmap bm = generateQRCode(GlobalConstants.inputText);
            qrcode.setImageBitmap(bm);

            Log.i("RedmpFrag", GlobalConstants.inputText);

            //set Bar cdoe

            Bitmap bm2 = getBarCode(GlobalConstants.inputText);
            barcode.setImageBitmap(bm2);

        }



        return v;
    }

    public Bitmap generateQRCode(String text) {

        Bitmap mBitmap=null;
//        LogUtil.info("generateQRCode", "generateQRCode:" + (TextUtils.isEmpty(qrMbid) || mBitmap == null || !mbid.equals(qrMbid)) + ", " + TextUtils.isEmpty(mbid)
//                + ", " + (mBitmap == null) + ", " + (!mbid.equals(qrMbid)) +", "+fullCardNo);



        QRCodeWriter writer = new QRCodeWriter();
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 0); /* default = 4 */
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, (int) convertDpToPixel(120, getActivity()), (int) convertDpToPixel(120, getActivity()), hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    mBitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return mBitmap;

//     imgQRCode.setImageBitmap(mBitmap);


//        try {
//            // generate a 150x150 QR code
//            Bitmap bm = encodeAsBitmap(barcode_content, BarcodeFormat.QR_CODE, 150, 150);
//
//            if(bm != null) {
//                image_view.setImageBitmap(bm);
//            }
//        } catch (WriterException e) { //eek }
    }


    public Bitmap getBarCode(String data) {
//        Bitmap mBitmap = null;
//        com.google.zxing.Writer c9 = new Code39Writer();

        int width;
        int height;
//        if(Utils.getScreenWidth(mContext) <= 1080){
        width = (int) convertDpToPixel(200, getActivity());
        height = (int) convertDpToPixel(40, getActivity());
        MultiFormatWriter writer = new MultiFormatWriter();
        String finalData = Uri.encode(data);

        // Use 1 as the height of the matrix as this is a 1D Barcode.
        BitMatrix bm = null;
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 0); /* default = 4 */
            bm = writer.encode(finalData, BarcodeFormat.CODE_128, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
//            LogUtil.info("getBarCode", "getBarCode:"+e.toString());
        }
        Bitmap imageBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);

        for (int i = 0; i < bm.getWidth(); i++) {
            // Paint columns of width 1
            int[] column = new int[height];
            Arrays.fill(column, bm.get(i, 0) ? Color.BLACK : Color.TRANSPARENT);
            imageBitmap.setPixels(column, 0, 1, i, 0, 1, height);
        }
        return imageBitmap;
    }



    public static float convertDpToPixel(float dp, Context context) {
        float px = dp * getDensity(context);
        return px;
    }

    public static float convertPixelToDp(float px, Context context) {
        float dp = px / getDensity(context);
        return dp;
    }

    public static float getDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

}
