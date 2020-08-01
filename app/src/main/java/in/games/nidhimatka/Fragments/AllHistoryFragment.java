package in.games.nidhimatka.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Adapter.AllHistoryAdapter;
import in.games.nidhimatka.Adapter.FundsHistryAdapter;
import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Model.HistryModel;
import in.games.nidhimatka.Model.RequestModel;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.ConnectivityReceiver;
import in.games.nidhimatka.Util.CustomJsonRequest;
import in.games.nidhimatka.Util.CustomVolleyJsonArrayRequest;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.Module;
import in.games.nidhimatka.Util.Session_management;
import in.games.nidhimatka.Util.ToastMsg;
import in.games.nidhimatka.networkconnectivity.NoInternetConnection;

import static in.games.nidhimatka.Config.BaseUrls.URL_FUND_HISTORY;
import static in.games.nidhimatka.Config.BaseUrls.URL_GET_HISTORY;
import static in.games.nidhimatka.Config.Constants.KEY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllHistoryFragment extends Fragment {
    RecyclerView rv_hsitry;
    Module module ;
    ArrayList<HistryModel>bid_list;
    ArrayList<RequestModel>req_list;
    LoadingBar loadingBar;
    AllHistoryAdapter allHistoryAdapter ;
    FundsHistryAdapter fundsHistryAdapter;
    Session_management session_management ;
    String user_id ,type ;
    ToastMsg toastMsg ;

    public AllHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view = inflater.inflate(R.layout.fragment_all_history, container, false);
     rv_hsitry = view.findViewById(R.id.rv_histry);
     module = new Module();
     bid_list = new ArrayList<>();
     req_list = new ArrayList<>();
     loadingBar = new LoadingBar(getActivity());
   toastMsg = new ToastMsg(getActivity());
    session_management = new Session_management(getActivity());
    user_id = session_management.getUserDetails().get(KEY_ID);
   type = getArguments().getString("type");
   Log.e("type",type);
   if (ConnectivityReceiver.isConnected()) {
       if (type.equals("bid")) {
           ((MainActivity) getActivity()).setTitle("Bid History");

           getHistry(user_id);
       } else if (type.equals("funds")) {
           ((MainActivity) getActivity()).setTitle("Funds History");
           getFundHistry(user_id);
       }
   }   else
   {
       Intent intent = new Intent(getActivity(), NoInternetConnection.class);
       startActivity(intent);
   }
     return view ;
    }

    private void getHistry( String user_id) {
        loadingBar.show();
       bid_list.clear();
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id",user_id);
        CustomJsonRequest jsonObjReq = new CustomJsonRequest(Request.Method.POST,
                URL_GET_HISTORY, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("histry", response.toString());
                loadingBar.dismiss();
                try {
                    boolean res = response.getBoolean("responce");
                    if (res)
                    {
                        JSONArray data = response.getJSONArray("data");

                        for (int i =0 ;i <data.length();i++)
                        {
                            HistryModel model = new HistryModel();
                            JSONObject obj = data.getJSONObject(i);
                            model.setId(obj.getString("id"));
                            model.setMatka_id(obj.getString("matka_id"));
                            model.setGame_id(obj.getString("game_id"));
                            model.setUser_id(obj.getString("user_id"));
                            model.setPoints(obj.getString("points"));
                            model.setDigits(obj.getString("digits"));
                            model.setDate(obj.getString("date"));
                            model.setTime(obj.getString("time"));
                            model.setBet_type(obj.getString("bet_type"));
                            model.setStatus(obj.getString("status"));
                            model.setName(obj.getString("name"));
                            bid_list.add(model);
                        }
                        Log.d("bid_list", String.valueOf(bid_list.size()));

                            if (bid_list.size()>0) {
                                allHistoryAdapter = new AllHistoryAdapter(getActivity(), bid_list);
                                rv_hsitry.setLayoutManager(new LinearLayoutManager(getActivity()));
                                rv_hsitry.setAdapter(allHistoryAdapter);
                                allHistoryAdapter.notifyDataSetChanged();
                            }
                            else
                            {
                                toastMsg.toastInfo("No History Available");
                            }


                    }
                    else
                    {
                        toastMsg.toastIconError(""+response.get("Error").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                String msg=module.VolleyErrorMessage(error);

                if(!msg.equals(""))
                {
                    toastMsg.toastIconError(""+msg);
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,"histry");

    }

    private void getFundHistry( String user_id) {
        loadingBar.show();
        req_list.clear();
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id",user_id);
        CustomJsonRequest jsonObjReq = new CustomJsonRequest(Request.Method.POST,
                URL_FUND_HISTORY, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("fund_histry", response.toString());
                loadingBar.dismiss();
                try {
                    boolean res = response.getBoolean("responce");
                    if (res)
                    {
                        JSONArray data = response.getJSONArray("data");

                        for (int i =0 ;i <data.length();i++)
                        {
                         RequestModel model = new RequestModel();
                            JSONObject obj = data.getJSONObject(i);
                            model.setRequest_id(obj.getString("request_id"));
                            model.setRequest_points(obj.getString("request_points"));
                            model.setRequest_status(obj.getString("request_status"));
                            model.setUser_id(obj.getString("user_id"));
                            model.setType(obj.getString("type"));
                            model.setTime(obj.getString("time"));
                          req_list.add(model);
                        }
                        Log.d("req_list", String.valueOf(req_list.size()));

                        if (req_list.size()>0) {
                             fundsHistryAdapter= new FundsHistryAdapter(req_list,getActivity());
                            rv_hsitry.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv_hsitry.setAdapter(fundsHistryAdapter);
                           fundsHistryAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            toastMsg.toastInfo("No History Available");
                        }


                    }
                    else
                    {
                        toastMsg.toastIconError(""+response.get("Error").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                String msg=module.VolleyErrorMessage(error);

                if(!msg.equals(""))
                {
                    toastMsg.toastIconError(""+msg);
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,"histry");

    }

}
