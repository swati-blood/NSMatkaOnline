package in.matka.ns.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.matka.ns.AppController;
import in.matka.ns.Common.Common;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.NetworkStateChangeReciever;
import in.matka.ns.R;
import in.matka.ns.Util.ConnectivityReceiver;
import in.matka.ns.Util.CustomJsonRequest;
import in.matka.ns.Util.Session_management;
import in.matka.ns.Util.ToastMsg;
import in.matka.ns.networkconnectivity.NoInternetConnection;


public class LoginActivity extends AppCompatActivity {

    private static String TAG = LoginActivity.class.getSimpleName();
    boolean doubleBackToExitPressedOnce=false;
    Session_management session_management;
    EditText etName,etPassword;
    Common common;
    Button btn_login,btn_loginWithMPin;
    ProgressDialog progressDialog;
    private Dialog dialog;
    private EditText edtEmail,edtEmailId;
    public static String mainName="";
    ToastMsg toastMsg;
    Activity ctx = LoginActivity.this;
     Button btnRegister,btnForgetPassword,btnForgetUserID;
    private Button btnForUserID;
    TextView btnForPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etName=(EditText)findViewById(R.id.etUser);
        etPassword=(EditText)findViewById(R.id.etPass);
        common=new Common(LoginActivity.this);
toastMsg= new ToastMsg(LoginActivity.this);
        btnRegister=(Button)findViewById(R.id.login_register_btn);
        btnForPassword=(TextView)findViewById(R.id.btnForgetPass);
        btnForUserID=(Button)findViewById(R.id.btnForgetUserId);
           session_management=new Session_management(LoginActivity.this);
        boolean sdfff=isConnected(LoginActivity.this);
        if(sdfff==true)
        {
           // Snackbar.make(findViewById(R.id.main_layout),"Network Status: ",Snackbar.LENGTH_INDEFINITE).show();
        }
        else
        {
            Snackbar.make(findViewById(R.id.main_layout),"No Internet Connection",Snackbar.LENGTH_INDEFINITE).show();
        }

       // final String[] net = new String[1];
        IntentFilter intentFilter=new IntentFilter(NetworkStateChangeReciever.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean isNetworkAvailable=intent.getBooleanExtra(NetworkStateChangeReciever.IS_NETWORK_AVAILABLE,false);
                String netwotkStatus=isNetworkAvailable ? "connected" : "disconnected";

                if(netwotkStatus.equals("disconnected"))
                {
                    Snackbar.make(findViewById(R.id.main_layout),"No Internet Connection",Snackbar.LENGTH_INDEFINITE).show();
                }
                else
                {
                    Snackbar.make(findViewById(R.id.main_layout),"Connected",Snackbar.LENGTH_LONG).show();
                }
         //       net[0] =netwotkStatus;


              //  Toast.makeText(MainActivity.this,""+netwotkStatus,Toast.LENGTH_LONG).show();

            }
        },intentFilter);



       // Snackbar.make(findViewById(R.id.main_layout),"Network Status :"+sd,Snackbar.LENGTH_LONG).show();



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,VerificationActivity.class);
                intent.putExtra("type","r");
                startActivity(intent);

            }
        });


        progressDialog=new ProgressDialog(LoginActivity.this);
//        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        btn_login=(Button) findViewById(R.id.login_login_btn);
        btn_loginWithMPin=(Button)findViewById(R.id.login_mpilogin_btn);

        btn_loginWithMPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent=new Intent(LoginActivity.this,LoginWithMpinActivity.class);
                startActivity(intent);


            }
        });

        btnForPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //                dialog=new Dialog(MainActivity.this);
        //                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //                dialog.setContentView(R.layout.dialog_forget_pass_layout);
        //                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //                btnForgetPassword=(Button)dialog.findViewById(R.id.forget_password);
        //                edtEmail=(EditText)dialog.findViewById(R.id.etForget_email);
        //
        //                dialog.setCanceledOnTouchOutside(false);
        //                dialog.show();
        //
        //
        //                btnForgetPassword.setOnClickListener(new View.OnClickListener() {
        //                    @Override
        //                    public void onClick(View v) {
        //
        //
        //                        if(TextUtils.isEmpty(edtEmail.getText().toString()))
        //                        {
        //                            edtEmail.setError("Enter registered Email Id");
        //                            edtEmail.requestFocus();
        //                            return;
        //                        }
        //                        else
        //                        {
        //                            String mail=edtEmail.getText().toString().trim();
        //                            getPassword(mail);
        //                        }
        //
        //                    }
        //                });
        //
                Intent intent = new Intent(LoginActivity.this,VerificationActivity.class);
                intent.putExtra("type","f");
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String mName=etName.getText().toString().trim();
                    String mPass=etPassword.getText().toString().trim();
