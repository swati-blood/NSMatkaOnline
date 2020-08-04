package in.games.nidhimatka.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.BaseUrls;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Prevalent.Prevalent;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.CustomJsonRequest;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.Session_management;
import in.games.nidhimatka.Util.ToastMsg;

import static in.games.nidhimatka.Activity.splash_activity.min_add_amount;
import static in.games.nidhimatka.Activity.splash_activity.withdrw_text;
import static in.games.nidhimatka.Config.Constants.KEY_WALLET;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawFundsFragment extends Fragment implements View.OnClickListener {
    Common common;
    private TextView txtback,txtWalletAmount,txtMobile ,txt_withdrw_instrctions;
    private LoadingBar progressDialog;
    private EditText etPoint;
    ToastMsg toastMsg ;
    private Button btnSave;
    String saveCurrentDate,saveCurrentTime;
    int day,hours;
    Session_management session_management;
    int w_amt ;
    public WithdrawFundsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_withdraw_funds, container, false);
       initView(view);
       return  view ;
    }

    public  void initView(View v)
    {
        ((MainActivity) getActivity()).setTitle("Withdraw Funds");
        common=new Common(getActivity());
       toastMsg=new ToastMsg(getActivity());
        session_management=new Session_management(getActivity());
        txtback=(TextView)v.findViewById(R.id.txtBack);
        txtWalletAmount=(TextView)v.findViewById(R.id.wallet_amount);
        txtWalletAmount.setVisibility(View.GONE);
//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        TextView txtWalet=  toolbar.findViewById(R.id.txtWallet);
        w_amt = Integer.parseInt(session_management.getUserDetails().get(KEY_WALLET));
        etPoint=(EditText)v.findViewById(R.id.etRequstPoints);
        btnSave=(Button)v.findViewById(R.id.add_Request);
        txtMobile=(TextView)v.findViewById(R.id.textview5);
        txt_withdrw_instrctions =v.findViewById(R.id.withdrw_msg);
        progressDialog=new LoadingBar(getActivity());
        txt_withdrw_instrctions.setText(withdrw_text);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.add_Request)
        {

            String points=etPoint.getText().toString().trim();

            if(TextUtils.isEmpty(points))
            {
                etPoint.setError("Enter Some points");
                return;
            }
            else
            {

                Calendar calendar=Calendar.getInstance();

                SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate=currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss");
                saveCurrentTime=currentTime.format(calendar.getTime());

                day=calendar.get(Calendar.DAY_OF_WEEK);


                String a[]=saveCurrentTime.split(":");
                hours=Integer.parseInt(a[0]);
                //  Toast.makeText(WithdrawalActivity.this, ""+day +hours, Toast.LENGTH_SHORT).show();
//                if((hours>=10&&hours<17)&&(day>1 && day<7))
                if(day>1 && day<7)
                {
                    String user_id = common.getUserId();
                    String pnts = String.valueOf(points);
                    String st = "pending";

                    int t_amt = Integer.parseInt(pnts);
                    if (w_amt > 0) {

                        if(t_amt<Integer.parseInt(min_add_amount))
                        {
                            toastMsg.toastIconError(("Minimum Withdraw amount "+min_add_amount));
                        }
                        else
                        {
                            if (t_amt > w_amt) {
                                toastMsg.toastIconError("Your requested amount exceeded");
                                return;
                            } else {
                                // saveInfoIntoDatabase(user_id, String.valueOf(t_amt), st);
                                saveInfoIntoDatabase(user_id,String.valueOf(t_amt),st,"Withdrawal");
                            }
//
                        }

                    } else {
                        toastMsg.toastIconError("You don't have enough points in wallet ");
                    }

                }
                else{
                    toastMsg.toastIconError("Time Out ");
                    return;

                }

//                        saveInfoIntoDatabase(user_id,pnts,st);
            }

        }
    }

    private void saveInfoIntoDatabase(final String user_id, final String points, final String st,String type) {
        progressDialog.show();
        int wl=Integer.parseInt(session_management.getUserDetails().get(KEY_WALLET));
        final String rem=String.valueOf(wl-Integer.parseInt(points));
        HashMap<String,String> params=new HashMap<>();
        params.put("user_id",user_id);
        params.put("points",points);
        params.put("request_status",st);
        params.put("type",type);
        params.put("wallet",rem);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REQUEST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    boolean resp=response.getBoolean("responce");
                    if(resp)
                    {
                        session_management.updateWallet(rem);
                        common.showToast(""+response.getString("message"));

                        loadFragment();
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

    public void loadFragment()
    {
        Fragment fm  = new HomeFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                .addToBackStack(null).commit();

    }
}
