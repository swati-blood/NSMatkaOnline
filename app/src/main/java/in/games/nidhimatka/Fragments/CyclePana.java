package in.games.nidhimatka.Fragments;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Adapter.TableAdaper;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.Objects.sp_input_data;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;


public class CyclePana extends Fragment implements View.OnClickListener {
    Common common;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    private int stat=0;
    private Button btnAdd,btnSave,btnType,btnGameType;
    String p,g;
    private TextView txtDigit,txtPoint,txtType;
    TextView bt_back;
    TextView btnDelete;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id ,txt_timer,tv_timer ;
    private Dialog dialog;
    private TextView txtOpen,txtClose;
    private final String[] d10={"100","110","120","130","140","150","160","170","180","190"};
    private final String[] d11={"110","111","112","113","114","115","116","117","118","119"};
    private final String[] d12={"112","120","122","123","124","125","126","127","128","129"};
    private final String[] d13={"113","123","130","133","134","135","136","137","138","139"};
    private final String[] d14={"114","124","134","140","144","145","146","147","148","149"};
    private final String[] d15={"115","125","135","145","150","155","156","157","158","159"};
    private final String[] d16={"116","126","136","146","156","160","166","167","168","169"};
    private final String[] d17={"117","127","137","147","157","167","170","177","178","179"};
    private final String[] d18={"118","128","138","148","158","168","178","180","188","189"};
    private final String[] d19={"119","129","139","149","159","169","179","189","190","199"};
    private final String[] d20={"120","200","220","230","240","250","260","270","280","290"};
    private final String[] d22={"122","220","223","224","225","226","227","228","229","222"};
    private final String[] d23={"123","230","233","234","235","236","237","238","239","223"};
    private final String[] d24={"124","240","244","245","246","247","248","249","224","234"};
    private final String[] d25={"125","250","255","256","257","258","259","225","235","245"};
    private final String[] d26={"126","260","266","267","268","269","226","236","346","256"};
    private final String[] d27={"127","270","277","278","279","227","237","247","257","267"};
    private final String[] d28={"128","280","288","289","228","238","248","258","268","278"};
    private final String[] d29={"129","290","299","229","239","249","259","269","279","289"};
    private final String[] d30={"130","230","300","330","340","350","360","370","380","390"};
    private final String[] d34={"134","234","334","340","344","345","346","347","348","349"};
    private final String[] d35={"135","350","355","335","345","235","356","357","358","359"};
    private final String[] d36={"136","360","366","336","346","356","367","368","369","236"};
    private final String[] d37={"137","370","377","337","347","357","367","378","379","237"};
    private final String[] d38={"138","380","388","238","338","348","358","368","378","389"};
    private final String[] d39={"139","390","399","349","359","369","379","389","239","339"};
    private final String[] d40={"140","240","340","400","440","450","460","470","480","490"};
    private final String[] d44={"144","244","344","440","449","445","446","447","448","444"};
    private final String[] d45={"145","245","345","450","456","457","458","459","445","455"};
    private final String[] d46={"146","460","446","467","468","469","246","346","456","466"};
    private final String[] d47={"147","470","447","478","479","247","347","457","467","477"};
    private final String[] d48={"148","480","489","248","348","448","488","458","468","478"};
    private final String[] d49={"149","490","499","449","459","469","479","489","249","349"};
    private final String[] d50={"500","550","150","250","350","450","560","570","580","590"};
    private final String[] d55={"155","556","557","558","559","255","355","455","555","550"};
    private final String[] d56={"156","556","567","568","569","356","256","456","560","566"};
    private final String[] d57={"157","257","357","457","557","578","579","570","567","577"};
    private final String[] d58={"158","558","568","578","588","589","580","258","358","458"};
    private final String[] d59={"159","259","359","459","559","569","579","589","590","599"};
    private final String[] d60={"600","160","260","360","460","560","660","670","680","690"};
    private final String[] d66={"660","667","668","669","666","166","266","366","466","566"};
    private final String[] d67={"670","167","267","367","467","567","667","678","679","677"};
    private final String[] d68={"680","688","668","678","168","268","368","468","568","689"};
    private final String[] d69={"690","169","269","369","469","569","669","679","689","699"};
    private final String[] d70={"700","170","270","370","470","570","670","770","780","790"};
    private final String[] d77={"770","177","277","377","477","577","677","778","779","777"};
    private final String[] d78={"178","278","378","478","578","678","778","788","789","780"};
    private final String[] d79={"179","279","379","479","579","679","779","789","799","790"};
    private final String[] d80={"180","280","380","480","580","680","780","880","800","890"};
    private final String[] d88={"188","288","388","488","588","688","788","889","888","880"};
    private final String[] d89={"189","289","389","489","589","689","789","889","890","899"};
    private final String[] d90={"900","190","290","390","490","590","690","790","890","990"};
    private final String[] d99={"199","299","399","499","599","699","799","899","990","999"};

