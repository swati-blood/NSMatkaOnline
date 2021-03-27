package in.matka.ns.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.matka.ns.AppController;
import in.matka.ns.Common.Common;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.R;
import in.matka.ns.Util.CustomJsonRequest;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.Module;
import in.matka.ns.Util.Session_management;

import static in.matka.ns.Config.Constants.KEY_ID;
import static in.matka.ns.Config.Constants.KEY_PAYTM;
import static in.matka.ns.Config.Constants.KEY_PHONEPAY;
import static in.matka.ns.Config.Constants.KEY_TEZ;
import static in.matka.ns.Config.Constants.KEY_UPI;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment implements View.OnClickListener {
    LinearLayout lin_user, lin_bank , lin_gpay , lin_paytm , lin_phonepe, lin_upi;
    EditText et_gpay ,et_paytm ,et_phonepe,et_upi;
    Button btnSubmit ;
    String phonepay="" , paytm="" , gpay ="" ,u_id, upi="";
    Session_management session_management ;
    LoadingBar loadingBar ;
    Module module ;
    Common common ;

    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_my_profile, container, false);
      intiViews(view);
      return view;
    }
    void intiViews(View v)
    {
        lin_bank = v.findViewById(R.id.lin_bank);
        lin_user = v.findViewById(R.id.lin_user);
        lin_gpay = v.findViewById(R.id.lin_gpay);
        lin_paytm = v.findViewById(R.id.lin_paytm);
        lin_phonepe = v.findViewById(R.id.lin_phonepe);
        et_gpay = v.findViewById(R.id.et_gpay);
        et_paytm = v.findViewById(R.id.et_paytm);
        et_phonepe = v.findViewById(R.id.et_phonepay);
        et_upi = v.findViewById(R.id.et_upi);
        lin_upi =v.findViewById(R.id.lin_upi);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        lin_user.setOnClickListener(this);
        lin_gpay.setOnClickListener(this);
        lin_paytm.setOnClickListener(this);
        lin_phonepe.setOnClickListener(this);
        lin_bank.setOnClickListener(this);
        loadingBar = new LoadingBar(getActivity());
        common = new Common(getActivity());
        session_management = new Session_management(getActivity());
        String x=common.checkNull(session_management.getUserDetails().get(KEY_PHONEPAY).toString());
        String tz=common.checkNull(session_management.getUserDetails().get(KEY_TEZ).toString());
        String p=common.checkNull(session_management.getUserDetails().get(KEY_PAYTM).toString());
        String up=common.checkNull(session_management.getUserDetails().get(KEY_UPI).toString());
//        Log.e("lkjhgf",up);
        Log.e("bhnjmk","kjhgfd");

        u_id = session_management.getUserDetails().get(KEY_ID);
        common.setDataEditText(et_phonepe,x);
        common.setDataEditText(et_paytm,p);
        common.setDataEditText(et_gpay,tz);
        common.setDataEditText(et_upi,up);



    }

    @Override
    public void onClick(View v) {
        Fragment fm = null;
        if (v.getId()== R.id.lin_user)
        {
            fm = new UserFragment();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();

        }
        else if (v.getId()==R.id.lin_bank)
        {
            fm = new BankFragment();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }else if (v.getId()==R.id.lin_upi)
        {
            et_upi.setEnabled(true);
        }
        else if (v.getId()==R.id.lin_gpay){
            et_gpay.setEnabled(true);
        }
             else if (v.getId()==R.id.lin_paytm){
            et_paytm.setEnabled(true);
        }
        else if (v.getId()==R.id.lin_phonepe){
            et_phonepe.setEnabled(true);
        }
        else if (v.getId()==R.id.btnSubmit)
        {
           gpay = et_gpay.getText().toString();
           paytm = et_paytm.getText().toString();
           phonepay = et_phonepe.getText().toString();
           upi =et_upi.getText().toString();
           storeAccDetails(gpay,paytm,phonepay,u_id,upi);

        }

    }
    private void storeAccDetails(final String teznumber, final String paytmno , final String phonepay, final String mailid,final String upi) {

       loadingBar.show();
        final Map<String,String> params=new HashMap<>();
        params.put("key","4");
        params.put("user_id",mailid);
        params.put("upi",upi);
        params.put("tez",teznumber);
        params.put("paytm",paytmno);
        params.put("phonepay",phonepay);
        Log.e("acc_params",params.toString());

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REGISTER, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingBar.dismiss();
                try {
                    boolean resp=response.getBoolean("responce");
                    Log.e("accounts",response.toString()+"\n"+params);
                    if(resp)
                    {
                        session_management.updatePaymentSection(upi,teznumber,paytmno,phonepay);
                        Log.e("kmjnhbgfcx",session_management.getUserDetails().get(KEY_UPI));
                        common.showToast(""+response.getString("message"));

                    }
                    else
                    {
                        common.showToast(""+response.getString("message"));
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
                    common.showToast(msg);
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,"json_tez");

    }
}
