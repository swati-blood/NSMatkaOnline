package in.games.nidhimatka.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hololo.library.otpview.OTPView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.R;
import in.games.nidhimatka.SmsReceiver;
import in.games.nidhimatka.Util.ConnectivityReceiver;
import in.games.nidhimatka.Util.CustomJsonRequest;
import in.games.nidhimatka.Util.SmsListener;
import in.games.nidhimatka.networkconnectivity.NoInternetConnection;

import static in.games.nidhimatka.Activity.splash_activity.msg_status;
import static in.games.nidhimatka.Config.BaseUrls.URL_GENERATE_OTP;
import static in.games.nidhimatka.Config.BaseUrls.URL_VERIFICATION;


public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {
ImageView iv_back;
    RelativeLayout rel_gen,rel_verify,rel_timer;
    EditText et_phone;
    Button btn_send,btn_verify,btn_resend;
    TextView tv_timer;
    String type="";
    String otp="",mobile="" ;
    Common common;
    ProgressDialog loadingBar;
    String str="";
OTPView et_otp;

    public static final String OTP_REGEX = "[0-9]{3,6}";
    CountDownTimer countDownTimer ;
    private final int REQUEST_ID_MULTIPLE_PERMISSIONS=1;
   Activity ctx=VerificationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        initViews();
    }

    private void initViews() {
        iv_back=findViewById (R.id.iv_back);

        iv_back.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });
        rel_gen=findViewById(R.id.rel_gen);
        rel_verify=findViewById(R.id.rel_verify);
        rel_timer=findViewById(R.id.rel_timer);
        et_phone=findViewById(R.id.et_phone);
        et_otp=(OTPView) findViewById(R.id.et_otp);


        if (et_phone.getText().toString().length() > 0) {
            et_phone.getText().clear();
        }

        btn_send=findViewById(R.id.btn_send);
        btn_verify=findViewById(R.id.btn_verify);
        btn_resend=findViewById(R.id.btn_resend);
        tv_timer=findViewById(R.id.tv_timer);
        common=new Common(ctx);
        loadingBar=new ProgressDialog(ctx);
        loadingBar.setMessage("Loading...");
        loadingBar.setCanceledOnTouchOutside(false);
        type=getIntent().getStringExtra("type");
        btn_send.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        btn_resend.setOnClickListener(this);
        checkAndRequestPermissions();
    }


    @Override
    public void onClick(View view) {

        if(view.getId() ==R.id.btn_send)
        {
           mobile=et_phone.getText().toString();
           otp=common.getRandomKey(6);
           if(mobile.isEmpty())
           {
               common.showToast("Enter Mobile Number");
               et_phone.requestFocus();
           }
           else if(mobile.length()!=10)
           {
               common.showToast("Invalid Mobile Number");
               et_phone.requestFocus();
           }
           else
           {
               if (ConnectivityReceiver.isConnected()) {

                   if(type.equalsIgnoreCase("f"))
                   {
                       sendOtpforPass(mobile,otp, URL_GENERATE_OTP);
                   }
                   else
                   {
                       sendOtpforPass(mobile,otp,URL_VERIFICATION);
                   }
               }
               else
               {
                   Intent intent = new Intent(VerificationActivity.this, NoInternetConnection.class);
                   startActivity(intent);
               }

           }
        }
        else if(view.getId() == R.id.btn_verify)
        {
          String stringOtp=et_otp.getOtp ().toString();
          if(stringOtp.isEmpty())
          {
              common.showToast("Enter OTP");
               et_otp.requestFocus();
          }
          else if(stringOtp.length()<4)
          {
              common.showToast(" OTP is too short");
              et_otp.requestFocus();
          }
          else
          {
              if(tv_timer.getText().toString().equalsIgnoreCase("timeout"))
              {
                  common.showToast("Timeout");
              }
              else
              {
                  if(otp.equals(stringOtp))
                  {
                       if(type.equalsIgnoreCase("f"))
                       {
                           Intent intent=new Intent(ctx,UpdatePasswordActivity.class);
                           intent.putExtra("mobile",mobile);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(intent);

                           finish();
                       }
                       else
                       {
                           Intent intent=new Intent(ctx,RegisterActivity.class);
                           intent.putExtra("mobile",mobile);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           startActivity(intent);

                           finish();
                       }
                  }
                  else
                  {
                      common.showToast("Invalid OTP");
                  }

              }

          }
        }
        else if(view.getId() == R.id.btn_resend)
        {
            mobile=et_phone.getText().toString();
            otp=common.getRandomKey(6);
            if(mobile.isEmpty())
            {
                common.showToast("Enter Mobile Number");
                et_phone.requestFocus();
            }
            else if(mobile.length()!=10)
            {
                common.showToast("Invalid Mobile Number");
                et_phone.requestFocus();
            }
            else
            {
                if(type.equalsIgnoreCase("f"))
                {
                    sendOtpforPass(mobile,otp,URL_GENERATE_OTP);
                }
                else
                {
                    sendOtpforPass(mobile,otp,URL_VERIFICATION);
                }
            }
        }
    }

    private void sendOtpforPass(final String mobile, final String otp, String url) {
        loadingBar.show();
        HashMap<String, String> params=new HashMap<>();
        params.put("mobile",mobile);
        params.put("otp",otp);
        Log.e ( "sendOtpforPass: ",params.toString () );
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("gen_ottt",""+response.toString());
                loadingBar.dismiss();
                try
                {
                    String res=response.getString("status");
                    if(res.equalsIgnoreCase("success"))
                    {
                        if(rel_verify.getVisibility() == View.GONE)
                        {
                            rel_verify.setVisibility(View.VISIBLE);
                            if(msg_status.equals("0"))
                            {
                                countDownTimer=new CountDownTimer(5000,1000) {
                                    @Override
                                    public void onTick(long l) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        et_otp.setOtp (otp);
                                    }
                                };
                                countDownTimer.start();
                            }
                            else
                            {
                                getSmsOtp();
                            }

                        }
                        rel_gen.setVisibility(View.GONE);
                        setCounterTimer(120000,tv_timer);
                        common.showToast(response.getString("message"));
                    }
                    else
                    {
                        common.showToast(response.getString("message"));
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    common.showToast("Something went wrong");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(""+msg);
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest);

    }

    public void  setCounterTimer(long diff,final TextView txt_timer)
    {
        txt_timer.setTextColor(getResources().getColor(R.color.white));

        CountDownTimer countDownTimer = new CountDownTimer(diff, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                txt_timer.setText(text);

            }

            @Override
            public void onFinish() {
                otp="";
                txt_timer.setText("Timeout");
                txt_timer.setTextColor(getResources().getColor(R.color.lowColor));
                if(btn_resend.getVisibility() == View.GONE)
                {
                    btn_resend.setVisibility(View.VISIBLE);
                }
            }
        }.start();

    }
    private boolean checkAndRequestPermissions()
    {
        int sms = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if (sms != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    public void getSmsOtp()
    {
        try
        {


            SmsReceiver.bindListener(new SmsListener() {
                @Override
                public void messageReceived(String messageText) {

                    //From the received text string you may do string operations to get the required OTP
                    //It depends on your SMS format
                    Log.e("Message",messageText);
                    // Toast.makeText(SmsVerificationActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();

                    // If your OTP is six digits number, you may use the below code

                    Pattern pattern = Pattern.compile(OTP_REGEX);
                    Matcher matcher = pattern.matcher(messageText);
                    String otp="";
                    while (matcher.find())
                    {
                        otp = matcher.group();
                    }

                    if(!(otp.isEmpty() || otp.equals("")))
                    {
                        et_otp.setOtp (otp);


                    }

                    //           Toast.makeText(SmsVerificationActivity.this,"OTP: "+ otp ,Toast.LENGTH_LONG).show();

                }
            });
        }
        catch (Exception ex)
        {
            // Toast.makeText(SmsVerificationActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
