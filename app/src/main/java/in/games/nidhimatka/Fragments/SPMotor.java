package in.games.nidhimatka.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.games.nidhimatka.Adapter.TableAdaper;
import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.Objects.sp_input_data;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.CustomJsonRequest;
import in.games.nidhimatka.Util.LoadingBar;

public class SPMotor extends Fragment implements View.OnClickListener {
    Common common;
    private int stat=0;
    RadioButton rd_open,rd_close;
    RadioGroup rd_group;
    private final String[] triplePanna={"012","712","435","123","890","567","234","912","678","345"};
    private Button btnAdd,btnSave,btnType,btnGameType;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    TextView txt_date,txt_type;
    private EditText etDigits;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    LoadingBar progressDialog;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type = "close";
    public SPMotor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_s_p_motor, container, false);
       intiView(view);
       return view ;
    }
    void intiView(View v)
    {
        txt_date = v.findViewById(R.id.tv_date);
        txt_type = v.findViewById(R.id.tv_type);
        list_table=v.findViewById(R.id.list_table);
        btnAdd=(Button)v.findViewById(R.id.digit_add);
        btnSave=(Button)v.findViewById(R.id.digit_save);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView txtWalet=  toolbar.findViewById(R.id.txtWallet);
        etPoints=(EditText)v.findViewById(R.id.etPoints);
        common = new Common(getActivity());
        progressDialog = new LoadingBar(getActivity());
        matka_name = getArguments().getString("matka_name");
        game_name = getArguments().getString("game_name");
        matka_id = getArguments().getString("m_id");
        game_id = getArguments().getString("game_id");
        s_time = getArguments().getString("start_time");
        e_time = getArguments().getString("end_time");
        w_amount = txtWalet.getText().toString();
        list=new ArrayList<>();
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.digit_add)
        {

            String bet = type;

                if (TextUtils.isEmpty(etDigits.getText().toString())) {
                    etDigits.setError("Please enter any digit");
                    etDigits.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPoints.getText().toString())) {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }  else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 1) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 1");
                        etPoints.requestFocus();
                        return;


                    } else {
                        list.clear();
                        String d = etDigits.getText().toString();
                        String p = etPoints.getText().toString();
                        String th = null;
                        if(stat==1)
                        {
                            th="open";
                        }
                        else if(stat==2)
                        {
                            if(bet.equals("open"))
                            {
                                th="open";
                            }
                            else  if(bet.equals("close"))
                            {
                                th="close";
                            }

                        }



                        //Toast.makeText(SpMotorActivity.this,"DDat"+d[0],Toast.LENGTH_LONG).show();

                        //String asd=spInput(d);


                        //  String inputData = String.valueOf(assd);
                        String inputData =etDigits.getText().toString().trim();
                        if (inputData.equals("false")) {
                            Toast.makeText(getActivity(), "Wrong input", Toast.LENGTH_LONG).show();
                        } else {
                            if (game_name.equalsIgnoreCase("sp motor")) {
                                getDataSet(inputData, p, th,URLs.URL_SpMotor);
                            }
                            else
                            {
                                getDataSet(inputData, p, th,URLs.URL_DpMotor);
                            }
                        }

//                    Toast.makeText(SpMotorActivity.this,"DDat"+asd,Toast.LENGTH_LONG).show();


                        etPoints.setText("");
                        etDigits.setText("");
                        etDigits.requestFocus();
                        //  btnType.setText("Select Type");
                    }
                }

        }
        else if (v.getId() == R.id.digit_save)
        {

            common.setBidsDialog(Integer.parseInt(w_amount),list,matka_id,type,game_id,w_amount,matka_name,progressDialog,btnSave,s_time,e_time);
        }

    }
    private void getDataSet(final String inputData, final String d, final String th ,String url) {
        //  Toast.makeText(SpMotorActivity.this,"at"+inputData,Toast.LENGTH_LONG).show();
        progressDialog.show();
//        String url = "";
//        if (game_name.equalsIgnoreCase("SP Motor"))
//        {
//            url =URLs.URL_SpMotor;
//        }
//        else if (game_name.equalsIgnoreCase("DP Motor"))
//        {
//            url =URLs.URL_DpMotor;
//        }
        String json_tag="json_sp_motor";
        Map<String, String> params = new HashMap<>();
        params.put("arr", inputData);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST,url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //    Toast.makeText(SpMotorActivity.this, "Data" + response, Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = response;

                    String status = jsonObject.getString("status");
                    JSONArray as = jsonObject.getJSONArray("data");

                    if (status.equals("success")) {
                        for (int i = 0; i <= as.length() - 1; i++) {
                            String p = as.getString(i);
                            //setTableData(p,d,th);
                            common.addData(p,d,th,list,tableAdaper,list_table,btnSave);

                            //arrayList.add(new SingleDigitObjects(p,d,th));
                        }
///                                Toast.makeText(SpMotorActivity.this, "Something wrong"+as, Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG).show();

                    }


//                            JSONObject object=new JSONObject(response);
//                            String status=object.getString("status");
//                            List asd=Arrays.asList(object.getString("answer"));

                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Error :" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :" + error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);

    }

}
