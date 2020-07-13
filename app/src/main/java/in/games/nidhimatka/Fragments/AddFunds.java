package in.games.nidhimatka.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
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

import java.util.HashMap;
import java.util.Map;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.BaseUrls;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Prevalent.Prevalent;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.ConnectivityReceiver;
import in.games.nidhimatka.Util.CustomJsonRequest;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.networkconnectivity.NoInternetConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFunds extends Fragment implements View.OnClickListener {
    Common common;
    EditText etPoints;
    LoadingBar progressDialog;
    private TextView bt_back,txtMatka;
    Button btnRequest;
    private TextView txtWallet_amount;
    int min_amount ;
    public AddFunds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_add_funds, container, false);
       initViews(view);
       return  view ;
    }

    void initViews(View v)
    {
        ((MainActivity) getActivity()).setTitle("Add Funds");
        txtMatka=(TextView)v.findViewById(R.id.board);
        etPoints=(EditText)v.findViewById(R.id.etRequstPoints);
        btnRequest=(Button)v.findViewById(R.id.add_Request);
        progressDialog=new LoadingBar(getActivity());
        bt_back=(TextView)v.findViewById(R.id.txtBack);
        txtWallet_amount=(TextView)v.findViewById(R.id.wallet_amount);
        common=new Common(getActivity());
        btnRequest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_Request)
        {


            int points=Integer.parseInt(etPoints.getText().toString().trim());

            if(TextUtils.isEmpty(etPoints.getText().toString()))
            {
                etPoints.setError("Enter Some Points");
                return;
            }
            else
            {
                if(points<min_amount)
                {
                    common.errorMessageDialog("Minimum Range for points is "+ min_amount);

                }
                else
                {
                    String user_id= common.getUserId();
                    String p=String.valueOf(points);
                    String st="pending";
                    if (ConnectivityReceiver.isConnected()) {
                        saveInfoIntoDatabase(user_id, p, st, "Add");
                    } else
                    {
                        Intent intent = new Intent(getActivity(), NoInternetConnection.class);
                        startActivity(intent);
                    }
                }
            }
        }
    }
    private void saveInfoIntoDatabase(final String user_id, final String points, final String st,String type) {
       progressDialog.show();
       HashMap<String,String> params=new HashMap<>();
       params.put("user_id",user_id);
       params.put("points",points);
       params.put("request_status",st);
       params.put("type",type);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REQUEST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                     boolean resp=response.getBoolean("responce");
                     if(resp)
                     {
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
