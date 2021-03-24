package in.matka.NS.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.matka.NS.AppController;
import in.matka.NS.Common.Common;
import in.matka.NS.Config.BaseUrls;
import in.matka.NS.R;
import in.matka.NS.Util.ConnectivityReceiver;
import in.matka.NS.Util.CustomJsonRequest;
import in.matka.NS.Util.LoadingBar;
import in.matka.NS.Util.Session_management;
import in.matka.NS.networkconnectivity.NoInternetConnection;

import static in.matka.NS.Config.Constants.KEY_ADDRESS;
import static in.matka.NS.Config.Constants.KEY_CITY;
import static in.matka.NS.Config.Constants.KEY_DOB;
import static in.matka.NS.Config.Constants.KEY_EMAIL;
import static in.matka.NS.Config.Constants.KEY_GENDER;
import static in.matka.NS.Config.Constants.KEY_ID;
import static in.matka.NS.Config.Constants.KEY_MOBILE;
import static in.matka.NS.Config.Constants.KEY_NAME;
import static in.matka.NS.Config.Constants.KEY_PINCODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    EditText et_name , et_mobile ,et_email,et_add ,et_city ,et_pin;
    TextView tv_date;
    Button btnSubmit ;
    LoadingBar progressDialog;
    LoadingBar loadingBar ;
    //ProgressDialog progressDialog;
    RadioButton rc_male , rc_female;
    Session_management session_management ;
    String name , mobile, email ,add , gender ="" ,date ,city ,pin,u_id ;

    Common common;
    int   year,month,day;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initViews(view);
        return  view ;
    }
    void initViews(View v)
    {

        et_name= v.findViewById(R.id.etName);
        et_mobile= v.findViewById(R.id.etMobile);
        et_email= v.findViewById(R.id.et_email);
        et_add= v.findViewById(R.id.etAddress);
        et_city= v.findViewById(R.id.etCity);
        et_pin= v.findViewById(R.id.etPin);
        tv_date= v.findViewById(R.id.et_dob);
        btnSubmit= v.findViewById(R.id.btnSubmit);
        rc_female= v.findViewById(R.id.female);
        rc_male= v.findViewById(R.id.male);
        progressDialog = new LoadingBar(getActivity());
      //  progressDialog = new ProgressDialog (getActivity());
        common = new Common(getActivity());
        session_management = new Session_management(getActivity());
        //loadingBar.show ();
       // progressDialog.show ();
        rc_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    gender = "Male";
                }
                else
                {
                    gender = "Female";
                }
//                Toast.makeText(getActivity(),""+gender,Toast.LENGTH_LONG).show();
            }
        });
        rc_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    gender = "Female";
                }
                else
                {
                    gender = "Male";
                }
