package in.matka.NS.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

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

import static in.matka.NS.Config.Constants.KEY_ACCOUNNO;
import static in.matka.NS.Config.Constants.KEY_BANK_NAME;
import static in.matka.NS.Config.Constants.KEY_HOLDER;
import static in.matka.NS.Config.Constants.KEY_ID;
import static in.matka.NS.Config.Constants.KEY_IFSC;

public class BankFragment extends Fragment implements View.OnClickListener {
    EditText et_b_name ,et_acc_no ,et_ifsc,et_name;
    Button btnSubmit ;
    Session_management session_management ;
    String name , b_name, acc_no ,ifsc,u_id ;
    LoadingBar progressDialog;
    Common common;
    public BankFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_bank, container, false);
       initViews(v);
       return  v ;
    }
    void initViews(View v)
    {
        et_acc_no=(EditText)v.findViewById(R.id.etAccNo);
        et_b_name=(EditText)v.findViewById(R.id.etBankName);
        et_ifsc=(EditText)v.findViewById(R.id.etIfsc);
        et_name=(EditText)v.findViewById(R.id.etHName);
        btnSubmit = v.findViewById(R.id.add_bank);
        progressDialog = new LoadingBar(getActivity());
        common = new Common(getActivity());
        session_management = new Session_management(getActivity());
        btnSubmit.setOnClickListener(this);
        String acc= common.checkNull(session_management.getUserDetails().get(KEY_ACCOUNNO));
        String nm=common.checkNull(session_management.getUserDetails().get(KEY_HOLDER));
        String bn=common.checkNull(session_management.getUserDetails().get(KEY_BANK_NAME));
        String iff= common.checkNull(session_management.getUserDetails().get(KEY_IFSC));

        u_id = common.checkNull(session_management.getUserDetails().get(KEY_ID));

        common.setDataEditText(et_acc_no,acc);
        common.setDataEditText(et_name,nm);
        common.setDataEditText(et_b_name,bn);
        common.setDataEditText(et_ifsc,iff);

    }

    @Override
    public void onClick(View v) {
          if (v.getId()==R.id.add_bank)
        {

           acc_no=et_acc_no.getText().toString().trim();
            b_name=et_b_name.getText().toString().trim();
          ifsc=et_ifsc.getText().toString().trim();
           name=et_name.getText().toString().trim();

            if(TextUtils.isEmpty(acc_no))
            {
                et_acc_no.setError("Enter your account number");
                et_acc_no.requestFocus();
                return;

            }
            else if(TextUtils.isEmpty(b_name))
            {
                et_b_name.setError("Enter Bank Name");
                et_b_name.requestFocus();
                return;

            } else if(TextUtils.isEmpty(ifsc))
            {
                et_ifsc.setError("Enter ifsc code");
                et_ifsc.requestFocus();
                return;

            } else if(TextUtils.isEmpty(name))
            {
                et_name.setError("Enter acc holder name");
               et_name.requestFocus();
                return;

            }
            else
            {
                if(ConnectivityReceiver.isConnected())
                {
                storeBankDetails(acc_no,b_name,ifsc,name,u_id);}
                else
                {
                    Intent intent = new Intent(getActivity(), NoInternetConnection.class);
                    startActivity(intent);
                }
            }


        }
    }
    private void storeBankDetails(final String accno,final String bankname,final String ifsc,final String hod_name,final String mailid) {

        progressDialog.show();
        Map<String,String> params=new HashMap<>();
        params.put("key","3");
        params.put("user_id",mailid);
        params.put("accountno",accno);
        params.put("bankname",bankname);
        params.put("ifsc",ifsc);
        params.put("accountholder",hod_name);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REGISTER, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                try {
                    boolean resp=response.getBoolean("responce");
                    if(resp)
                    {
                        session_management.updateAccSection(accno,bankname,ifsc,hod_name);
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
                    common.showToast("Something went wrong");
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

        AppController.getInstance().addToRequestQueue(customJsonRequest,"add_bank_json");

    }
}
