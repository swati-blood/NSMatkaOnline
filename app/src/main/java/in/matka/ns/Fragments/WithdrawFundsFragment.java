package in.matka.ns.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import in.matka.ns.Activity.MainActivity;
import in.matka.ns.AppController;
import in.matka.ns.Common.Common;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.Intefaces.GetAppSettingData;
import in.matka.ns.Model.AppSettingModel;
import in.matka.ns.Model.PaymentMethodModel;
import in.matka.ns.R;
import in.matka.ns.Util.CustomJsonRequest;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.Session_management;
import in.matka.ns.Util.ToastMsg;

import static android.content.Context.COMPANION_DEVICE_SERVICE;
import static in.matka.ns.Activity.splash_activity.withdrw_text;
import static in.matka.ns.Config.BaseUrls.URL_PAYMENT_METHOD;
import static in.matka.ns.Config.Constants.KEY_ACCOUNNO;
import static in.matka.ns.Config.Constants.KEY_HOLDER;
import static in.matka.ns.Config.Constants.KEY_IFSC;
import static in.matka.ns.Config.Constants.KEY_PAYTM;
import static in.matka.ns.Config.Constants.KEY_PHONEPAY;
import static in.matka.ns.Config.Constants.KEY_TEZ;
import static in.matka.ns.Config.Constants.KEY_UPI;
import static in.matka.ns.Config.Constants.KEY_WALLET;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawFundsFragment extends Fragment implements View.OnClickListener {
    Common common;
    private TextView txtback, txtWalletAmount, txtMobile, txt_withdrw_instrctions;
    private LoadingBar progressDialog;
    private EditText etPoint;
    ToastMsg toastMsg;
    private Button btnSave;
    String saveCurrentDate, saveCurrentTime;
    int day, hours;
    String min_add_amount = "0";
    Session_management session_management;
    int w_amt;
    int type = -1;
    RadioGroup rd_group;
    RadioButton rb_paytm, rb_google, rb_phone, rb_bank, rb_upi;
    ArrayList<PaymentMethodModel> pList;

    public WithdrawFundsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw_funds, container, false);
        initView(view);
        common.appSettingData(new GetAppSettingData() {
            @Override
            public void getAppSettingData(AppSettingModel model) {
                min_add_amount = model.getMin_withdraw().toString();
            }
        });
        return view;
    }

    public void initView(View v) {
        ((MainActivity) getActivity()).setTitle("Withdraw Points");
        common = new Common(getActivity());
        toastMsg = new ToastMsg(getActivity());
        session_management = new Session_management(getActivity());
        txtback = (TextView) v.findViewById(R.id.txtBack);
        txtWalletAmount = (TextView) v.findViewById(R.id.wallet_amount);
        txtWalletAmount.setVisibility(View.GONE);
        rb_upi = v.findViewById(R.id.rb_upi);
        rd_group = v.findViewById(R.id.radio_group);
        rb_paytm = v.findViewById(R.id.rb_paytm);
        rb_google = v.findViewById(R.id.rb_google);
        rb_phone = v.findViewById(R.id.rb_phone);
        rb_bank = v.findViewById(R.id.rb_bank);
        ;

        pList = new ArrayList<>();
//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        TextView txtWalet=  toolbar.findViewById(R.id.txtWallet);
        w_amt = Integer.parseInt(session_management.getUserDetails().get(KEY_WALLET));
        etPoint = (EditText) v.findViewById(R.id.etRequstPoints);
        btnSave = (Button) v.findViewById(R.id.add_Request);
        txtMobile = (TextView) v.findViewById(R.id.textview5);
        txt_withdrw_instrctions = v.findViewById(R.id.withdrw_msg);
        progressDialog = new LoadingBar(getActivity());
        txt_withdrw_instrctions.setText(withdrw_text);
        btnSave.setOnClickListener(this);

        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                String getValue = rb.getText().toString();
                type = getValueType(getValue);
//                OpenValidSection(type);
            }
        });
//        saveInfoIntoDatabase("1", "123", "sxdcf", "add");
        paymentMethods();
