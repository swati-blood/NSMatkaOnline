package in.matka.ns.Fragments;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.matka.ns.Activity.MainActivity;
import in.matka.ns.Adapter.TableAdaper;
import in.matka.ns.Common.Common;
import in.matka.ns.Model.TableModel;
import in.matka.ns.R;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.ToastMsg;

/**
 * A simple {@link Fragment} subclass.
 */
public class HalfSangamFragment extends Fragment implements View.OnClickListener {
    Common common;
    private TextView txtDigit,txtPoint,txtType,btnDelete;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,tv_timer;
    int val_p=0;
    ToastMsg toastMsg ;
    private int stat=0;
    private final String[] singlePaana={"137","128","146","236","245","290","380","470","489","560","678","579",
            "119","155","227","335","344","399","588","669","777","100","129","138","147","156","237","246",
            "345","390","480","570","589","679","110","228","255","336","499","660","778","200","444",
            "120","139","148","157","238","247","256","346","490","580","670","689","779","788","300","111",
            "130","149","158","167","239","248","257","347","356","590","680","789","699","770","400","888",
            "140","159","168","230","249","258","267","348","357","456","690","780","113","122","177","339",
            "366","447","799","889","500","555",
            "123","150","169","178","240","259","268","349","358","367","457","790","114","277","330","448",
            "466","556","880","899","600","222",
            "124","160","179","250","269","278","340","359","368","458","467","890","115","133","188","223","377",
            "449","557","566","700","999",
            "125","134","170","189","260","279","350","369","378","459","468","567","116","224","233","288","440",
            "477","558","666", "126","135","180","235","270","289","360","379","450","469","478",
            "568","117","144","199","225","388","559","577","667","900","333",
            "127","136","145","190","234","280","370","389","460","479","569","578","118","226","244","299","334","488",
            "668","677","000","550",
            "688",
            "166","229","337","355","445","599","112","220","266",
            "338","446","455",
            "800","990"};
    RelativeLayout rlLayout_open_digit,rlLayout_open_panna,rlLayout_close_digit,rlLayout_close_panna;

    AutoCompleteTextView etOpenPanna,etClosePanna;
    private Button btnAdd,btnSave,btnGameType;
    TextView bt_back;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
     private EditText etPoints,etOpenDigit,etCloseDigit;
    LoadingBar progressDialog;
    private ListView lstView;
     private Dialog dialog;

    TextView txt_date ,btnChange ;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type = "open" ,game_date="";
    private static int flag=0;

