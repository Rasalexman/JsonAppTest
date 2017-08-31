package mincor.com.jsonapptest.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import mincor.com.jsonapptest.R;
import mincor.com.jsonapptest.application.MainApplication;

/**
 * Created by Admin on 01.12.2016.
 */

public final class Support {

    public static void style(View view, int value) {
        view.setScaleX(value);
        view.setScaleY(value);
        view.setAlpha(value);
    }

    public Support() {
    }

    public static int dpToPx(int dp)  {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)  {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * SHOW OK MESSAGE ALERT
     * @param context - Activity.this
     * @param title - header of alert
     * @param message - message of the alert
     */
    public static final void alertDisplayerWithOK(@NonNull Context context, String title, String message, DialogInterface.OnClickListener callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.cancel());
        if(callback == null){
            builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
        }else{
           builder.setPositiveButton("OK", callback);
        }

        AlertDialog ok = builder.create();
        ok.show();
    }

    /**
     * DISABLE ALL CHILD IN VIEWGROUP
     * @param enable
     * @param vg
     */
    public static final void disableEnableControls(boolean enable, ViewGroup vg){
        View child;
        for (int i = 0; i < vg.getChildCount(); i++){
            child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            }
        }
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) MainApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return (activeNetwork != null && activeNetwork.isConnected()
                    && activeNetwork.isConnectedOrConnecting()
                    && activeNetwork.isAvailable());
        }
        return false;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static synchronized String getBetweenTime(String dateStr, String pattern){
        String beetweenStr = "";
        Date messageDate = new Date();
        SimpleDateFormat dateformater = null;
        try {
            dateformater = new SimpleDateFormat(pattern);
            messageDate = dateformater.parse(dateStr);
        } catch (ParseException e) { }

        long diff = new Date().getTime() - messageDate.getTime();
        long secBetween = (TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS));
        long minsBetween = (TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS));
        long hoursBetween = (TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));
        long daysBetween = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

        if(minsBetween < 60){
            if(minsBetween < 1){
                if(secBetween > 30) beetweenStr = String.valueOf(secBetween) + " " +MainApplication.getInstance().getString(R.string.back_less_sec);
                else  beetweenStr = MainApplication.getInstance().getString(R.string.back_now);
            }else{
                beetweenStr = String.valueOf(minsBetween) + " " +MainApplication.getInstance().getString(R.string.back_less_min);
            }
        }else if(hoursBetween >= 1 && hoursBetween < 24){
            beetweenStr = String.valueOf(hoursBetween) + " " +MainApplication.getInstance().getString(R.string.back_less_hour);
        }else if(daysBetween > 365){
            dateformater = new SimpleDateFormat("dd MMM yyyy", myDateFormatSymbols);
            beetweenStr = dateformater.format(messageDate);
        }else{
            dateformater = new SimpleDateFormat("dd MMM", myDateFormatSymbols);
            beetweenStr = dateformater.format(messageDate);
        }

        return beetweenStr;
    }

    public static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){
        @Override
        public String[] getMonths() {
            return MainApplication.getInstance().getResources().getStringArray(R.array.month_short);
        }

        @Override
        public String[] getShortMonths() {
            return MainApplication.getInstance().getResources().getStringArray(R.array.month_short);
        }
    };

    /**
     * GET LOADED IMG FROM GALLERY
     * @param uri
     * @param parent
     * @return
     * @throws IOException
     */
    public static final Bitmap getBitmapFromUri(Uri uri, Activity parent) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                parent.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * GET BYTES FROM BITMAP
     * @param bitmap
     * @return
     */
    public static final byte[] getByteArrayfromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    /**
     * CONVERT BYTES TO BITMAP
     * @param bitmap
     * @return
     */
    public static final Bitmap getBitmapfromByteArray(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap , 0, bitmap.length);
    }


    private static ProgressDialog mProgressDialog;
    /**
     * SHOW OR HIDE PROGRESS DIALOG
     * @param isShow
     * @param context
     * @param message
     */
    public static final void showHideProgressDialog(boolean isShow, Context context, String message){
        if(isShow && context != null && message != null){
            if(mProgressDialog == null){
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // устанавливаем стиль
                mProgressDialog.setMessage(message);  // задаем текст
                mProgressDialog.show();
            }
        }else if(!isShow){
            if(mProgressDialog != null){
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }
    }
}