//        Log.e("getMethod",pList.get(0).getMethod());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_Request) {

            String points = etPoint.getText().toString().trim();

            if (TextUtils.isEmpty(points)) {
                etPoint.setError("Enter Some points");
                return;
            } else {

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
                saveCurrentTime = currentTime.format(calendar.getTime());

                day = calendar.get(Calendar.DAY_OF_WEEK);


                String a[] = saveCurrentTime.split(":");
                hours = Integer.parseInt(a[0]);
                //  Toast.makeText(WithdrawalActivity.this, ""+day +hours, Toast.LENGTH_SHORT).show();
//                if((hours>=10&&hours<17)&&(day>1 && day<7))
                Log.e("sderftgyhu", String.valueOf(day));
                if (day > 1 && day == 7) {
                    String user_id = common.getUserId();
                    String pnts = String.valueOf(points);
                    String st = "pending";

                    int t_amt = Integer.parseInt(pnts);
                    if (w_amt > 0) {

                        if (t_amt < Integer.parseInt(min_add_amount)) {
                            toastMsg.toastIconError(("Minimum Withdraw amount " + min_add_amount));
                        } else {
                            if (t_amt > w_amt) {
                                toastMsg.toastIconError("Your requested amount exceeded");
                                return;
                            } else {
                                if (type == -1) {
                                    common.errorMessageDialog("Select Any One Withdraw Type");
                                } else {
                                    validateWithdrawType(type,st,"Withdrawal");
                                }
                            }
//                            else {
//                                // saveInfoIntoDatabase(user_id, String.valueOf(t_amt), st);
//                                saveInfoIntoDatabase(user_id,String.valueOf(t_amt),st,"Withdrawal");
//                            }
//
                        }

                    } else {
                        toastMsg.toastIconError("You don't have enough points in wallet ");
                    }

                } else {
                    toastMsg.toastIconError("Time Out ");
                    return;

                }

//                        saveInfoIntoDatabase(user_id,pnts,st);
            }

        }
    }

    public int getValueType(String getValue) {
        int pos = -1;
        if (getValue.equalsIgnoreCase("UPI")) {
            pos = 0;
        }
        if (getValue.equalsIgnoreCase("Paytm")) {
            pos = 1;
        } else if (getValue.equalsIgnoreCase("Google Pay")) {
            pos = 2;
        } else if (getValue.equalsIgnoreCase("Phone Pe")) {
            pos = 3;
        } else if (getValue.equalsIgnoreCase("Bank Account")) {
            pos = 4;
        }
        return pos;

    }

    private void saveInfoIntoDatabase(final String user_id, final String points, final String st, String type, String w_type) {
        progressDialog.show();
        int wl = Integer.parseInt(session_management.getUserDetails().get(KEY_WALLET));
        final String rem = String.valueOf(wl - Integer.parseInt(points));
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("points", points);
        params.put("request_status", st);
        params.put("type", type);
        params.put("wallet", rem);
        params.put("trans_id", "");
        params.put("w_type",w_type);

        CustomJsonRequest customJsonRequest = new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_REQUEST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.e("jnhbgvfc", response.toString());
                try {
                    boolean resp = response.getBoolean("responce");
                    if (resp) {
                        session_management.updateWallet(rem);
                        common.showToast("" + response.getString("message"));

                        loadFragment();
                    } else {
                        common.showToast("" + response.getString("error"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                common.showVolleyError(error);

            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest);
    }

    public void loadFragment() {
        Fragment fm = new HomeFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                .addToBackStack(null).commit();

    }

    public void paymentMethods() {
        final HashMap<String, String> params = new HashMap<>();
        common.postRequest(URL_PAYMENT_METHOD, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("PAYMENT_METHOD", response.toString());
                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        PaymentMethodModel pModel = new PaymentMethodModel();
                        JSONObject object = array.getJSONObject(i);
                        pModel.setId(object.getString("id"));
                        pModel.setMethod(object.getString("method"));
                        pModel.setWithdrwal(object.getString("withdrwal"));
                        pModel.setAdd(object.getString("add"));
                        pList.add(pModel);
                        if (pList.get(i).getMethod().equalsIgnoreCase("PhonePe") && pList.get(i).getWithdrwal().equals("1")) {
                            rb_phone.setVisibility(View.VISIBLE);
                        } else if (pList.get(i).getMethod().equalsIgnoreCase("Paytm") && pList.get(i).getWithdrwal().equals("1")) {
                            rb_paytm.setVisibility(View.VISIBLE);
                        } else if (pList.get(i).getMethod().equalsIgnoreCase("Bank") && pList.get(i).getWithdrwal().equals("1")) {
                            rb_bank.setVisibility(View.VISIBLE);
                        } else if (pList.get(i).getMethod().equalsIgnoreCase("Google Pay") && pList.get(i).getWithdrwal().equals("1")) {
                            rb_google.setVisibility(View.VISIBLE);
                        } else if (pList.get(i).getMethod().equalsIgnoreCase("UPI") && pList.get(i).getWithdrwal().equals("1")) {
                            rb_upi.setVisibility(View.VISIBLE);
                        }

                    }

                    Log.e("arraylength", String.valueOf(array.length()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void validateWithdrawType(int type,final String st, String types) {
        String points = etPoint.getText().toString().trim();
        Fragment fm = null;
        String wType = "";
        String details = "";
        if (type == 0) {
            String upi = session_management.getUserDetails().get(KEY_UPI);
            if (upi.isEmpty()) {
//
                common.showToast("Enter UPI Mobile Number");
                fm = new MyProfileFragment();
            } else if (upi.length() < 6) {
                common.showToast("Enter Valid UPI Number");
                fm = new MyProfileFragment();
//
            } else {
                wType = "UPI";
                details = upi;
            }

        } else if (type == 1) {
            String paytm = session_management.getUserDetails().get(KEY_PAYTM);
            if (paytm.isEmpty()) {
                common.showToast("Enter Paytm Number");
                fm = new MyProfileFragment();
            } else if (paytm.length() < 10) {
                common.showToast("Enter Valid Paytm Number");
                fm = new MyProfileFragment();
            } else {
                wType = "Paytm";
                details = paytm;
            }
        }
                else if(type==2) {
            String google = session_management.getUserDetails().get(KEY_TEZ);
            if (google.isEmpty()) {
                common.showToast("Enter Google Pay Number");
                fm = new MyProfileFragment();
            } else if (google.length() < 10) {
                common.showToast("Enter Valid Google Pay Number");
                fm = new MyProfileFragment();
            } else {
                wType = "Google Pay";
                details = google;
            }
        }
        else if(type==3) {
            String phone = session_management.getUserDetails().get(KEY_PHONEPAY);
            if (phone.isEmpty()) {
                common.showToast("Enter PhonePe Number");
                fm = new MyProfileFragment();
            } else if (phone.length() < 10) {
                common.showToast("Enter Valid PhonePe Number");
                fm = new MyProfileFragment();
            } else {
                wType = "PhonePe";
                details = phone;
            }
        }
        else if(type==4) {
            String holder = session_management.getUserDetails().get(KEY_HOLDER);
            String acc_no = session_management.getUserDetails().get(KEY_ACCOUNNO);
            String ifsc_code = session_management.getUserDetails().get(KEY_IFSC);
            if (holder.isEmpty()) {
                common.showToast("Enter Account Holder Name");
                fm = new BankFragment();
            } else if (acc_no.isEmpty())
            {
                common.showToast("Enter Account Number");
                fm = new BankFragment();
            }
            else if (!acc_no.matches("^\\d{9,18}$"))
            {
                common.showToast("Invalid Account Number");
                fm = new BankFragment();
            }
            else if (ifsc_code.isEmpty())
            {
                common.showToast("Enter Ifsc code");
                fm = new BankFragment();
            }

            else {
                wType ="Bank";
//                details = "Account Holder Name - "+holder +"\n" +"Account Number - "+acc_no +"\n" +"Ifsc Code - "+ ifsc_code;
            }
        }
        if((!common.checkNullString(wType))){
            saveInfoIntoDatabase(common.getUserId(),points,st,types,wType);
        }

        if (fm != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm).addToBackStack(null).commit();
        }
    }

    }