    public HalfSangamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_half_sangam, container, false);
        intiView(view);
        return  view;
    }
    void intiView(View v)
    {
        btnChange=v.findViewById(R.id.btnChange);
        rlLayout_open_digit=(RelativeLayout)v.findViewById(R.id.relativeLayout4);
        rlLayout_open_panna=(RelativeLayout)v.findViewById(R.id.relative_c_Layout4);
        rlLayout_close_panna=(RelativeLayout)v.findViewById(R.id.relativeLayout11);
        rlLayout_close_digit=(RelativeLayout)v.findViewById(R.id.relative_c_Layout11);
        txt_date = v.findViewById(R.id.tv_date);

        list_table=v.findViewById(R.id.list_table);
        btnAdd=(Button)v.findViewById(R.id.digit_add);
        btnSave=(Button)v.findViewById(R.id.digit_save);
        etPoints=(EditText)v.findViewById(R.id.etPoints);
        etCloseDigit=(EditText)v.findViewById(R.id.et_c_closedigit);
        etOpenDigit=(EditText)v.findViewById(R.id.etSingleDigit);
        etOpenPanna=(AutoCompleteTextView)v.findViewById(R.id.et_c_openpanna);
        etClosePanna=(AutoCompleteTextView)v.findViewById(R.id.et_ClosePanna);
        btnAdd=(Button)v.findViewById(R.id.digit_add);
        progressDialog=new LoadingBar(getActivity());
       list_table=v.findViewById(R.id.list_table);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView txtWalet=  toolbar.findViewById(R.id.txtWallet);

        common = new Common(getActivity());
       toastMsg = new ToastMsg(getActivity());
        progressDialog = new LoadingBar(getActivity());
        matka_name = getArguments().getString("matka_name");
        game_name = getArguments().getString("game_name");
        matka_id = getArguments().getString("m_id");
        game_id = getArguments().getString("game_id");
        s_time = getArguments().getString("start_time");
        e_time = getArguments().getString("end_time");
        w_amount = ((MainActivity) getActivity()).getWallet();
        list=new ArrayList<>();
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_date.setText(common.getCurrentDateDay());
        txt_date.setClickable(false);
        ((MainActivity) getActivity()).setTitle(matka_name+"-"+game_name);

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,singlePaana);
        etOpenPanna.setAdapter(adapter);
        etClosePanna.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.digit_save)
        {
            int er = list.size();
            if (er <= 0) {
                String message = "Please Add Some Bids";
                toastMsg.toastIconError(message);
                return;
            } else {

                try {
                    int amt = 0;
                    ArrayList list_digits = new ArrayList();
                    ArrayList list_type = new ArrayList();
                    ArrayList list_points = new ArrayList();
                    int rows = list.size();


                    for (int i = 0; i < rows; i++) {


                        TableModel tableModel = list.get(i);
                        String asd = tableModel.getDigits();
                        String d_all[] = asd.split("-");
                        String d0 = d_all[0].toString();
                        String d1 = d_all[1].toString();

                        String asd1 = tableModel.getPoints().toString();
                        String asd2 = tableModel.getType().toString();
                        int b = 1;
                        if (asd2.equals("Half sangam")) {
                            b = 0;
                        } else {
                            b = 0;
                        }


                        amt = amt + Integer.parseInt(asd1);


                        char quotes = '"';
                        list_digits.add(quotes + d0 + quotes);
                        list_points.add(asd1);
                        list_type.add(quotes + d1 + quotes);


                        // String sd=list_digits.add();
                    }


                    String id = common.getUserId().toString().trim();

                    String date = game_date.substring(0,10);
//                    String dt=btnGameType.getText().toString().trim();
//                    String d[]=dt.split(" ");
//
//                    String c=d[0].toString();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("points", list_points);
                    jsonObject.put("digits", list_digits);
                    jsonObject.put("bettype", list_type);
                    jsonObject.put("user_id", id);
                    jsonObject.put("matka_id", matka_id);
                    jsonObject.put("date", date);
                    jsonObject.put("game_id", game_id);

                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(jsonObject);

                    int wallet_amount = Integer.parseInt(w_amount);
                    if (wallet_amount < amt) {
                        toastMsg.toastIconError("Insufficient Amount");
//                            setBidsDialog(HalfSangamActivity.this,Integer.parseInt(w),list);
//                           return;
                    } else {
                        common.setBidsDialog(Integer.parseInt(w_amount), list, matka_id, date, game_id, w_amount, matka_name, progressDialog, btnSave, s_time, e_time);
//                            btnSave.setEnabled(false);
                        //    Toast.makeText(HalfSangamActivity.this,""+jsonArray,Toast.LENGTH_LONG).show();
                    }} catch (Exception err) {
                    toastMsg.toastIconError("Err" + err.getMessage());
                        }

                    }
                }
        else if (v.getId() == R.id.digit_add)
        {
//            String date_b = btnGameType.getText().toString().trim();
//            String b[] = date_b.split(" ");
//            String vt = b[3];
            String vt = "Half Sangam";

            game_date = txt_date.getText().toString();
            if (game_date.equals("Select Date"))
            {
                toastMsg.toastIconError("Select Date");
            }
//            else if (type.equals("Select Type"))
//            {
//                Toast.makeText(getActivity(),"Select game type",Toast.LENGTH_LONG).show();
//
//            }


              else if (rlLayout_open_digit.getVisibility() == View.VISIBLE) {
                    String open_digit = etOpenDigit.getText().toString().trim();
                    String close_panna = etClosePanna.getText().toString().trim();
                    String points = etPoints.getText().toString().trim();

                    if (TextUtils.isEmpty(open_digit)) {
                        etOpenDigit.setError("Enter digits");
                        etOpenDigit.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(close_panna)) {
                        etClosePanna.setError("Enter pana");
                        etClosePanna.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(points)) {
                        etPoints.setError("Enter points");
                        etPoints.requestFocus();
                        return;

                    } else if(!Arrays.asList(singlePaana).contains(close_panna))
                    {
                        toastMsg.toastIconError("This is invalid pana");
                        etClosePanna.setText("");
                        etClosePanna.requestFocus();
                        return;
                    }
                    else {
                        int pints = Integer.parseInt(etPoints.getText().toString().trim());
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
                            //setTableRowforOPenDigit(open_digit, close_panna, points);
                            common.addData(open_digit+"-"+close_panna,points,vt,list,tableAdaper,list_table,btnSave);

                            etOpenDigit.requestFocus();
                            clearCtrls();

                        }
                    }

                } else if (rlLayout_close_digit.getVisibility() == View.VISIBLE) {
                    String close_digit = etCloseDigit.getText().toString().trim();
                    String open_panna = etOpenPanna.getText().toString().trim();
                    String points = etPoints.getText().toString().trim();

                    if (TextUtils.isEmpty(close_digit)) {
                        etCloseDigit.setError("Enter digits");
                        etCloseDigit.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(open_panna)) {
                        etOpenPanna.setError("Enter pana");
                        etOpenPanna.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(points)) {
                        etPoints.setError("Enter Point");
                        etPoints.requestFocus();
                        return;
                    } else if(!Arrays.asList(singlePaana).contains(open_panna))
                    {
                        toastMsg.toastIconError("This is invalid pana");
                        etOpenPanna.setText("");
                        etOpenPanna.requestFocus();
                        return;
                    }
                    else {
                        int pints = Integer.parseInt(etPoints.getText().toString().trim());
                        if (pints < 10) {
                            //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                            etPoints.setError("Minimum Biding amount is 10");
                            etPoints.requestFocus();
                            return;


                        }
                        else if (pints > Integer.parseInt(w_amount)) {
                            toastMsg.toastIconError("Insufficient Amount");
                        }else {
                            //   setTableRowforCloseDigit(close_digit, open_panna, points);
//                                module.addData(HalfSangamActivity.this,close_digit+"-"+open_panna,points,"Half Sangam",list,tableAdaper,list_table,btnSave);
                            common.addData(open_panna+"-"+close_digit,points,vt,list,tableAdaper,list_table,btnSave);

                            etCloseDigit.requestFocus();
                            clearCtrls();

                        }
                    }


                }

                //   Toast.makeText(HalfSangamActivity.this,""+status,Toast.LENGTH_LONG).show();

        }


        else if (v.getId() == R.id.btnChange)
        {
            if(rlLayout_open_digit.getVisibility()==View.VISIBLE)
            {
                rlLayout_open_digit.setVisibility(View.INVISIBLE);
                rlLayout_close_panna.setVisibility(View.INVISIBLE);
                rlLayout_close_digit.setVisibility(View.VISIBLE);
                rlLayout_open_panna.setVisibility(View.VISIBLE);
//                    list.clear();
            }
            else if(rlLayout_close_digit.getVisibility()==View.VISIBLE)
            {

                rlLayout_open_digit.setVisibility(View.VISIBLE);
                rlLayout_close_panna.setVisibility(View.VISIBLE);
                rlLayout_close_digit.setVisibility(View.INVISIBLE);
                rlLayout_open_panna.setVisibility(View.INVISIBLE);
//                    list.clear();
            }
        }
//        else if (v.getId()==R.id.tv_type)
//        {
//            Date date=new Date();
//            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
//            String ctt=dateFormat.format(date);
//            common.setBetTypeDialog(dialog,txtOpen,txtClose,matka_id,txt_type,progressDialog,ctt);
//        }
        else if (v.getId()==R.id.tv_date)
        {

            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,progressDialog);
        }

    }
    public void clearCtrls()
    {
        etOpenDigit.setText("");
        etCloseDigit.setText("");
        etClosePanna.setText("");
        etOpenPanna.setText("");
        etPoints.setText("");

    }
}
