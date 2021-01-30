package in.matka.rose.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import in.matka.rose.AppController;
import in.matka.rose.Common.Common;
import in.matka.rose.Config.BaseUrls;
import in.matka.rose.R;
import in.matka.rose.Util.ConnectivityReceiver;
import in.matka.rose.Util.CustomJsonRequest;
import in.matka.rose.networkconnectivity.NoInternetConnection;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    String mobile = "";
    private Button btnRegister;
    TextView txt_back ;
    Common common;

    CheckBox checkbox;
    private EditText txtName,txtMobile,txtPass,txtConPass,txtUserName;
//    static String URL_REGIST="http://anshuwap.com/AddaApp/register.php";

    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkbox=findViewById (R.id.checkbox);

        txt_back = findViewById(R.id.txt_back);
        txtName=(EditText)findViewById(R.id.etName);
       common=new Common(RegisterActivity.this);
        txtMobile=(EditText)findViewById(R.id.etMobile);
        txtPass=(EditText)findViewById(R.id.etPass);
        txtConPass=(EditText)findViewById(R.id.etConPass);
        txtUserName=(EditText)findViewById(R.id.etUserName);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        progressDialog=new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading...");
      //  progressDialog.setMessage("Please wait for a moment");
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mobile = getIntent().getStringExtra("mobile");
        txtMobile.setText(mobile);
        txtMobile.setEnabled(false);

      btnRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
//              String em=txtEmail.getText().toString().trim();
              if(TextUtils.isEmpty(txtUserName.getText().toString()))
              {
                  txtUserName.setError("Please Enter User name");
                  txtUserName.requestFocus();
                  return;
              }
              else if(TextUtils.isEmpty(txtName.getText().toString()))
              {
                  txtName.setError("Please Enter name");
                  txtName.requestFocus();
                  return;
              }
              else  if(TextUtils.isEmpty(txtMobile.getText().toString()))
              {
                  txtMobile.setError("Please enter mobile number");
                  txtMobile.requestFocus();
                  return;
              }else  if(TextUtils.isEmpty(txtPass.getText().toString()))
              {
                  txtPass.setError("Please enter password");
                  txtPass.requestFocus();
                  return;
              }else  if(TextUtils.isEmpty(txtConPass.getText().toString()))
              {
                  txtConPass.setError("Please re-enter password");
                  txtConPass.requestFocus();
                  return;
              }
              else if(!checkbox.isChecked ()){
                  Toast.makeText (RegisterActivity.this, "Please Accept Terms and Conditions", Toast.LENGTH_SHORT).show ( );

              }

              else
              {
                  String phone_value=txtMobile.getText().toString().trim();
                  int sf= Integer.parseInt(phone_value.substring(0,1));
                  int len=phone_value.length();

                  if(sf<6 || len<10)
                  {
                      Toast.makeText(RegisterActivity.this,"Invalid Mobile number \n" +
                              "mobile number never start with 0 and <6", Toast.LENGTH_LONG).show();
                  }
                  else
                  {
                      String pass=txtPass.getText().toString().trim();
                      String conpass=txtConPass.getText().toString().trim();
                      if(pass.equals(conpass))
                      {
                          if (ConnectivityReceiver.isConnected()) {

                              register(phone_value);
                          }
                          else
                          {
                              Intent intent = new Intent(RegisterActivity.this, NoInternetConnection.class);
                              startActivity(intent);
                          }

                      }
                      else
                      {
                          Toast.makeText(RegisterActivity.this,"password must be matched", Toast.LENGTH_LONG).show();
                          return;
                      }

                  }
              }


          }
      });





    }


    public String validMobile(String phone)
    {
        String value="";
        int len=phone.length();

        switch (len)
        {
            case 10:
                value=phone;
                break;
            case 13:
                value=phone.substring(3,13);
                break;
            default:
                value="invalid";

        }

        return value;
    }


    private void register(String phone_value) {

        progressDialog.show();
        final String uname=txtUserName.getText().toString().trim();
        final String fname=txtName.getText().toString().trim();
        final String fmobile=phone_value;
        final String fpass=txtPass.getText().toString().trim();
        final String fconpass=txtConPass.getText().toString().trim();

        HashMap<String,String> params=new HashMap<>();
        params.put("key","1");
        params.put("username",uname);
        params.put("name",fname);
        params.put("mobile",fmobile);
        params.put("password",fpass);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REGISTER, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
               try {
                  boolean resp=response.getBoolean("responce");
                  if(resp)
                  {
                      common.showToast(""+response.getString("message").toString());
                      Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                      startActivity(intent);
                      finish();

                  }
                  else
                  {
                      common.showToast(response.getString("error").toString());
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
}
