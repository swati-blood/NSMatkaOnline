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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.games.nidhimatka.Adapter.TableAdaper;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;

public class OddEvenFragment extends Fragment implements View.OnClickListener {
    Common common;
    ListView listView;
    List<TableModel> list;
    TableAdaper tableAdaper;
    RadioGroup rd_group;
    public static Button btnAdd,btnSave,btnType,btnGameType;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    LoadingBar progressDialog;
    TextView btnDelete;
    CheckBox chkOdd,chkEven;
    private int stat=0;
    Dialog dialog;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type = "",game_date="";
    private TextView txt_date,txt_type ,txtOpen,txtClose,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    String bet_date="";
    private String bet_status="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_odd_even, container, false);
       intiView(view);
       return view;
    }
    void intiView(View v)
    {
        txt_date = v.findViewById(R.id.tv_date);
        txt_type = v.findViewById(R.id.tv_type);
        listView=(ListView)v.findViewById(R.id.list_table);
        btnAdd=(Button)v.findViewById(R.id.digit_add);
        btnSave=(Button)v.findViewById(R.id.digit_save);
        chkOdd=v.findViewById(R.id.oddDigits);
        chkEven=v.findViewById(R.id.evenDigits);
        etDgt=(AutoCompleteTextView)v.findViewById(R.id.etSingleDigit);
        etPoints=(EditText)v.findViewById(R.id.etPoints);
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
       txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);
        chkEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.size()>0)
                {
                    list.clear();

                }
                if(chkOdd.isChecked())
                {
                    chkOdd.setChecked(false);
                    chkEven.setChecked(true);
                }
                else
                {
                    chkEven.setChecked(true);
                }

            }
        });

        chkOdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.size()>0)
                {
                    list.clear();

                }
                if(chkEven.isChecked())
                {
                    chkOdd.setChecked(true);
                    chkEven.setChecked(false);
                }
                else
                {
                    chkOdd.setChecked(true);
                }

            }
        });




    }

    @Override
    public void onClick(View v) {
    if (v.getId()==R.id.digit_add)
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



     else if(TextUtils.isEmpty(etPoints.getText().toString()))
        {
            etPoints.setError("Please enter some point");
            etPoints.requestFocus();
            return;

        }



        else
        {
            int pints=Integer.parseInt(etPoints.getText().toString().trim());
            if(pints<10)
            {
                //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                etPoints.setError("Minimum Biding amount is 10");
                etPoints.requestFocus();
                return;


            }
            else if (pints>Integer.parseInt(w_amount))
            {
                common.errorMessageDialog("Insufficient Amount");
            }
            else
            {
                String th=null;
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


                String p=etPoints.getText().toString().trim();
                if(chkOdd.isChecked())
                {

                    String[] odd={"1","3","5","7","9"};

                    for(int i=0; i<=odd.length-1; i++)
                    {

                        common.addData(odd[i],p,bet,list,tableAdaper,listView,btnSave);
                        //      setBidsDialog(OddEvenActivity.this,,list);
                        //setOddData(odd[i],p,th);
                    }
                }
                else if(chkEven.isChecked())
                {

                    String[] even={"0","2","4","6","8"};


                    for(int i=0; i<=even.length-1; i++)
                    {

                        common.addData(even[i],p,bet,list,tableAdaper,listView,btnSave);


                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Please select any digit type",Toast.LENGTH_LONG).show();
                    return;
                }
            }

        }

    }
    else if (v.getId()== R.id.digit_save)
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
    public void clrControls()
    {

        etPoints.setText("");

        list.clear();

        txt_type.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_date.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_type.setText(getActivity().getResources().getString(R.string.select_type));
        txt_date.setText(getActivity().getResources().getString(R.string.select_date));
        btnSave.setText("Save");
    }
}
