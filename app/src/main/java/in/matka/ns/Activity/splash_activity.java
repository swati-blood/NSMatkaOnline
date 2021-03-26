package in.matka.ns.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import in.matka.ns.AppController;
import in.matka.ns.Common.Common;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.R;
import in.matka.ns.Util.ConnectivityReceiver;
import in.matka.ns.Util.CustomVolleyJsonArrayRequest;
import in.matka.ns.Util.Session_management;
import in.matka.ns.networkconnectivity.NoInternetConnection;

public class splash_activity extends AppCompatActivity {

    final String TAG=splash_activity.class.getSimpleName();
    Session_management session_management;
   public static int ver_code=0;
    private AlertDialog dialog;

    public static final int MY_PERMISSIONS_REQUEST_WRITE_FIELS = 102;
   Common common;
   public static String home_text ="",dialog_image="", withdrw_text="",tagline= "" ,min_add_amount="",link = "" ,app_link="",share_link="",msg_status="",withdrw_no="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
   common=new Common(splash_activity.this);
   session_management=new Session_management(splash_activity.this);
//        Thread background = new Thread() {
//            public void run() {
//
//                try {
//                    // Thread will sleep for 5 seconds
//                    sleep(2 * 1000);
//
//                    // After 5 seconds redirect to another intent
//                    checkAppPermissions();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        // start thread
//        background.start();
//
        if (ConnectivityReceiver.isConnected()) {

            getApiData();
        }
        else
        {
            Intent intent = new Intent(this, NoInternetConnection.class);
          startActivity(intent);
        }



    }

    private void getApiData() {

        String json_tag="json_splash_request";
        HashMap<String,String> params=new HashMap<String, String>();
        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.GET, BaseUrls.URL_INDEX, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG,""+response.toString());
               try
                {

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
                        dialog_image = dataObj.getString("dialog_img");
                        ver_code=Integer.parseInt(dataObj.getString("version"));
                        msg=dataObj.getString("message");
                    checkAppPermissions();
//
//
//                    if(version_code==ver_code)
//                    {
//                        if(session_management.isLoggedIn())
//                        {
//                            Intent intent = new Intent(splash_activity.this,MainActivity.class);
//                            startActivity(intent);
//                            finish();
//
//                        }
//                        else
//                        {
//                            Intent intent = new Intent(splash_activity.this,LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//
//                        }

//                    }
//                    else
//                    {
//                        AlertDialog.Builder builder=new AlertDialog.Builder(splash_activity.this);
//                        builder.setTitle("Version Information");
//                        builder.setMessage(msg);
//                        builder.setCancelable(false);
//                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                String url = null;
//                                try {
//                                    url = app_link;
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                                Intent intent = new Intent(Intent.ACTION_VIEW);
//                                intent.setData(Uri.parse(url));
//                                startActivity(intent);
//
//                            }
//                        });
//                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                                finishAffinity();
//                            }
//                        });
//                        AlertDialog alertDialog=builder.create();
//                        alertDialog.show();
//                    }
//
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
    public void checkAppPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED  ||
                ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.INTERNET) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_NETWORK_STATE)) {
                go_next();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                android.Manifest.permission.INTERNET,
                                android.Manifest.permission.ACCESS_NETWORK_STATE
                        },
                        MY_PERMISSIONS_REQUEST_WRITE_FIELS);
            }
        } else {
            go_next();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_FIELS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                go_next();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(splash_activity.this);
                builder.setMessage("App required some permission please enable it")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openPermissionScreen();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        });
                dialog = builder.show();
            }
            return;
        }
    }

    public void go_next() {
        if(session_management.isLoggedIn())
        {
            session_management.updateDilogStatus(false);
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


    public void openPermissionScreen() {
        // startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", splash_activity.this.getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
