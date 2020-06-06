package in.games.nidhimatka.Fragments;

import android.app.Dialog;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.games.nidhimatka.Adapter.TableAdaper;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.Objects.sp_input_data;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;

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
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
CheckBox chkBox ;
    AutoCompleteTextView etDgt;
    private EditText etPnt;
    RelativeLayout relativeLayout ;
    private EditText etPoints;
    LoadingBar progressDialog;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type = "close";

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
        if (v.getId()== R.id.digit_add)
        {
//            String date_b=btnGameType.getText().toString().trim();
//            String b[]=date_b.split(" ");
//            String vt=b[3];
            String vt = type;
            if(vt.equals("Open")) {


                if(chkBox.isChecked()==true)
                {
                    String points=etPoints.getText().toString().trim();
                    if(TextUtils.isEmpty(points))
                    {
                        etPoints.setError("Enter Some Points");
                        etPoints.requestFocus();
                        return;
                    }
                    else
                    {
                        int pints = Integer.parseInt(etPoints.getText().toString().trim());
                        if (pints < 10) {
                            //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                            etPoints.setError("Minimum Biding amount is 10");
                            etPoints.requestFocus();
                            return;


                        } else {
                            for (int i = 0; i <= red_bracket.length - 1; i++) {
                                //setOddData(red_bracket[i], points, "close");
                                common.addData(red_bracket[i],points,"close",list,tableAdaper,list_table,btnSave);

                            }

                            etPoints.setText("");
                            etPoints.requestFocus();
                        }
                    }
                }
                else
                {
                    String digits=etDgt.getText().toString().trim();
                    String points=etPoints.getText().toString().trim();

                    if(TextUtils.isEmpty(digits))
                    {
                        etDgt.setError("Enter Some Digits");
                        etDgt.requestFocus();
                        return;
                    }
                    else if(TextUtils.isEmpty(points))
                    {
                        etPoints.setError("Enter Some Points");
                        etPoints.requestFocus();
                        return;
                    }
                    else if(!Arrays.asList(red_bracket).contains(digits))
                    {
                        common.errorMessageDialog("Invalid Jodi");
                        return;
                    }
                    else
                    {
                        int pints = Integer.parseInt(points);
                        if (pints < 10) {
                            //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                            etPoints.setError("Minimum Biding amount is 10");
                            etPoints.requestFocus();
                            return;


                        } else {

                            //setOddData(digits, points, "close");
                            common.addData(digits,points,"close",list,tableAdaper,list_table,btnSave);

                            etPoints.setText("");
                            etDgt.setText("");
                            etDgt.requestFocus();
                        }
                    }
                }
            }
            else
            {
                String message="Biding closed for this date";
                common.errorMessageDialog(message);
                return;
            }
        }
        else if (v.getId() == R.id.digit_save)
        {

            common.setBidsDialog(Integer.parseInt(w_amount),list,matka_id,type,game_id,w_amount,matka_name,progressDialog,btnSave,s_time,e_time);
        }


    }
}
