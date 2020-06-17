package in.games.nidhimatka.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.games.nidhimatka.Activity.MainActivity;
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
    TextView txt_date,txt_type ,txtOpen,txtClose,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private EditText etDigits;
    private EditText etDgt,etPnt;
    String matName="";
    Dialog dialog ;
    private EditText etPoints;
    LoadingBar progressDialog;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type = "",game_date="";
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
        etDigits=(EditText)v.findViewById(R.id.etSingleDigit);
        common = new Common(getActivity());
        progressDialog = new LoadingBar(getActivity());
        matka_name = getArguments().getString("matka_name");
        game_name = getArguments().getString("game_name");
        matka_id = getArguments().getString("m_id");
        game_id = getArguments().getString("game_id");
        s_time = getArguments().getString("start_time");
        e_time = getArguments().getString("end_time");
        w_amount = txtWalet.getText().toString();
//        w_amount = "12";
        list=new ArrayList<>();
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);

        ((MainActivity) getActivity()).setTitle(matka_name+"-"+game_name);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.digit_add)
        {
            type = txt_type.getText().toString();
            game_date =txt_date.getText().toString();
            String bet = type;
            if (game_date.equals("Select Date"))
            {
                Toast.makeText(getActivity(),"Select Date",Toast.LENGTH_LONG).show();
            }
            else if (type.equals("Select Type"))
            {
                Toast.makeText(getActivity(),"Select game type",Toast.LENGTH_LONG).show();

            }

            else if (TextUtils.isEmpty(etDigits.getText().toString())) {
                    etDigits.setError("Please enter any digit");
                    etDigits.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPoints.getText().toString())) {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }  else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 10");
                        etPoints.requestFocus();
                        return;


                    }
                    else if (pints>Integer.parseInt(w_amount))
                    {
                        common.errorMessageDialog("Insufficient Amount");
                    }
                    else {
                        list.clear();
                        String d = etDigits.getText().toString();
                        String p = etPoints.getText().toString();
                        String th = type;
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


                        String inputData =etDigits.getText().toString().trim();
                        if (inputData.equals("false")) {
                            Toast.makeText(getActivity(), "Wrong input", Toast.LENGTH_LONG).show();
                        }

                            else {
                            if (game_name.equalsIgnoreCase("sp motor")) {
                                getDataSet(inputData, p, bet,URLs.URL_SpMotor);
                            }
                            else
                            {
                                getDataSet(inputData, p, bet,URLs.URL_DpMotor);
                            }
                        }
//
//                        etPoints.setText("");
//                        etDigits.setText("");
//                        etDigits.requestFocus();
                        //  btnType.setText("Select Type");
                    }
                }

        }
        else if (v.getId() == R.id.digit_save)
        {
            int amt=0;
            for(int j=0;j<list.size();j++)
            {
                amt=amt+Integer.parseInt(list.get(j).getPoints());
            }
            if (amt>Integer.parseInt(w_amount))
            {
                common.errorMessageDialog("Insufficient Amount");
                clrControls();
            }
            else {
                try {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    String cur_time = format.format(date);
                    String cur_date = sdf.format(date);
                    String g_d = game_date.substring(0, 10);
//                Toast.makeText(getActivity(),""+g_d,Toast.LENGTH_LONG).show();
                    Log.e("date ", String.valueOf(g_d) + "\n" + String.valueOf(cur_date));

               if (cur_date.equals(g_d)) {
                        Log.e("true", "today");
                        Date s_date = format.parse(s_time);
                        Date e_date = format.parse(e_time);
                        Date c_date = format.parse(cur_time);
                        long difference = c_date.getTime() - s_date.getTime();
                        long as = (difference / 1000) / 60;

                        long diff_close = c_date.getTime() - e_date.getTime();
                        long curr = (diff_close / 1000) / 60;
                        long current_time = c_date.getTime();

                        if (as < 0) {

                            common.setBidsDialog(Integer.parseInt(w_amount), list, matka_id, game_date, game_id, w_amount, matka_name, progressDialog, btnSave, s_time, e_time);
                        } else if (curr < 0) {
                            common.setBidsDialog(Integer.parseInt(w_amount), list, matka_id, game_date, game_id, w_amount, matka_name, progressDialog, btnSave, s_time, e_time);
                        } else {
                            clrControls();
                            common.errorMessageDialog("Betting is Closed Now");

                        }
                    } else {

                        common.setBidsDialog(Integer.parseInt(w_amount), list, matka_id, game_date.substring(0, 10), game_id, w_amount, matka_name, progressDialog, btnSave, s_time, e_time);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (v.getId()==R.id.tv_type)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            common.setBetTypeDialog(dialog,txtOpen,txtClose,matka_id,txt_type,progressDialog,ctt);
        }
        else if (v.getId()==R.id.tv_date)
        {

            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,progressDialog);
        }

    }
    private void getDataSet(final String inputData, final String d, final String th , final String url) {
        //  Toast.makeText(SpMotorActivity.this,"at"+inputData,Toast.LENGTH_LONG).show();
        progressDialog.show();

        String json_tag="json_sp_motor";
        Map<String, String> params = new HashMap<>();
        params.put("arr", inputData);
        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST,url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("motor",url+response.toString());
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

    public void clrControls()
    {

        etPoints.setText("");
        etDigits.setText("");
        list.clear();

        txt_type.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_date.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_type.setText(getActivity().getResources().getString(R.string.select_type));
        txt_date.setText(getActivity().getResources().getString(R.string.select_date));
        btnSave.setText("Save");
    }
}