//                Toast.makeText(getActivity(),""+gender,Toast.LENGTH_LONG).show();
            }
        });
        btnSubmit.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        String ad= common.checkNull(session_management.getUserDetails().get(KEY_ADDRESS));
        String ct=common.checkNull(session_management.getUserDetails().get(KEY_CITY));
        String pn=common.checkNull(session_management.getUserDetails().get(KEY_PINCODE));
        String mobile = common.checkNull(session_management.getUserDetails().get(KEY_MOBILE));
        String email = common.checkNull(session_management.getUserDetails().get(KEY_EMAIL));
        String dob = common.checkNull(session_management.getUserDetails().get(KEY_DOB));
        String gen = common.checkNull(session_management.getUserDetails().get(KEY_GENDER));
        String nm = common.checkNull(session_management.getUserDetails().get(KEY_NAME));
        if (gen.equals("Male"))
        {
            rc_male.setChecked(true);
            rc_female.setChecked(false);
        }
        else
        {
            rc_male.setChecked(false);
            rc_female.setChecked(true);
        }
       u_id = common.checkNull(session_management.getUserDetails().get(KEY_ID));
        et_mobile.setText(mobile);
        tv_date.setText(dob);
        et_mobile.setEnabled(false);
        common.setDataEditText(et_email,email);
        common.setDataEditText(et_name,nm);
        common.setDataEditText(et_add,ad);
        common.setDataEditText(et_city,ct);
        common.setDataEditText(et_pin,pn);

    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnSubmit)
        {
            name = et_name.getText().toString();
            mobile = et_mobile.getText().toString();
            email = et_email.getText().toString();
            date = tv_date.getText().toString();
           add = et_add.getText().toString();
            city = et_city.getText().toString();
           pin = et_pin.getText().toString();

            if(TextUtils.isEmpty(name))
            {
                et_mobile.setError("Enter Name");
                et_mobile.requestFocus();
                return;
            }
           else if(TextUtils.isEmpty(mobile))
            {
                et_mobile.setError("Enter Mobile Number");
                et_mobile.requestFocus();
                return;
            }
//            else if(TextUtils.isEmpty(email))
//            {
//                et_email.setError("Enter email Address");
//                et_email.requestFocus();
//                return;
//            }
            if (email.length()>0)
            {
                if (!email.matches(emailPattern)) {
                    et_email.setError("Enter valid email Address");
                    et_email.requestFocus();
                    return;
                }
            }
            if(TextUtils.isEmpty(date))
            {
                tv_date.setError("Enter Date of Birth");
                tv_date.requestFocus();
                return;
            }
            else if(TextUtils.isEmpty(add))
            {
                et_add.setError("Enter your Address");
                et_add.requestFocus();
                return;

            }
            else if(TextUtils.isEmpty(city))
            {
                et_city.setError("Enter city name");
                et_city.requestFocus();
                return;

            } else if(TextUtils.isEmpty(pin))
            {
                et_pin.setError("Enter pin code");
                et_pin.requestFocus();
                return;

            }
            if (gender.isEmpty())
            {
                rc_male.requestFocus();
                common.showToast("Select Gender");
            }
            else
            {
                if (ConnectivityReceiver.isConnected()) {
                    storeProfileData(date, name, gender, email);
                    storeAddressData(add, city, pin, u_id);

                }
                else
                {
                    Intent intent = new Intent(getActivity(), NoInternetConnection.class);
                    startActivity(intent);
                }

            }
        }
        else if (v.getId()==R.id.et_dob)
        {
           selectDate();
        }
    }
    private void selectDate() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                year  = year;
                month = month;
                day   = dayOfMonth;

                // Show selected date
                tv_date.setText(new StringBuilder().append(day).append("-")
                        .append(month + 1) .append("-").append(year)
                        .append(" "));

            }
        },year,month,day);
        datePickerDialog.show();

    }
    private void storeProfileData(final String dob , final String name, final String gender , final String email)
    {   progressDialog.show();
        Map<String,String> params=new HashMap<>();
        params.put("key","5");
        params.put("email",email);
        params.put("dob",dob);
        params.put("name",name);
        params.put("gender",gender);
        params.put("user_id",u_id);
        Log.e("profile",params.toString());

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REGISTER, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    boolean resp=response.getBoolean("responce");
                    if(resp)
                    {
                        session_management.updateEmailSection(email,dob,gender,name);
                        common.showToast(""+response.getString("message"));

                    }
                    else
                    {
                        common.showToast(""+response.getString("error"));
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    common.showToast(ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(msg);
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,"json_tez");

    }
    private void storeAddressData(final String a,final String c,final String p,final String mob) {

        progressDialog.show();
        String json_tag="add_address";
        Map<String,String> params=new HashMap<>();
        params.put("key","2");
        params.put("address",a);
        params.put("city",c);
        params.put("pin",p);
        params.put("user_id",mob);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REGISTER, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    boolean resp=response.getBoolean("responce");
                    if(resp)
                    {
                        session_management.updateAddressSection(a,c,p);
//                        common.showToast(""+response.getString("message"));
                        common.showToast("Updated Successfully");


                    }
                    else
                    {
                        common.showToast(""+response.getString("error"));
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    common.showToast(ex.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(msg);
                }
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);

    }
}
