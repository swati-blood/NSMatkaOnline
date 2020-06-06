package in.games.nidhimatka.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.CustomJsonRequest;


public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rel_gen,rel_verify,rel_timer;
    EditText et_phone,et_otp;
    Button btn_send,btn_verify,btn_resend;
    TextView tv_timer;
    String type="";
    String otp="",mobile="";
    Common common;
    ProgressDialog loadingBar;
    String str="";

   Activity ctx=VerificationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initViews();
    }

    private void initViews() {
        rel_gen=findViewById(R.id.rel_gen);
        rel_verify=findViewById(R.id.rel_verify);
        rel_timer=findViewById(R.id.rel_timer);
        et_phone=findViewById(R.id.et_phone);
        et_otp=findViewById(R.id.et_otp);
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
               if(type.equalsIgnoreCase("f"))
               {
                   sendOtpforPass(mobile,otp, URLs.URL_GENERATE_OTP);
               }
               else
               {
                   sendOtpforPass(mobile,otp,URLs.URL_VERIFICATION);
               }
           }
        }
        else if(view.getId() == R.id.btn_verify)
        {
          String stringOtp=et_otp.getText().toString();
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
                    sendOtpforPass(mobile,otp,URLs.URL_GENERATE_OTP);
                }
                else
                {
                    sendOtpforPass(mobile,otp,URLs.URL_VERIFICATION);
                }
            }
        }
    }

    private void sendOtpforPass(final String mobile, String otp, String url) {
        loadingBar.show();
        HashMap<String, String> params=new HashMap<>();
        params.put("mobile",mobile);
        params.put("otp",otp);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("gen",""+response.toString());
                loadingBar.dismiss();
                try
                {
                    String res=response.getString("status");
                    if(res.equalsIgnoreCase("success"))
                    {
                        if(rel_verify.getVisibility() == View.GONE)
                        {
                            rel_verify.setVisibility(View.VISIBLE);

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

}
