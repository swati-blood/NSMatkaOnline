package in.matka.ns.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.matka.ns.Common.Common;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.matka.ns.Adapter.MatkaCategoryAdapter;
import in.matka.ns.AppController;
import in.matka.ns.Model.MatkasObjects;
import in.matka.ns.R;
import in.matka.ns.Util.ConnectivityReceiver;
import in.matka.ns.Util.CustomVolleyJsonArrayRequest;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.Module;
import in.matka.ns.Util.RecyclerTouchListener;
import in.matka.ns.networkconnectivity.NoInternetConnection;

import static in.matka.ns.Config.BaseUrls.URL_Matka;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatkaListFragment extends Fragment {
    Common common;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private MatkaCategoryAdapter matkaCategoryAdapter;
    private ArrayList<MatkasObjects> matkaList;
    Module module;
    LoadingBar progressDialog;
    public MatkaListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view =inflater.inflate(R.layout.fragment_matka_list, container, false);
    initViews(view);

     return  view ;
    }
    public void initViews(View v)
    {
        common=new Common(getActivity());
              module=new Module();
        progressDialog=new LoadingBar(getActivity());
        matkaList=new ArrayList();
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        matkaCategoryAdapter=new MatkaCategoryAdapter(getActivity(),matkaList);
        //matakListViewAdapter=new MatakListViewAdapter(this,matkaList);
        recyclerView.setAdapter(matkaCategoryAdapter);


if(ConnectivityReceiver.isConnected())
{
        getMatkaData();}
else
{
    Intent intent = new Intent(getActivity(), NoInternetConnection.class);
    startActivity(intent);
}
       recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MatkasObjects objects = matkaList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("type","bid");
                bundle.putString("matka_name",objects.getName());
                bundle.putString("matka_id",objects.getId());
                bundle.putString("start_number",objects.getStarting_num());
                bundle.putString("number",objects.getNumber());
                bundle.putString("end_number",objects.getEnd_num());
                bundle.putString("end_time",objects.getBid_end_time());
                bundle.putString("start_time",objects.getBid_start_time());
                Fragment fm  = new AllHistryListFragment();
                fm.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));



    }
    public void getMatkaData()
    {
        progressDialog.show();

        String json_tag="json_get_matkas";
        HashMap<String,String> map=new HashMap<>();

        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.POST, URL_Matka, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

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
                        matkaCategoryAdapter.notifyDataSetChanged();


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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                Toast.makeText(getActivity(),"Error :"+msg,Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_tag);


    }

}
