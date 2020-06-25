package in.games.nidhimatka.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.BaseUrls;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.CustomJsonRequest;
import in.games.nidhimatka.Util.CustomVolleyJsonArrayRequest;
import in.games.nidhimatka.Util.Session_management;

public class splash_activity extends AppCompatActivity {

    Session_management session_management;
   float version_code;
   Common common;
   public static String home_text ="", withdrw_text="",tagline= "" ,min_add_amount="",link = "" ,app_link="",share_link="",msg_status="",withdrw_no="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
   common=new Common(splash_activity.this);
   session_management=new Session_management(splash_activity.this);
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
             version_code = pInfo.versionCode;
           // Toast.makeText(splash_activity.this,""+version,Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getApiData();

    }

    private void getApiData() {

        String json_tag="json_splash_request";
        HashMap<String,String> params=new HashMap<String, String>();
        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.GET, BaseUrls.URL_INDEX, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               try
                {
                    float ver_code=0;
                    String msg="";
                        JSONObject dataObj=response.getJSONObject(0);
                        tagline = dataObj.getString("tag_line");
                        withdrw_text = dataObj.getString("withdraw_text").toLowerCase();
                        withdrw_no = dataObj.getString("withdraw_no");
                        home_text = dataObj.getString("home_text").toString();
                        min_add_amount = dataObj.getString("min_amount");
                        msg_status = dataObj.getString("msg_status");
                        app_link = dataObj.getString("app_link");
                        share_link = dataObj.getString("share_link");
                        ver_code=Float.parseFloat(dataObj.getString("version"));
                        msg=dataObj.getString("message");


                    if(version_code==ver_code)
                    {
                        if(session_management.isLoggedIn())
                        {
                            Intent intent = new Intent(splash_activity.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            Intent intent = new Intent(splash_activity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }

                    }
                    else
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(splash_activity.this);
                        builder.setTitle("Version Information");
                        builder.setMessage(msg);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String url = null;
                                try {
                                    url = app_link;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finishAffinity();
                            }
                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(splash_activity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(""+msg);
                }
            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_tag);


    }

}
