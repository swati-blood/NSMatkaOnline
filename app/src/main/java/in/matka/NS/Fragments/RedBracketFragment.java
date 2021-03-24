package in.matka.NS.Fragments;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import in.matka.NS.Activity.MainActivity;
import in.matka.NS.Adapter.TableAdaper;
import in.matka.NS.Common.Common;
import in.matka.NS.Model.TableModel;
import in.matka.NS.R;
import in.matka.NS.Util.LoadingBar;
import in.matka.NS.Util.ToastMsg;

/**
 * A simple {@link Fragment} subclass.
 */
public class RedBracketFragment extends Fragment implements View.OnClickListener {
    Common common;
    private final String[] red_bracket={"00","11","22","33","44","55","66","77","88","99","05","16","27","38","49","50",
            "61","72","83","94"};
    int stat =0 ;

    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    TextView txt_date,txt_type;
    ToastMsg toastMsg;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id ,txtOpen,txtClose;
CheckBox chkBox ;
Dialog dialog ;
    AutoCompleteTextView etDgt;
    private EditText etPnt;
    RelativeLayout relativeLayout ;
    private EditText etPoints;
    LoadingBar progressDialog;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type = "Close",game_date ="";

    public RedBracketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_red_bracket, container, false);
       intiView(view);
       return  view ;
    }
    void intiView(View v)
    {

        txt_date = v.findViewById(R.id.tv_date);
        txt_type = v.findViewById(R.id.tv_type);
        list_table=v.findViewById(R.id.list_table);
        btnAdd=(Button)v.findViewById(R.id.digit_add);
        btnSave=(Button)v.findViewById(R.id.digit_save);
        chkBox=v.findViewById(R.id.chk_bx);
        relativeLayout=v.findViewById(R.id.relativeLayout4);
        etDgt=(AutoCompleteTextView)v.findViewById(R.id.etSingleDigit);
        etPoints=(EditText)v.findViewById(R.id.etPoints);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView txtWalet=  toolbar.findViewById(R.id.txtWallet);
        etPoints=(EditText)v.findViewById(R.id.etPoints);
        common = new Common(getActivity());
       toastMsg = new ToastMsg(getActivity());
        progressDialog = new LoadingBar(getActivity());
        matka_name = getArguments().getString("matka_name");
        game_name = getArguments().getString("game_name");
        matka_id = getArguments().getString("m_id");
        game_id = getArguments().getString("game_id");
        s_time = getArguments().getString("start_time");
        e_time = getArguments().getString("end_time");
        w_amount =((MainActivity) getActivity()).getWallet();
        list=new ArrayList<>();
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);
        txt_date.setText(common.getCurrentDateDay());
        txt_date.setClickable(false);
        ((MainActivity) getActivity()).setTitle(matka_name+"-"+game_name);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,red_bracket);
        etDgt.setAdapter(adapter);
        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true)
                {
                    relativeLayout.setVisibility(View.GONE);
                    //    txtDigit.setVisibility(View.INVISIBLE);
//                    etDgt.setVisibility(View.INVISIBLE);
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    relativeLayout.setVisibility(View.VISIBLE);
                    list.clear();
                    adapter.notifyDataSetChanged();

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.digit_add) {

            type = txt_type.getText().toString();
            game_date = txt_date.getText().toString();
            String vt = type;
            if (game_date.equals("Select Date")) {
                toastMsg.toastIconError( "Select Date");
            }
            else if (type.equals("Select Type"))
            {
                toastMsg.toastIconError("Select game type");

            }


            else if (vt.equalsIgnoreCase("Open")) {


                if (chkBox.isChecked() == true) {
                    String points = etPoints.getText().toString().trim();
                    if (TextUtils.isEmpty(points)) {
                        etPoints.setError("Enter Some Points");
                        etPoints.requestFocus();
                        return;
                    } else {
                        int pints = Integer.parseInt(etPoints.getText().toString().trim());
                        if (pints < 10) {
                            //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                            etPoints.setError("Minimum Biding amount is 10");
                            etPoints.requestFocus();
                            return;


                        } else if (pints > Integer.parseInt(w_amount)) {
                            toastMsg.toastIconError("Insufficient Amount");
                        } else {
                            for (int i = 0; i <= red_bracket.length - 1; i++) {
                                //setOddData(red_bracket[i], points, "close");
                                common.addData(red_bracket[i], points, "Close", list, tableAdaper, list_table, btnSave);

                            }

                            etPoints.setText("");
                            etPoints.requestFocus();
                        }
                    }
                } else {
                    String digits = etDgt.getText().toString().trim();
                    String points = etPoints.getText().toString().trim();

                    if (TextUtils.isEmpty(digits)) {
                        etDgt.setError("Enter Some Digits");
                        etDgt.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(points)) {
                        etPoints.setError("Enter Some Points");
                        etPoints.requestFocus();
                        return;
                    } else if (!Arrays.asList(red_bracket).contains(digits)) {
                        toastMsg.toastIconError(("Invalid Jodi"));
                        return;
                    } else {
                        int pints = Integer.parseInt(points);
                        if (pints < 10) {
                            //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                            etPoints.setError("Minimum Biding amount is 10");
                            etPoints.requestFocus();
                            return;


                        }
                        else if (pints > Integer.parseInt(w_amount)) {
                            toastMsg.toastIconError("Insufficient Amount");
                        }
                        else {

                            //setOddData(digits, points, "close");
                            common.addData(digits, points, "Close", list, tableAdaper, list_table, btnSave);

//                            etPoints.setText("");
//                            etDgt.setText("");
//                            etDgt.requestFocus();
                        }
                    }
                }

            } else {
                String message = "Biding closed for this date";
                toastMsg.toastIconError(message);
                return;
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
                toastMsg.toastIconError(("Insufficient Amount"));
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
                            toastMsg.toastIconError(("Biddiing is Closed Now"));

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
            common.setBetTypeDialog(dialog,txtOpen,txtClose,txt_type,txt_date.getText().toString(),s_time,e_time);
        }
        else if (v.getId()==R.id.tv_date)
        {

            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,progressDialog);
        }

    }
    public void clrControls()
    {

        etPoints.setText("");
        etDgt.setText("");
        list.clear();

        txt_type.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_date.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_type.setText(getActivity().getResources().getString(R.string.select_type));
        txt_date.setText(getActivity().getResources().getString(R.string.select_date));
        btnSave.setText("Save");
    }
}
