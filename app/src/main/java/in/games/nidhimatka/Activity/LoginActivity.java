package in.games.nidhimatka.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.snackbar.Snackbar;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Model.UsersObjects;
import in.games.nidhimatka.NetworkStateChangeReciever;
import in.games.nidhimatka.Prevalent.Prevalent;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.CustomJsonRequest;


public class LoginActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    boolean doubleBackToExitPressedOnce=false;
   EditText etName,etPassword;
   Common common;
    Button btn_login,btn_loginWithMPin;
    ProgressDialog progressDialog;
    private Dialog dialog;
    private EditText edtEmail,edtEmailId;
    public static String mainName="";
    Activity ctx = LoginActivity.this;
     Button btnRegister,btnForgetPassword,btnForgetUserID;
    private Button btnForPassword,btnForUserID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etName=(EditText)findViewById(R.id.etUser);
        etPassword=(EditText)findViewById(R.id.etPass);
        common=new Common(LoginActivity.this);

        btnRegister=(Button)findViewById(R.id.login_register_btn);
        btnForPassword=(Button)findViewById(R.id.btnForgetPass);
        btnForUserID=(Button)findViewById(R.id.btnForgetUserId);

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

//                   etName.setText("8178624292");
//                   etPassword.setText("123456");
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
                        getUserLoginRequest(mName,mPass);
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

        final CustomJsonRequest loginRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_USER_LOGIN, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.e("login",response.toString());

                if (response.equals(null)) {
                    Toast.makeText(ctx, "User does not exist ", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject object = response;
                        String status = object.getString("status");
                        if (status.equals("success")) {
                            JSONObject jsonObject = object.getJSONObject("data");
                            UsersObjects users = new UsersObjects();
                            users.setId(jsonObject.getString("id"));
                            users.setName(jsonObject.getString("name"));
                            users.setUsername(jsonObject.getString("username"));
                            users.setMobileno(jsonObject.getString("mobileno"));
//                            users.setEmail(jsonObject.getString("email"));
                            users.setAddress(jsonObject.getString("address"));
                            users.setCity(jsonObject.getString("city"));
                            users.setPincode(jsonObject.getString("pincode"));
                            users.setPassword(jsonObject.getString("password"));
                            users.setAccountno(jsonObject.getString("accountno"));
                            users.setBank_name(jsonObject.getString("bank_name"));
                            users.setIfsc_code(jsonObject.getString("ifsc_code"));
                            users.setAccount_holder_name(jsonObject.getString("account_holder_name"));
                            users.setPaytm_no(jsonObject.getString("paytm_no"));
                            users.setTez_no(jsonObject.getString("tez_no"));
                            users.setPhonepay_no(jsonObject.getString("phonepay_no"));
                            Prevalent.currentOnlineuser = users;
//                            0String success=jsonObject.getString("success");
//                            JSONArray jsonArray=jsonObject.getJSONArray("login");
//                            if(success.equals("1"))
//                            {
                            String p = jsonObject.getString("password");
                            if (mPass.equals(p)) {
                                Intent intent = new Intent(ctx, MainActivity.class);
                                intent.putExtra("username", jsonObject.getString("username").toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                progressDialog.dismiss();
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(ctx, "Password is not correct ", Toast.LENGTH_LONG).show();
                            }
//                                for(int i=0;i<jsonArray.length();i++)
//                                {
//                                    JSONObject object=jsonArray.getJSONObject(i);
//                                    String name=object.getString("email").trim();
//
//
//                                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
//                                    intent.putExtra("username",name);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
//                                    progressDialog.dismiss();
//                                    finish();
//                                    //  loading.setVisibility(View.GONE);
//                                    //    btn_reg.setVisibility(View.VISIBLE);
//
//                                }
//


                        } else if (status.equals("unsuccessfull")) {
                            progressDialog.dismiss();
                            String message = object.getString("data");
                            Toast.makeText(ctx, "" + message, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ctx, "Something Went wrong", Toast.LENGTH_LONG).show();
                        }
                        //Toast.makeText(MainActivity.this,"User "+jsonObject.getString("name").toString(),Toast.LENGTH_LONG).show();
                        //progressDialog.dismiss();
                        //Log.d("a",response);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //   loading.setVisibility(View.GONE);
                        // btn_reg.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                      }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(""+msg);
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
