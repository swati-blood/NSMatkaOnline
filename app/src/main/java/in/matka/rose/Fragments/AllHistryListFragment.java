package in.matka.rose.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.matka.rose.Activity.MainActivity;
import in.matka.rose.Adapter.BidHistoryAdapter;
import in.matka.rose.Adapter.Request_HistoryAdapter;
import in.matka.rose.Adapter.TransactionHistoryAdapter;
import in.matka.rose.Adapter.Withdraw_request_Adapter;
import in.matka.rose.AppController;
import in.matka.rose.Common.Common;
import in.matka.rose.Config.BaseUrls;
import in.matka.rose.Model.BidHistoryObjects;
import in.matka.rose.Model.TransactionHistoryObjects;
import in.matka.rose.Model.Withdraw_requwset_obect;
import in.matka.rose.Objects.Fund_Request_HistoryObject;
import in.matka.rose.R;
import in.matka.rose.Util.ConnectivityReceiver;
import in.matka.rose.Util.CustomVolleyJsonArrayRequest;
import in.matka.rose.Util.LoadingBar;
import in.matka.rose.Util.ToastMsg;
import in.matka.rose.networkconnectivity.NoInternetConnection;

import static in.matka.rose.Config.BaseUrls.URL_BID_HISTORY;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllHistryListFragment extends Fragment {
    RecyclerView rv_histry ;
    String type ="";
    String matka_id="" ,user_id = "";
    LoadingBar progressDialog ;
    ArrayList<BidHistoryObjects> bid_list;
    ArrayList<TransactionHistoryObjects> trans_list;
    ArrayList<Fund_Request_HistoryObject> fund_list;
    ArrayList<Withdraw_requwset_obect> w_list;
    Common common ;
    BidHistoryAdapter bidHistoryAdapter ;
    TransactionHistoryAdapter transactionHistoryAdapter ;
    Withdraw_request_Adapter withdrawRequestAdapter ;
    Request_HistoryAdapter request_historyAdapter ;

    public AllHistryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_all_histry_list, container, false);
       initViews(view);
       return  view ;
    }
    public void initViews(View v)
    {
        ((MainActivity) getActivity()).setTitle("Select Game");
        rv_histry = v.findViewById(R.id.recyclerView);
        rv_histry.setLayoutManager(new LinearLayoutManager(getActivity()));
        type = getArguments().getString("type");
        progressDialog = new LoadingBar(getActivity());
        common = new Common(getActivity());
        user_id = common.getUserId();
        bid_list = new ArrayList<>();
        trans_list = new ArrayList<>();
        fund_list = new ArrayList<>();
        w_list = new ArrayList<>();
        if (ConnectivityReceiver.isConnected()) {
            if (type.equalsIgnoreCase("transaction")) {
                getTranssactionHistoryData(user_id);
            } else if (type.equalsIgnoreCase("bid")) {
                matka_id = getArguments().getString("matka_id");
                getBidData(user_id, matka_id);

            } else if (type.equalsIgnoreCase("withdraw")) {
                getWithdrawData(user_id);
            } else if (type.equalsIgnoreCase("funds")) {
                getRequestData(user_id);
            }
        }   else
        {
            Intent intent = new Intent(getActivity(), NoInternetConnection.class);
            startActivity(intent);
        }

    }

    private void getBidData(final String user_id, final String matka_id) {

        final String json_request_tag="json_bid_history_tag";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("us_id",user_id);
        params.put("matka_id",matka_id);

        progressDialog.show();

        bid_list.clear();

        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.POST, URL_BID_HISTORY, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("histort_bid",response.toString());
                try
                {
                    JSONArray jsonArray=response;
                    String h=jsonArray.getString(0);
                    if(h.equals("null") || h.equals(null))
                    {
                        progressDialog.dismiss();
                        new ToastMsg(getActivity()).toastIconError(("No history for this Matka"));
                    }
                    else
                    {

                        for(int i=0; i<=jsonArray.length()-1;i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            BidHistoryObjects matkasObjects = new BidHistoryObjects();
                            matkasObjects.setId(jsonObject.getString("id"));
                            matkasObjects.setUser_id(jsonObject.getString("user_id"));
                            matkasObjects.setMatka_id(jsonObject.getString("matka_id"));
                            matkasObjects.setBet_type(jsonObject.getString("bet_type"));
                            matkasObjects.setPoints(jsonObject.getString("points"));
                            matkasObjects.setDigits(jsonObject.getString("digits"));
                            matkasObjects.setDate(jsonObject.getString("date"));
                            matkasObjects.setTime(jsonObject.getString("time"));
                            matkasObjects.setName(jsonObject.getString("name"));
                            matkasObjects.setGame_id(jsonObject.getString("game_id"));
                            matkasObjects.setStatus(jsonObject.getString("status"));
                            matkasObjects.setPlay_for(jsonObject.getString("play_for"));
                            matkasObjects.setPlay_on(jsonObject.getString("play_on"));
                            matkasObjects.setDay(jsonObject.getString("day"));
                           bid_list.add(matkasObjects);


                        }
                        bidHistoryAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();


                    }
                    //progressDialog.dismiss();

                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                 new ToastMsg(getActivity()).toastIconError("Something went wrong");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                common.showVolleyError(error);

            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_request_tag);




    }
    private void getTranssactionHistoryData(final String user_id) {

        progressDialog.show();
        trans_list.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrls.Url_transaction_history, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("success"))
                    {

                        JSONArray jsonArray=jsonObject.getJSONArray("msg");

                        for (int i=0; i<jsonArray.length();i++)
                        {
                            JSONObject objects=jsonArray.getJSONObject(i);

                            TransactionHistoryObjects objects1=new TransactionHistoryObjects();

                            objects1.setId(objects.getString("id"));
                            objects1.setAmt(objects.getString("amt"));
                            objects1.setBid_id(objects.getString("bid_id"));
                            objects1.setDate(objects.getString("date"));
                            objects1.setDigits(objects.getString("digits"));
                            objects1.setGame_id(objects.getString("game_id"));
                            objects1.setMatka_id(objects.getString("matka_id"));
                            objects1.setTime(objects.getString("time"));
                            objects1.setType(objects.getString("type"));
                            objects1.setUser_id(objects.getString("user_id"));



                           trans_list.add(objects1);
                        }

                        transactionHistoryAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                    else if(status.equals("unsuccessful"))
                    {

                    }
                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    new ToastMsg(getActivity()).toastIconError(""+ex.getMessage());
                    return;
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new ToastMsg(getActivity()).toastIconError("Error"+error.toString());
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("us_id",user_id);
                //  params.put("matka_id",matka_id);


                // params.put("phonepay",phonepaynumber);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
    private void getWithdrawData(final String user_id) {

        progressDialog.show();

        w_list.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrls.Url_wthdraw_req_history, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("wdrw_histry",response.toString());

                if(response.equals("empty"))
                {
                    progressDialog.dismiss();
                    new ToastMsg(getActivity()).toastIconError("empty");
//                        Log.e("Volley",error.toString());

                }
                else
                {
                    try
                    {
                        JSONArray jsonArray=new JSONArray(response);
                        //progressDialog.dismiss();
                        for(int i=0; i<=jsonArray.length()-1;i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Withdraw_requwset_obect matkasObjects = new Withdraw_requwset_obect();
                            matkasObjects.setId(jsonObject.getString("id"));
                            matkasObjects.setWithdraw_points(jsonObject.getString("withdraw_points"));
                            matkasObjects.setTime(jsonObject.getString("time"));
                            matkasObjects.setWithdraw_status(jsonObject.getString("withdraw_status"));
                            matkasObjects.setUser_id(jsonObject.getString("user_id"));


                            w_list.add(matkasObjects);


                        }
                        request_historyAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }
                    catch (Exception ex)
                    {
                        new ToastMsg(getActivity()).toastInfo("There is no history");
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();
                    }
                }


                //  Toast.makeText(BidActivity.this,response,Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        new ToastMsg(getActivity()).toastIconError("Error"+error.toString());
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("user_id",user_id);



                // params.put("phonepay",phonepaynumber);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void getRequestData(final String user_id) {

        progressDialog.show();
        String json_tag="json_req";
        final HashMap<String,String> params=new HashMap<String,String>();
        params.put("user_id",user_id);

        fund_list.clear();
        CustomVolleyJsonArrayRequest customJsonRequest=new CustomVolleyJsonArrayRequest(Request.Method.POST, BaseUrls.Url_req_history, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("asdasd",""+response.toString());
                try
                {
                    if( response.length()!=0)
                    {
//                        String status=response.getString("status");
//                        if(status.equals("success"))
//                        {
//                            JSONArray jsonArray=response.getJSONArray("data");
//                            progressDialog.dismiss();
                        for(int i=0; i<=response.length()-1;i++) {

                            JSONObject jsonObject = response.getJSONObject(i);

                            Fund_Request_HistoryObject matkasObjects = new Fund_Request_HistoryObject();
                            matkasObjects.setRequest_id(jsonObject.getString("request_id"));
                            matkasObjects.setRequest_points(jsonObject.getString("request_points"));
                            matkasObjects.setTime(jsonObject.getString("time"));
                            matkasObjects.setRequest_status(jsonObject.getString("request_status"));
                            matkasObjects.setUser_id(jsonObject.getString("user_id"));


                            fund_list.add(matkasObjects);


                        }
                        request_historyAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
//
                    }
                    else
                    {
                        new ToastMsg(getActivity()).toastInfo("No History Found");
                    }

                }
                catch (Exception ex)
                {
//                    Toast.makeText(getActivity(),"There is no history for this game",Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                new ToastMsg(getActivity()).toastIconError((msg));

            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);
    }

}
