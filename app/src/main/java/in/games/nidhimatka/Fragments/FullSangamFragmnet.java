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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Adapter.TableAdaper;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.Prevalent.Prevalent;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.ToastMsg;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullSangamFragmnet extends Fragment implements View.OnClickListener {
    Common common;
    private int stat=0;
    private TextView txtType,btnDelete;
    private int val_p=0;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private Dialog dialog;
    ToastMsg toastMsg ;
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


    private Button btnAdd,btnSave,btnType,btnGameType;
    private EditText etDgt,etPnt;
     private EditText etPoints;
    LoadingBar progressDialog;
    ListView list_table;
    TableAdaper tableAdaper;
    List<TableModel> list;
    AutoCompleteTextView etOpenPana,etClosePana;
    TextView txt_date ,txt_type ;
    private String matka_id,e_time,s_time ,matka_name , game_id , game_name , w_amount ,type = "open",game_date="";
    public FullSangamFragmnet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_full_sangam_fragmnet, container, false);
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
        etOpenPana=v.findViewById(R.id.et_OpenPanna);
        etClosePana=v.findViewById(R.id.et_ClosePanna);
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
        w_amount = ((MainActivity) getActivity()).getWallet();
        list=new ArrayList<>();
        btnAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        txt_date.setOnClickListener(this);
        txt_type.setOnClickListener(this);
        ((MainActivity) getActivity()).setTitle(matka_name+"-"+game_name);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,singlePaana);
        etOpenPana.setAdapter(adapter);
        etClosePana.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.digit_add) {

            type = txt_type.getText().toString();
            game_date = txt_date.getText().toString();
            String vt = type;

            if (game_date.equals("Select Date"))
            {
                toastMsg.toastIconError("Select Date");
            }
//            else if (type.equals("Select Type"))
//            {
//                toastMsg.toastIconError("Select game type");
//
//            }

//            if (vt.equalsIgnoreCase("Open")) {
                String open_pana = etOpenPana.getText().toString().trim();
                String close_pana = etClosePana.getText().toString().trim();
                String points = etPoints.getText().toString().trim();

                if (TextUtils.isEmpty(open_pana)) {
                    etOpenPana.setError("Enter open pana");
                    etOpenPana.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(close_pana)) {
                    etClosePana.setError("Enter close pana");
                    etClosePana.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(points)) {
                    etPoints.setError("Enter points");
                    etPoints.requestFocus();
                    return;
                } else if (!Arrays.asList(singlePaana).contains(open_pana)) {
                    toastMsg.toastIconError("This is invalid pana");
                    etOpenPana.setText("");
                    etOpenPana.requestFocus();
                    return;

                } else if (!Arrays.asList(singlePaana).contains(close_pana)) {
                    toastMsg.toastIconError("This is invalid pana");
                    etClosePana.setText("");
                    etClosePana.requestFocus();
                    return;

                } else {
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
                        //  setTableRowData(open_pana, close_pana, points);
                        common.addData(open_pana + "-" + close_pana, points, type, list, tableAdaper, list_table, btnSave);
                        etOpenPana.setText("");
                        etClosePana.setText("");
                        etPoints.setText("");
                        etOpenPana.requestFocus();
                    }
                }

//            }
//        else if (vt.equalsIgnoreCase("Close")) {
//                String message = "Biding closed for this date";
//                common.errorMessageDialog(message);
//                return;
//            }

        } else if (v.getId() == R.id.digit_save) {
            int er = list.size();
            if (er <= 0) {
                String message = "Please Add Some Bids";
                toastMsg.toastIconError(message);
                return;
            } else {

                int amt = 0;
                ArrayList list_digits = new ArrayList();
                ArrayList list_type = new ArrayList();
                ArrayList list_points = new ArrayList();
                int rows = list.size();


                for (int i = 0; i < rows; i++) {


                    TableModel tableModel = list.get(i);
                    String asd = tableModel.getDigits().toString();
                    String d_all[] = asd.split("-");
                    String d0 = d_all[0].toString();
                    String d1 = d_all[1].toString();

                    String asd1 = tableModel.getPoints().toString();
                    String asd2 = tableModel.getType().toString();
                    int b = 1;
                    if (asd2.equals("Full Sangam")) {
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


                String id = common.getUserId().trim();

                String date = game_date.substring(0,10);
//                String dt = btnGameType.getText().toString().trim();
//                String d[] = dt.split(" ");
//
//                String c = d[0].toString();

                JSONObject jsonObject = new JSONObject();
                try {
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

                        String message = "Insufficient Amount";
                        toastMsg.toastIconError(message);
                        return;

                    } else {
                        int up_amt = wallet_amount - amt;
                        String asd = String.valueOf(up_amt);
                        String userid = common.getUserId();
                        btnSave.setEnabled(false);
                        common.setBidsDialog(Integer.parseInt(w_amount), list, matka_id, type, game_id, w_amount, matka_name, progressDialog, btnSave, s_time, e_time);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        else if (v.getId()== R.id.tv_date)
        {
            common.setDateDialog(dialog,matka_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,txt_date,progressDialog);
        }
    }
}
