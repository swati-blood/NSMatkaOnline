package in.games.rosegame.Util;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 29,November,2019
 */
public class Module {

    public static void startTimer(String time, final TextView txt_timer, final String type) {

        Date date = new Date();
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
        String cur_time = parseFormat.format(date);
        try {
            Date s_time = parseFormat.parse(cur_time.trim());
            Date e_time = parseFormat.parse(time.trim());
            long diff_e_s = e_time.getTime() - s_time.getTime();
            Log.e("diff", "" + diff_e_s);
//
            new CountDownTimer(diff_e_s, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                    if (type.equals("open")) {
                        txt_timer.setText("Bid Opens in :" + text);
                    } else if (type.equals("close")) {
                        txt_timer.setText("Bid closes in :" + text);
                    }


                }

                @Override
                public void onFinish() {


                }
            }.start();


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void startTimerCounter(CountDownTimer countDownTimer, String time, final TextView txt_timer, final String type, final String c_type) {

        Date date = new Date();
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
        String cur_time = parseFormat.format(date);
        try {
            final Date s_time = parseFormat.parse(cur_time.trim());
            Date e_time = parseFormat.parse(time.trim());
            long diff_e_s = e_time.getTime() - s_time.getTime();
            Log.e("diff", "" + diff_e_s);
            countDownTimer = new CountDownTimer(diff_e_s, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);

//                        if (type.equals("open")) {
//                            txt_timer.setText("Bid Opens in :" + text);
//                        }
//                        if (type.equals("close")) {
                    txt_timer.setText(text);
//                        }


                }

                @Override
                public void onFinish() {


                }
            }.start();


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    public String VolleyErrorMessage(VolleyError error)
    {
        String str_error ="";
        if (error instanceof TimeoutError) {
            str_error="Connection Timeout";
        } else if (error instanceof AuthFailureError) {
            str_error="Session Timeout";
            //TODO
        } else if (error instanceof ServerError) {
            str_error="Server not responding please try again later";
            //TODO
        } else if (error instanceof NetworkError) {
            str_error="Server not responding please try again later";
            //TODO
        } else if (error instanceof ParseError) {
            //TODO
            str_error="An Unknown error occur";
        }else if(error instanceof NoConnectionError){
            str_error="No Internet Connection";
        }

        return str_error;
    }
    public static String get24To12Format(String timestr)
    {
        String tm="";
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

        try {
            Date _24Hourst = _24HourSDF.parse(timestr);
            tm = _12HourSDF.format(_24Hourst);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tm;
    }
    public static long getTimeDifference(String time) {
        long diff_e_s=0;
        Date date = new Date();
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
        String cur_time = parseFormat.format(date);
        try {
            final Date s_time = parseFormat.parse(cur_time.trim());
            Date e_time = parseFormat.parse(time.trim());
            diff_e_s = e_time.getTime() - s_time.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return diff_e_s;
    }

}
