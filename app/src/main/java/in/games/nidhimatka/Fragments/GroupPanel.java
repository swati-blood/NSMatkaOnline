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
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.games.nidhimatka.Adapter.TableAdaper;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.Objects.sp_input_data;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;

public class GroupPanel extends Fragment implements View.OnClickListener {
    private final String[] d1={"123","178","137","678","236","367","128","268"};
    private final String[] d2={"240","245","790","470","290","579","259","457"};
    private final String[] d3={"100","150","600","556","155","560"};
    private final String[] d4={"330","880","358","380","588","335"};
    private final String[] d5={"119","169","146","466","114","669"};
    private final String[] d6={"349","344","899","448","489","399"};
    private final String[] d7={"777","222","277","227"};
    private final String[] d8={"246","129","179","147","124","679","467","269"};
    private final String[] d9={"480","340","345","890","390","458","589","359"};
    private final String[] d10={"336","133","138","188","688","368"};
    private final String[] d11={"110","660","566","115","156","160"};
    private final String[] d12={"200","255","557","700","570","250"};
    private final String[] d13={"377","237","223","778","278","228"};
    private final String[] d14={"444","999","449","499"};
    private final String[] d15={"139","189","134","148","468","346","369","689"};
    private final String[] d16={"157","170","567","120","670","125","260","256"};
    private final String[] d17={"238","233","337","378","788","288"};
    private final String[] d18={"300","355","800","558","580","350"};
    private final String[] d19={"490","990","599","445","440","459"};
    private final String[] d20={"247","477","279","779","229","224"};
    private final String[] d21={"666","111","116","166"};

    private final String[] d22={"478","248","234","789","289","347","239","379"};
    private final String[] d23={"135","130","680","180","158","568","360","356"};
    private final String[] d24={"400","455","450","900","559","590"};
    private final String[] d25={"220","225","770","270","577","257"};
    private final String[] d26={"149","144","446","469","699","199"};
    private final String[] d27={"112","126","117","167","667","266"};
    private final String[] d28={"333","888","388","338"};

    private final String[] d29={"258","235","578","780","230","280","357","370"};
    private final String[] d30={"140","456","159","145","460","569","190","690"};
    private final String[] d31={"249","299","799","479","447","244"};
    private final String[] d32={"122","177","267","127","677","226"};
    private final String[] d33={"113","136","168","118","668","366"};
    private final String[] d34={"348","334","389","339","488","889"};
    private final String[] d35={"555","000","550","500"};

    private final String[][] main=new String[][]{d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27
            ,d28,d29,d30,d31,d32,d33,d34,d35};
    Common common;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    int stat = 0;
    private Button btnAdd,btnSave,btnGameType;
    private EditText etPoints;
    LoadingBar progressDialog;
    TextView txt_date ,txt_type ,txtClose,txtOpen;
    AutoCompleteTextView editText;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id ,txt_timer,tv_timer ;
    Dialog dialog ;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type = "" ,game_date="";
    public GroupPanel() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_group_panel, container, false);
       intiView(view);
       return view;
    }

    void intiView(View v)
    {
        txt_date = v.findViewById(R.id.tv_date);
        txt_type = v.findViewById(R.id.tv_type);
        list_table=v.findViewById(R.id.list_table);
        btnAdd=(Button)v.findViewById(R.id.digit_add);
        btnSave=(Button)v.findViewById(R.id.digit_save);
        editText=(AutoCompleteTextView)v.findViewById(R.id.etSingleDigit);
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
        txt_type.setOnClickListener(this);
        txt_date.setOnClickListener(this);

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, sp_input_data.group_jodi_array);
        editText.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.digit_add)
        {
//            String date_b=btnGameType.getText().toString().trim();
//            String b[]=date_b.split(" ");
//            String vt=b[3];
            type = txt_type.getText().toString();
            game_date =txt_date.getText().toString();
            String vt = type;
            if (game_date.equals("Select Date"))
            {
                Toast.makeText(getActivity(),"Select Date",Toast.LENGTH_LONG).show();
            }
            else if (type.equals("Select Type"))
            {
                Toast.makeText(getActivity(),"Select game type",Toast.LENGTH_LONG).show();

            }

           else if(vt.equalsIgnoreCase("Open")) {

                String dData = editText.getText().toString().trim();
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    editText.setError("Please enter any digit");
                    editText.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPoints.getText().toString())) {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                } else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 10");
                        etPoints.requestFocus();
                        return;


                    }else if (pints > Integer.parseInt(w_amount)) {
                        common.errorMessageDialog("Insufficient Amount");
                    }
                    else {
                        String th = "close";

//                        th = "close";
                        int key = -1;
                        boolean st = false;
                        String d = editText.getText().toString();
                        String p = etPoints.getText().toString();


                        for (int i = 0; i <= main.length - 1; i++) {
                            for (int j = 0; j <= main[i].length - 1; j++) {
                                if (main[i][j].contains(d)) {
                                    key = i;
                                    st = true;

                                    break;
                                }

                                // Toast.makeText(GroupPanelActivity.this,"Data in j: "+main[i][j],Toast.LENGTH_LONG).show();
                            }
                            if (st == true) {

                                list.clear();
//                            Toast.makeText(GroupJodiActivity.this,"exist"+key,Toast.LENGTH_LONG).show();


                                // ArrayList<String> list = new ArrayList<String>();
                                for (int k = 0; k <= main[key].length - 1; k++) {
                                    // progressDialog.show();
                                    //list.add(main[key][k].toString());
                                    //        setTableData(main[key][k], p, th);
                                    common.addData(main[key][k], p,th,list,tableAdaper,list_table,btnSave);


                                    //arrayList.clear();
                                }


                                // Toast.makeText(GroupPanelActivity.this,"Data in j: "+list,Toast.LENGTH_LONG).show();
                                //  progressDialog.dismiss();
                                break;

                            }


                        }
                        if (st == false) {
                            Toast.makeText(getActivity(), "not exist ", Toast.LENGTH_LONG).show();
                            // progressDialog.dismiss();
                        }

//                        editText.setText("");
//                        etPoints.setText("");
//
//                        editText.requestFocus();
                    }
                    //  arrayList.clear();
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
        editText.setText("");
        list.clear();

        txt_type.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_date.setTextColor(getActivity().getResources().getColor(R.color.grey));
        txt_type.setText(getActivity().getResources().getString(R.string.select_type));
        txt_date.setText(getActivity().getResources().getString(R.string.select_date));
        btnSave.setText("Save");
    }
}
