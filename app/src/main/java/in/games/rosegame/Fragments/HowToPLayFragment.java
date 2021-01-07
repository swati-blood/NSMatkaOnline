package in.games.rosegame.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import in.games.rosegame.Activity.MainActivity;
import in.games.rosegame.AppController;
import in.games.rosegame.R;
import in.games.rosegame.Util.ConnectivityReceiver;
import in.games.rosegame.Util.CustomVolleyJsonArrayRequest;
import in.games.rosegame.Util.LoadingBar;
import in.games.rosegame.networkconnectivity.NoInternetConnection;

import static in.games.rosegame.Config.BaseUrls.URL_PLAY;

public class HowToPLayFragment extends Fragment implements View.OnClickListener{
    TextView bt_back,txtData,txtLink;
    RelativeLayout rel_click;
    ImageView img;
    LoadingBar progressDialog;
    public HowToPLayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setTitle("How to Play");
       View view =  inflater.inflate(R.layout.fragment_how_to_p_lay, container, false);
        progressDialog=new LoadingBar(getActivity());
        img=view.findViewById(R.id.img);
        txtData=(TextView)view.findViewById(R.id.w2);
        txtLink=(TextView)view.findViewById(R.id.link);
        rel_click=(RelativeLayout)view.findViewById(R.id.rel_click);

        rel_click.setOnClickListener(this);
        img.setOnClickListener(this);


if (ConnectivityReceiver.isConnected()) {
    getHowToPlayData();
} else
{
    Intent intent = new Intent(getActivity(), NoInternetConnection.class);
    startActivity(intent);
}


        return  view ;
    }
    private void getHowToPlayData() {
        progressDialog.show();
        String json_request_tag="json_how_request";
        HashMap<String,String> params=new HashMap<String,String>();

        CustomVolleyJsonArrayRequest customVolleyJsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.GET, URL_PLAY, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try
                {
                    JSONObject jsonObject=response.getJSONObject(0);
                    String data=jsonObject.getString("data");
                    String link=jsonObject.getString("link");
                    txtData.setText(data);
                    txtLink.setText(String.valueOf(link));
                    progressDialog.dismiss();
                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_request_tag);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rel_click || v.getId() == R.id.img){
            String h= txtLink.getText().toString().trim();
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(h));
            startActivity(intent);
        }

    }
}
