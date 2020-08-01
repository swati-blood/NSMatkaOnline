package in.games.nidhimatka.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Adapter.GameRateAdapter;
import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Model.GameRateModel;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.ConnectivityReceiver;
import in.games.nidhimatka.Util.CustomJsonRequest;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.ToastMsg;
import in.games.nidhimatka.networkconnectivity.NoInternetConnection;

import static in.games.nidhimatka.Config.BaseUrls.URL_NOTICE;


public class GameRatesFragment extends Fragment {
    LoadingBar progressDialog;
    TextView game_Name ,game_Rate ,game_range ,txt_timer;
    ArrayList<GameRateModel> list;
    ArrayList<GameRateModel> slist;
    GameRateAdapter gameRateAdapter;
    RecyclerView jannat_recycler,starline_recycler;
    ToastMsg toastMsg;


    public GameRatesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   View view = inflater.inflate(R.layout.fragment_game_rates, container, false);
   initViews(view);
   return  view ;
    }

  public void initViews(View v)
  {
      progressDialog=new LoadingBar(getActivity());
      toastMsg=new ToastMsg(getActivity());
      //    progressDialog.setTitle("Please wait");
      jannat_recycler=v.findViewById(R.id.jannat_recycler);
      starline_recycler=v.findViewById(R.id.starline_recycler);
      ((MainActivity) getActivity()).setTitle("Game Rates");
      if (ConnectivityReceiver.isConnected()) {
          getNotice();
      } else
      {
          Intent intent = new Intent(getActivity(), NoInternetConnection.class);
          startActivity(intent);
      }
  }
    private void getNotice() {

        progressDialog.show();
        list=new ArrayList<>();
        slist=new ArrayList<>();
        String tag_json_obj = "game_rate";
        Map<String, String> params = new HashMap<String, String>();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_NOTICE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONArray array=response.getJSONArray("data");

                        for (int i=0; i<array.length();i++)
                        {
                            GameRateModel gameRateModel=new GameRateModel();
                            JSONObject object=array.getJSONObject(i);
                            gameRateModel.setId(object.getString("id"));
                            gameRateModel.setName(object.getString("name"));
                            gameRateModel.setRate_range(object.getString("rate_range"));
                            gameRateModel.setRate(object.getString("rate"));
                            String type=object.getString("type").toString();
                            gameRateModel.setType(type);
                            if(type.equals("0"))
                            {
                                list.add(gameRateModel);

                            }
                            else
                            {
                                slist.add(gameRateModel);
                            }


                        }
                        gameRateAdapter=new GameRateAdapter(list,getActivity());
                        jannat_recycler.setAdapter(gameRateAdapter);
                        jannat_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
                        gameRateAdapter.notifyDataSetChanged();

                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Something Went Wrong",Toast.LENGTH_LONG).show();
                    }

                    progressDialog.dismiss();
                }
                catch (Exception ex)
                {progressDialog.dismiss();
                    toastMsg.toastIconError(""+ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                toastMsg.toastIconError(""+error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,tag_json_obj);
    }

}
