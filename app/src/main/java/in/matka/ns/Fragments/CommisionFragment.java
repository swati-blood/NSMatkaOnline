package in.matka.ns.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.matka.ns.Activity.MainActivity;
import in.matka.ns.Adapter.EarnCommisionAdapter;
import in.matka.ns.Common.Common;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.Model.EarnCommisionModel;
import in.matka.ns.R;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.Session_management;

import static in.matka.ns.Config.Constants.KEY_ID;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 03,April,2021
 */
public class CommisionFragment extends Fragment {
    private final String TAG=CommisionFragment.class.getSimpleName();
    LoadingBar loadingBar;
    Session_management session_management;
    Common common;
    ArrayList<EarnCommisionModel> list;
    RecyclerView rv_histry;
    ImageView no_history;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all_history,container,false);
        initView(view);
        return view;
    }

    private void initView(View v) {
     loadingBar=new LoadingBar(getActivity());
     session_management =new Session_management(getActivity());
     common=new Common(getActivity());
     list=new ArrayList<>();
     rv_histry=v.findViewById(R.id.rv_histry);
     no_history=v.findViewById(R.id.no_history);
     rv_histry.setNestedScrollingEnabled(false);
     rv_histry.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((MainActivity) getActivity()).setTitle("Earn History");
        getCommissionHistory();
    }

    private void getCommissionHistory(){
        loadingBar.show();
        list.clear();
        HashMap<String,String> params=new HashMap<>();
        params.put("user_id",session_management.getUserDetails().get(KEY_ID));
         common.postRequest(BaseUrls.URL_COMMISSION_HISTORY, params, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
             loadingBar.dismiss();
             try {
                 JSONObject object=new JSONObject(response);
                 if(object.getBoolean("responce")){
                      JSONArray array=object.getJSONArray("data");
                      for(int i=0; i<array.length();i++){
                          JSONObject obj = array.getJSONObject(i);
                          EarnCommisionModel model=new EarnCommisionModel();
                          model.setId(common.checkNull(obj.getString("id")));
                          model.setName(common.checkNull(obj.getString("name")));
                          model.setCreated_at(common.checkNull(obj.getString("created_at")));
                          model.setReferred_code(common.checkNull(obj.getString("referred_code")));
                          model.setRefer_by(common.checkNull(obj.getString("refer_by")));
                          model.setRefer_to(common.checkNull(obj.getString("refer_to")));
                          model.setRefer_amount(common.checkNull(obj.getString("refer_amount")));
                          model.setStatus(common.checkNull(obj.getString("status")));
                          list.add(model);
                      }

                      if(list.size()>0){
                          no_history.setVisibility(View.GONE);
                          rv_histry.setVisibility(View.VISIBLE);
                          EarnCommisionAdapter adapter=new EarnCommisionAdapter(list,getActivity());
                          rv_histry.setAdapter(adapter);
                          adapter.notifyDataSetChanged();
                      }else{
                          no_history.setVisibility(View.VISIBLE);
                          rv_histry.setVisibility(View.GONE);
                      }
                 }else{
                     common.showToast(""+object.getString("error"));
                     no_history.setVisibility(View.VISIBLE);
                     rv_histry.setVisibility(View.GONE);
                 }

             }catch (Exception  ex){
                 ex.printStackTrace();
             }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 loadingBar.dismiss();
                 common.showVolleyError(error);
             }
         });
    }
}