//                    String mName="anasmansoori734@gmail.com";
//
//                    String mPass="Anas@123";

                    if(TextUtils.isEmpty(mName))
                    {
                        etName.setError("Enter Mobile No");
                        etName.requestFocus();
                    }
                    else if(TextUtils.isEmpty(mPass))
                    {

                        etPassword.setError("Enter password");
                        etPassword.requestFocus();

                    }
                    else
                    {
                        mainName=mName;
//                        Login(mName,mPass);
                        if (ConnectivityReceiver.isConnected()) {

                            getUserLoginRequest(mName,mPass);
                        }
                        else
                        {
                            Intent intent = new Intent(LoginActivity.this, NoInternetConnection.class);
                            startActivity(intent);
                        }


                    }

//                    if(!mName.isEmpty()||!mPass.isEmpty())
//                    {
//
//                        mainName=mName;
//                        Login(mName,mPass);
//                    }
//                    else
//                    {
//                        etName.setError("Please enter user name/email");
//                        etPassword.setError("Please enter Password");
//                        return;
//                    }
                }



//                Intent intent=new Intent(MainActivity.this,GameActivity.class);
//                startActivity(intent);

        });
    }

    private void getUserLoginRequest(final String mName, final String mPass) {
        progressDialog.show();
        final String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobileno",mName);
        params.put("password",mPass);

        final CustomJsonRequest loginRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_LOGIN, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.e("login",response.toString());

                    try {
                        boolean resp=response.getBoolean("responce");
                        if (resp) {
                            JSONObject jsonObject = response.getJSONObject("data");
                            String id=common.checkNull(jsonObject.getString("id").toString());
                            String name=common.checkNull(jsonObject.getString("name").toString());
                            String username=common.checkNull(jsonObject.getString("username").toString());
                            String mobile=common.checkNull(jsonObject.getString("mobileno").toString());
                            String email=common.checkNull(jsonObject.getString("email").toString());
                            String address=common.checkNull(jsonObject.getString("address").toString());
                            String city=common.checkNull(jsonObject.getString("city").toString());
                            String pincode=common.checkNull(jsonObject.getString("pincode").toString());
                            String accno=common.checkNull(jsonObject.getString("accountno").toString());
                            String bank=common.checkNull(jsonObject.getString("bank_name").toString());
                            String ifsc=common.checkNull(jsonObject.getString("ifsc_code").toString());
                            String holder=common.checkNull(jsonObject.getString("account_holder_name").toString());
                            String paytm=common.checkNull(jsonObject.getString("paytm_no").toString());
                            String tez=common.checkNull(jsonObject.getString("tez_no").toString());
                            String phonepay=common.checkNull(jsonObject.getString("phonepay_no").toString());
                            String wallet=common.checkNull(jsonObject.getString("wallet").toString());
                            String dob=common.checkNull(jsonObject.getString("dob").toString());
                           String gender=common.checkNull(jsonObject.getString("gender").toString());
                            String p = jsonObject.getString("password");
                            if (mPass.equals(p)) {
                                session_management.createLoginSession(id,name,username,mobile,email,address
                                ,city,pincode,accno,bank,ifsc,holder,paytm,tez,phonepay,dob,wallet,gender);
                                Intent intent = new Intent(ctx, MainActivity.class);
                                intent.putExtra("username", jsonObject.getString("username").toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
//                                Toast.makeText(ctx, "Password is not correct ", Toast.LENGTH_LONG).show();
                            toastMsg.toastIconError("Password is not correct ");
                            }

                        }
                         else {
//                            Toast.makeText(ctx, ""+response.getString("error").toString(), Toast.LENGTH_LONG).show();
                          toastMsg.toastIconError(response.getString("error"));
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        progressDialog.dismiss();
                      }

                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
//                    common.showToast(""+msg);
                    toastMsg.toastIconError(""+msg);
                }

            }
        });
        AppController.getInstance().addToRequestQueue(loginRequest,tag_json_obj);


    }


    public boolean isConnected(Context context)
    {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting())
        {
            android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobileNet=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobileNet != null && mobileNet.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
            {
                return true;
            }
            else {
                return false;
            }

        }
        else
        {
            return false;
        }
    }

    public AlertDialog.Builder builDialog(Context c)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press OK to exit.. ");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        return builder;
    }


    @Override
    public void onBackPressed() {

        if(doubleBackToExitPressedOnce)
        {
            this.finishAffinity();
            super.onBackPressed();

            return;
        }
        this.doubleBackToExitPressedOnce=true;
        Toast.makeText(ctx,"Press again for exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        },2000);


    }






}
