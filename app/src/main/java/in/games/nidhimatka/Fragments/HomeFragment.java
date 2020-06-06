package in.games.nidhimatka.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.games.nidhimatka.Adapter.MatakListViewAdapter;
import in.games.nidhimatka.Adapter.MatkaAdapter;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Model.MatkaObject;
import in.games.nidhimatka.Model.MatkasObjects;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.Module;
import in.games.nidhimatka.Util.RecyclerTouchListener;

public class HomeFragment extends Fragment {

MatkaAdapter matkaAdapter ;
    private ArrayList<MatkasObjects> matkaList;
    private RecyclerView rv_matka;
    LoadingBar progressDialog;
    Common common;
    Module module;
    public static String mainName="";
    int flag =0 ;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_home2, container, false);
       initViews(view);

       rv_matka.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_matka, new RecyclerTouchListener.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {
               MatkasObjects objects = matkaList.get(position);
               Bundle bundle = new Bundle();

               bundle.putString("matka_name",objects.getName());
               bundle.putString("m_id",objects.getId());
               bundle.putString("start_number",objects.getStarting_num());
               bundle.putString("number",objects.getNumber());
               bundle.putString("end_number",objects.getEnd_num());
               bundle.putString("end_time",objects.getBid_end_time());
               bundle.putString("start_time",objects.getBid_start_time());
               Fragment fm  = new MainFragment();
               fm.setArguments(bundle);
               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                       .addToBackStack(null).commit();

           }

           @Override
           public void onLongItemClick(View view, int position) {

           }
       }));
       return  view;
    }

   private void initViews(View v)
   {
    matkaList = new ArrayList<>();

    rv_matka= v.findViewById(R.id.listView);
    progressDialog = new LoadingBar(getActivity());
    common = new Common(getActivity());
    module = new Module();
    getMatkaData();
    rv_matka.setLayoutManager(new LinearLayoutManager(getActivity()));
       matkaAdapter = new MatkaAdapter(getActivity(),matkaList);
       rv_matka.setAdapter(matkaAdapter);
   }

    public void getMatkaData()
    {
        progressDialog.show();

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URLs.URL_Matka, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //   matkaAdapter.notifyDataSetChanged();

                        Log.d("matka",String.valueOf(response));

                        matkaList.clear();

                        for(int i=0; i<response.length();i++)
                        {
                            try
                            {
                                JSONObject jsonObject=response.getJSONObject(i);

                                MatkasObjects matkasObjects=new MatkasObjects();
                                matkasObjects.setId(jsonObject.getString("id"));
                                matkasObjects.setName(jsonObject.getString("name"));
                                matkasObjects.setStart_time(jsonObject.getString("start_time"));
                                matkasObjects.setEnd_time(jsonObject.getString("end_time"));
                                matkasObjects.setStarting_num(jsonObject.getString("starting_num"));
                                matkasObjects.setNumber(jsonObject.getString("number"));
                                matkasObjects.setEnd_num(jsonObject.getString("end_num"));
                                matkasObjects.setBid_start_time(jsonObject.getString("bid_start_time"));
                                matkasObjects.setBid_end_time(jsonObject.getString("bid_end_time"));
                                matkasObjects.setCreated_at(jsonObject.getString("created_at"));
                                matkasObjects.setUpdated_at(jsonObject.getString("updated_at"));
                                matkasObjects.setSat_start_time(jsonObject.getString("sat_start_time"));
                                matkasObjects.setSat_end_time(jsonObject.getString("sat_end_time"));
                                matkasObjects.setStatus(jsonObject.getString("status"));
                                matkaList.add(matkasObjects);
                              matkaAdapter.notifyDataSetChanged();


                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                                return;
                            }
                        }

                        progressDialog.dismiss();


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        String msg=common.VolleyErrorMessage(error);
                        if(!msg.isEmpty())
                        {
                            common.showToast(""+msg);
                        }
                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);



    }

}
