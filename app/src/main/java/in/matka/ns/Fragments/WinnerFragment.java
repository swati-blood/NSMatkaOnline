package in.matka.ns.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.matka.ns.Activity.MainActivity;
import in.matka.ns.Activity.splash_activity;
import in.matka.ns.AppController;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.R;
import in.matka.ns.Util.ConnectivityReceiver;
import in.matka.ns.Util.CustomJsonRequest;
import in.matka.ns.Util.CustomVolleyJsonArrayRequest;
import in.matka.ns.Util.Module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WinnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WinnerFragment extends Fragment {
WebView webView;
ProgressBar progressBar;
Module module;
SwipeRefreshLayout swipe;
    public static String app_link ="";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WinnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WinnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WinnerFragment newInstance(String param1, String param2) {
        WinnerFragment fragment = new WinnerFragment ( );
        Bundle args = new Bundle ( );
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if (getArguments ( ) != null) {
            mParam1 = getArguments ( ).getString (ARG_PARAM1);
            mParam2 = getArguments ( ).getString (ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate (R.layout.fragment_winner, container, false);
        initview(view);
        return  view;
    }

    private void initview(View view) {
        webView=view.findViewById (R.id.webView);
        ((MainActivity)getActivity ()).setTitle ("Winners");
        progressBar = view.findViewById(R.id.progressBar);
        module=new Module ();
swipe=view.findViewById (R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWinner();
                swipe.setRefreshing(false);
            }
        });


     

    }


    private void getWinner() {

        String json_tag="json_splash_request";
        HashMap<String,String> params=new HashMap<String, String>();
        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.GET, BaseUrls.URL_INDEX, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("winner",""+response.toString());
                try
                {


                    JSONObject dataObj=response.getJSONObject(0);

                    app_link = dataObj.getString("app_link");
                    Log.e ("app", "onResponse: "+app_link);
                    webView.loadUrl (app_link);

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(getActivity (),"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                String msg=module.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    Toast.makeText (getContext (), ""+msg, Toast.LENGTH_SHORT).show ( );
                }
            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_tag);


    }



    private class myWebViewClient extends WebViewClient {



        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String Url) {
            view.loadUrl(Url);
            progressBar.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);


        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);

        }
    }

    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }
    }
}