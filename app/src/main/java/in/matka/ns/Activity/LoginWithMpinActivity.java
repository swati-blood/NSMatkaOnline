package in.matka.ns.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.snackbar.Snackbar;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.matka.ns.Common.Common;
import in.matka.ns.Model.UsersObjects;
import in.matka.ns.NetworkStateChangeReciever;
import in.matka.ns.R;

public class LoginWithMpinActivity extends AppCompatActivity {
    Snackbar snackbar;
    View view;
    TextView txtBack;
    Common common;
    boolean isConnected;
    Button btnLogin,btn_Mpin;
    private Button dialog_btnMpin;

    EditText etMpin,dialog_etEmail;
    Dialog dialog;
    ProgressDialog progressDialog;
    static String URL_MPIN_Login = "https://malamaal.anshuwap.com/restApi/mpin/mpin_login.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_mpin);
         common=new Common(LoginWithMpinActivity.this);
        btnLogin=(Button)findViewById(R.id.login_mpilogin_btn);
        etMpin=(EditText)findViewById(R.id.etMpin);
        btn_Mpin=(Button) findViewById(R.id.btn_mpin);
        txtBack=findViewById (R.id.txt_back);
        txtBack.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                finish ();
            }
        });

        boolean sdfff=common.isConnected();
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


        progressDialog=new ProgressDialog(LoginWithMpinActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mid=etMpin.getText().toString().trim();
                if(TextUtils.isEmpty(mid))
                {
                    etMpin.setError("Enter MPIN");
                    etMpin.requestFocus();
                    return;
                }
                else
                {
                    getMPINData(mid);
                }

            }
        });

        btn_Mpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                dialog=new Dialog(LoginWithMpinActivity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.dialog_foraget_mpin_layout);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                dialog_btnMpin=(Button)dialog.findViewById(R.id.forget_mpin);
//                dialog_etEmail=(EditText)dialog.findViewById(R.id.etForget_email);
//
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
//
//
//                dialog_btnMpin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                        if(TextUtils.isEmpty(dialog_etEmail.getText().toString()))
//                        {
//                            dialog_etEmail.setError("Enter registered Email Id");
//                            dialog_etEmail.requestFocus();
//                            return;
//                        }
//                        else
//                        {
//                            String mail=dialog_etEmail.getText().toString().trim();
//                            getForgotMpin(mail);
//                        }
//
//                    }
//                });
            }
        });

    }

//    private void getForgotMpin(final String mail) {
//
//
//        progressDialog.show();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrls.Url_forgot_mpin,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try
//                        {
//                            JSONObject jsonObject=new JSONObject(response);
//                            String success=jsonObject.getString("status");
//                            if(success.equals("success"))
//                            {
//                                progressDialog.dismiss();
//                                Toast.makeText(LoginWithMpinActivity.this, "Mail sent to your email address!!!", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                            else if(success.equals("unsuccessful"))
//                            {
//                                progressDialog.dismiss();
//                                String msg=jsonObject.getString("message");
//                                Toast.makeText(LoginWithMpinActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                                return;
//                            }
//
//
//
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//                            Toast.makeText(LoginWithMpinActivity.this, "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                            //  btnReg.setVisibility(View.VISIBLE);
//
//
//                        }
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                    }
//                })
//
//        {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params=new HashMap<>();
//
//                params.put("email",mail);
//                // params.put("phonepay",phonepaynumber);
//
//
//                return params;
//            }
//
//        };
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//
//
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(LoginWithMpinActivity.this);
    }

    private void getMPINData(final String mid) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_MPIN_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals(null))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(LoginWithMpinActivity.this,"Wrong MPIN", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {

                            try
                            {
                                JSONObject jsonObject = new JSONObject(response);
                                //Toast.makeText(MainActivity.this,"User "+jsonObject.getString("name").toString(),Toast.LENGTH_LONG).show();
                                //progressDialog.dismiss();
                                //Log.d("a",response);
                                UsersObjects users = new UsersObjects();
                                users.setId(jsonObject.getString("id"));
                                users.setName(jsonObject.getString("name"));
                                users.setUsername(jsonObject.getString("username"));
                                users.setMobileno(jsonObject.getString("mobileno"));
                                users.setEmail(jsonObject.getString("email"));
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
//                                Prevalent.currentOnlineuser=users;

                                 progressDialog.dismiss();

                                Intent intent = new Intent(LoginWithMpinActivity.this,MainActivity.class);
                                intent.putExtra("username", jsonObject.getString("username").toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();

                                //Toast.makeText(LoginWithMpinActivity.this,""+users.getId(),Toast.LENGTH_LONG).show();

                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginWithMpinActivity.this,"MPIN Not exist or Already loggedin"+ex.getMessage(), Toast.LENGTH_LONG).show();
                                etMpin.setText("");
                                return;
                            }

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(LoginWithMpinActivity.this,"MPIN not exist.", Toast.LENGTH_LONG).show();
                        etMpin.setText("");
                        return;
                    }
                })
        {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params=new HashMap<>();
                params.put("mid",mid);

                return params;
            }



        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //networkStateReceiver.removeListener(MainActivity.this);
//        this.unregisterReceiver(networkStateReceiver);
    }


}
