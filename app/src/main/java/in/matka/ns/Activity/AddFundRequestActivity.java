    package in.matka.ns.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import in.matka.ns.AppController;
import in.matka.ns.Common.Common;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.Intefaces.GetAppSettingData;
import in.matka.ns.Model.AppSettingModel;
import in.matka.ns.R;
import in.matka.ns.Util.ConnectivityReceiver;
import in.matka.ns.Util.CustomJsonRequest;
import in.matka.ns.Util.CustomVolleyJsonArrayRequest;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.Session_management;
import in.matka.ns.networkconnectivity.NoInternetConnection;

import static in.matka.ns.Config.BaseUrls.URL_GET_GATEWAY;
import static in.matka.ns.Config.Constants.KEY_EMAIL;
import static in.matka.ns.Config.Constants.KEY_MOBILE;
import static in.matka.ns.Config.Constants.KEY_NAME;

public class AddFundRequestActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {
    private final String TAG= AddFundRequestActivity.class.getSimpleName();
   TextView txtBack;

    Common common;
    EditText etPoints;
    Activity ctx=AddFundRequestActivity.this;
    String themeColor,desc,imageUrl,requestStatus,gatewayStatus,pnts;
    LoadingBar progressDialog;
    public TextView bt_back,txtMatka;
    Button btnRequest;
    private TextView txtWallet_amount;
    int min_amount ;
    Session_management session_management;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fund_request);


        initViews();
        Checkout.preload(getApplicationContext());
        common.appSettingData(new GetAppSettingData() {
            @Override
            public void getAppSettingData(AppSettingModel model) {
                min_amount=Integer.parseInt(model.getMin_amount().toString());
//               Log.e(TAG, "getAppSettingData: "+model.getMin_amount().toString());
            }
        });
        getGatewaySetting();

    }

    void initViews()
    {
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Points");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        session_management=new Session_management(ctx);
        txtMatka=(TextView)findViewById(R.id.board);
        etPoints=(EditText)findViewById(R.id.etRequstPoints);
        btnRequest=(Button)findViewById(R.id.add_Request);
        progressDialog=new LoadingBar(ctx);
        bt_back=(TextView)findViewById(R.id.txtBack);


        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        common=new Common(ctx);

        btnRequest.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_Request)
        {


            if(TextUtils.isEmpty(etPoints.getText().toString()))
            {
                etPoints.setError("Enter Some Points");
                return;
            }
            else
            {  int points=Integer.parseInt(etPoints.getText().toString().trim());
                if(points<min_amount)
                {
                    common.errorMessageDialog("Minimum Range for point is "+ min_amount);

                }
                else
                {
                    String user_id= common.getUserId();
                    pnts=String.valueOf(points);
                    if (ConnectivityReceiver.isConnected()) {
                        if(gatewayStatus.equals("0")){
                            saveInfoIntoDatabase(user_id, pnts, requestStatus, "Add","");
                        }else if(gatewayStatus.equals("1")){
                            startPayment(points);
                        }

//                        saveInfoIntoDatabase(user_id, p, st, "Add");
                    } else
                    {
                        Intent intent = new Intent(ctx, NoInternetConnection.class);
                        startActivity(intent);
                    }
                }
            }
        }
    }
    private void saveInfoIntoDatabase(final String user_id, final String points, final String st,String type,String trans_id) {
        progressDialog.show();
        HashMap<String,String> params=new HashMap<>();
        params.put("user_id",user_id);
        params.put("points",points);
        params.put("request_status",st);
        params.put("type",type);
        params.put("trans_id",trans_id);
        params.put("w_type","");

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REQUEST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("add_fund",response.toString());
                progressDialog.dismiss();
                try {
                    boolean resp=response.getBoolean("responce");
                    if(resp)
                    {
                        common.showToast(""+response.getString("message"));
                       finish();
                    }
                    else
                    {
                        common.showToast(""+response.getString("error"));
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                common.showVolleyError(error);

            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest);
    }

    public void startPayment(int amt) {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_live_s7AZi7HtHIO5Ff");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.icon);

        /**
         * Reference to current activity
         */
        Activity activity=AddFundRequestActivity.this;
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            options.put("name", session_management.getUserDetails().get(KEY_NAME));
            options.put("description", desc);
            options.put("image", imageUrl);
//            options.put("order_id", "order_DBJOWzybf0sJbdsds");//from response of step 3.
            options.put("theme.color", themeColor);
            options.put("currency", "INR");
            options.put("amount", (amt*100));//pass amount in currency subunits
//            options.put("amount", (500));//pass amount in currency subunits
            options.put("prefill.email", session_management.getUserDetails().get(KEY_EMAIL));
            options.put("prefill.contact",session_management.getUserDetails().get(KEY_MOBILE));
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout"+ e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.e(TAG, "onPaymentSuccess: "+s.toString() );
        saveInfoIntoDatabase(common.getUserId(),pnts,requestStatus,"Add",s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e(TAG, "onPaymentError: "+s );
        common.showToast("Payment Failed. Try again later");
    }

    public void getGatewaySetting(){
        HashMap<String,String> params=new HashMap<>();
        CustomVolleyJsonArrayRequest arrReq=new CustomVolleyJsonArrayRequest(Request.Method.POST, URL_GET_GATEWAY, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    imageUrl=response.getJSONObject(0).getString("icon");
                    themeColor=response.getJSONObject(0).getString("theme_color");
                    desc=response.getJSONObject(0).getString("description");
                    requestStatus=response.getJSONObject(0).getString("request_status");
                    gatewayStatus=response.getJSONObject(0).getString("gateway_status");
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                common.showVolleyError(error);
            }
        });
        AppController.getInstance().addToRequestQueue(arrReq);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:

                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}