    TextView txtMatka;
    private EditText etDgt,etPnt;
    private EditText etPoints;
    LoadingBar progressDialog;
    AutoCompleteTextView editText;
    TextView txt_date ,txt_type ;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type="" ,game_date ="";

    public CyclePana() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_cycle_pana, container, false);
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
        etPoints=(EditText)v.findViewById(R.id.etPoints);
        btnAdd=(Button)v.findViewById(R.id.digit_add);
        progressDialog=new LoadingBar(getActivity());
        list_table=v.findViewById(R.id.list_table);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView txtWalet=  toolbar.findViewById(R.id.txtWallet);
        editText=(AutoCompleteTextView)v.findViewById(R.id.etSingleDigit);
        txtDigit=(TextView)v.findViewById(R.id.dgt);
        txtPoint=(TextView)v.findViewById(R.id.pnt);
        txtType=(TextView)v.findViewById(R.id.type);
        btnDelete=(TextView)v. findViewById(R.id.del);
        common = new Common(getActivity());
        progressDialog = new LoadingBar(getActivity());
        matka_name = getArguments().getString("matka_name");
        game_name = getArguments().getString("game_name");
        matka_id = getArguments().getString("m_id");
        game_id = getArguments().getString("game_id");
        s_time = getArguments().getString("start_time");
        e_time = getArguments().getString("end_time");
        w_amount = ((MainActivity) getActivity()).getWallet();
        ((MainActivity) getActivity()).setTitle(matka_name+"-"+game_name);
        list=new ArrayList<>();
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
       txt_type.setOnClickListener(this);
       txt_date.setOnClickListener(this);


        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, sp_input_data.cycle_pana_array);
        editText.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.digit_add) {
            type = txt_type.getText().toString();
            game_date = txt_date.getText().toString();

            list.clear();
            String bet = type ;
            if (game_date.equals("Select Date"))
            {
                Toast.makeText(getActivity(),"Select Date",Toast.LENGTH_LONG).show();
            }
           else if (bet.equals("Select Type"))
           {
                Toast.makeText(getActivity(),"Select game type",Toast.LENGTH_LONG).show();

            } else if (TextUtils.isEmpty(editText.getText().toString())) {
                editText.setError("Please enter any digit");
                editText.requestFocus();
                return;
            } else if (TextUtils.isEmpty(etPoints.getText().toString())) {
                etPoints.setError("Please enter some point");
                etPoints.requestFocus();
                return;

            }

            else {
                int pints = Integer.parseInt(etPoints.getText().toString().trim());
                if (pints < 10) {
                   etPoints.setError("Minimum Biding amount is 10");
                    etPoints.requestFocus();
                    return;
                } else {
                    String th = type;
                    if (stat == 1) {
                        th = "open";
//                        txt_timer.setVisibility(View.VISIBLE);
                    } else if (stat == 2) {
                        if (bet.equals("open")) {
                            th = "open";
                        } else if (bet.equals("close")) {
                            th = "close";
                        }

                    }


                    int key = -1;
                    boolean st = false;
                    final String d = editText.getText().toString();
                    p = etPoints.getText().toString();
//                    g = btnGameType.getText().toString();

                    boolean sr = false;
                    switch (d) {
                        case "10":
                            list.clear();
                            setArray(d10, p, th);
                            break;

                        case "11":
                            list.clear();
                            setArray(d11, p, th);
                            break;
                        case "12":
                            list.clear();
                            setArray(d12, p, th);
                            break;
                        case "13":
                            list.clear();
                            setArray(d13, p, th);
                            break;
                        case "14":
                            list.clear();
                            setArray(d14, p, th);
                            break;
                        case "15":
                            list.clear();
                            setArray(d15, p, th);
                            break;
                        case "16":
                            list.clear();
                            setArray(d16, p, th);
                            break;
                        case "17":
                            list.clear();
                            setArray(d17, p, th);
                            break;
                        case "18":
                            list.clear();
                            setArray(d18, p, th);
                            break;
                        case "19":
                            list.clear();
                            setArray(d19, p, th);
                            break;
                        case "20":
                            list.clear();
                            setArray(d20, p, th);
                            break;
                        case "22":
                            list.clear();
                            setArray(d22, p, th);
                            break;
                        case "23":
                            list.clear();
                            setArray(d23, p, th);
                            break;
                        case "24":
                            list.clear();
                            setArray(d24, p, th);
                            break;
                        case "25":
                            list.clear();
                            setArray(d25, p, th);
                            break;
                        case "26":
                            list.clear();
                            setArray(d26, p, th);
                            break;

                        case "27":
                            list.clear();
                            setArray(d27, p, th);
                            break;

                        case "28":
                            list.clear();
                            setArray(d28, p, th);
                            break;

                        case "29":
                            list.clear();
                            setArray(d29, p, th);
                            break;

                        case "30":
                            list.clear();
                            setArray(d30, p, th);
                            break;

                        case "34":
                            list.clear();
                            setArray(d34, p, th);
                            break;

                        case "35":
                            list.clear();
                            setArray(d35, p, th);
                            break;

                        case "36":
                            list.clear();
                            setArray(d36, p, th);
                            break;

                        case "37":
                            list.clear();
                            setArray(d37, p, th);
                            break;

                        case "38":
                            list.clear();
                            setArray(d38, p, th);
                            break;

                        case "39":
                            list.clear();
                            setArray(d39, p, th);
                            break;

                        case "40":
                            list.clear();
                            setArray(d40, p, th);
                            break;

                        case "44":
                            list.clear();
                            setArray(d44, p, th);
                            break;

                        case "45":
                            list.clear();
                            setArray(d45, p, th);
                            break;

                        case "46":
                            list.clear();
                            setArray(d46, p, th);
                            break;

                        case "47":
                            list.clear();
                            setArray(d47, p, th);
                            break;
                        case "48":
                            list.clear();
                            setArray(d48, p, th);
                            break;

                        case "49":
                            list.clear();
                            setArray(d49, p, th);
                            break;

                        case "50":
                            list.clear();
                            setArray(d50, p, th);
                            break;
                        case "55":
                            list.clear();
                            setArray(d55, p, th);
                            break;
                        case "56":
                            list.clear();
                            setArray(d56, p, th);
                            break;

                        case "57":
                            list.clear();
                            setArray(d57, p, th);
                            break;

                        case "58":
                            list.clear();
                            setArray(d58, p, th);
                            break;
                        case "59":
                            list.clear();
                            setArray(d59, p, th);
                            break;
                        case "60":
                            list.clear();
                            setArray(d60, p, th);
                            break;
                        case "66":
                            list.clear();
                            setArray(d66, p, th);
                            break;
                        case "67":
                            list.clear();
                            setArray(d67, p, th);
                            break;
                        case "68":
                            list.clear();
                            setArray(d68, p, th);
                            break;

                        case "69":
                            list.clear();
                            setArray(d69, p, th);
                            break;

                        case "70":
                            list.clear();
                            setArray(d70, p, th);
                            break;

                        case "77":
                            list.clear();
                            setArray(d77, p, th);
                            break;

                        case "78":
                            list.clear();
                            setArray(d78, p, th);
                            break;

                        case "79":
                            list.clear();
                            setArray(d79, p, th);
                            break;

                        case "80":
                            list.clear();
                            setArray(d80, p, th);
                            break;
                        case "88":
                            list.clear();
                            setArray(d88, p, th);
                            break;

                        case "89":
                            list.clear();
                            setArray(d89, p, th);
                            break;

                        case "90":
                            list.clear();
                            setArray(d90, p, th);
                            break;

                        case "99":
                            list.clear();
                            setArray(d99, p, th);
                            break;

                    }
                }

            }
        }
        else if (v.getId()== R.id.digit_save) {
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
            common.setBetTypeDialog(dialog,txtOpen,txtClose,txt_type,txt_date.getText().toString(),s_time,e_time);
        }
        else if (v.getId()==R.id.tv_date)
        {
            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,progressDialog);
        }
    }
    public void setArray(String[] array ,String p, String th) {
       if (Integer.parseInt(p)>Integer.parseInt(w_amount))
        {
//         Toast.makeText(getActivity(),"Insufficient Amount",Toast.LENGTH_LONG).show();
         common.errorMessageDialog("Insufficient Amount");
         clrControls();
        }
        else {
            for (int i = 0; i < array.length; i++) {
                common.addData(array[i], p, th, list, tableAdaper, list_table, btnSave);
                editText.setText("");
                etPoints.setText("");

                editText.requestFocus();
            }
        }

    }
    public void clrControls()
    {
        etDgt.setText("");
        etPoints.setText("");
        list.clear();
        txt_type.setText(getActivity().getResources().getString(R.string.select_type));
        txt_type.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_date.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_date.setText(getActivity().getResources().getString(R.string.select_date));
       btnSave.setText("Save");
    }
    }
