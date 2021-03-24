package in.matka.NS.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.matka.NS.Activity.MainActivity;
import in.matka.NS.Adapter.Notice_Adapter;
import in.matka.NS.AppController;
import in.matka.NS.Model.Notice_Model;
import in.matka.NS.R;
import in.matka.NS.Util.CustomJsonRequest;
import in.matka.NS.Util.LoadingBar;
import in.matka.NS.Util.ToastMsg;

import static in.matka.NS.Config.BaseUrls.URL_NOTICEBOARD;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeFragment extends Fragment {
    LoadingBar progressDialog;
    RecyclerView recyclerView;
    ArrayList<Notice_Model> notice_list;
    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setTitle("Game Instructions");
        progressDialog=new LoadingBar(getActivity());

        View view=inflater.inflate(R.layout.fragment_notice, container, false);
        notice_list = new ArrayList<> (  );
        recyclerView=view.findViewById (R.id.recyclerView);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager(view.getContext ()));

        //url
      NoticeBoard ();
//        StringRequest stringRequest=new StringRequest (Request.Method.POST, BaseUrls.Url_transaction_history, new Response.Listener<String> ( ) {
//            @Override
//            public void onResponse(String response) {
//
//            },new
//        })

        return  view;
    }

        public void NoticeBoard() {
            progressDialog.show();
        HashMap< String,String>params=new HashMap<> (  );
//        params.put ("title",title);
//        params.put ("detail",detail);
        CustomJsonRequest customVolleyJsonArrayRequest=new CustomJsonRequest (Request.Method.POST, URL_NOTICEBOARD, params, new Response.Listener<JSONObject> ( ) {

            @Override
          public void onResponse(JSONObject response) {
                Log.d("notice",response.toString());
try {
        boolean status = response.getBoolean ("responce");
        if (status)
        {
            JSONArray data_arr = response.getJSONArray ("data");
            for(int i=0; i<=data_arr.length()-1;i++) {
                JSONObject jsonObject = data_arr.getJSONObject(i);

                Notice_Model model=new Notice_Model ();
                model.setTitle (jsonObject.getString("title"));
                model.setDescription (jsonObject.getString ("description"));
                notice_list.add (model);
            }
            Notice_Adapter notice_adapter=new Notice_Adapter (NoticeFragment.this,notice_list);
            recyclerView.setAdapter (notice_adapter);
        }
    progressDialog.dismiss ();

} catch (Exception e) {
    progressDialog.dismiss();
    e.printStackTrace ( );
    new ToastMsg(getActivity()).toastIconError("Something went wrong");
}
            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                new ToastMsg(getActivity()).toastIconError("Error");
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,"");

//        StringRequest stringRequest=new StringRequest (Request.Method.POST, URL_NOTICEBOARD, params, new Response.Listener<> ( ) {
//            @Override
//            public void onResponse(Object response) {
//
//            }
//        } ,new Response.ErrorListener ( ) {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
    }

}
