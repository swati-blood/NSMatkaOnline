package in.games.nidhimatka.Common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import in.games.nidhimatka.Activity.LoginActivity;
import in.games.nidhimatka.Activity.MainActivity;
import in.games.nidhimatka.Activity.NewGameActivity;
import in.games.nidhimatka.Activity.PanaActivity;
import in.games.nidhimatka.Adapter.ListItemAdapter;
import in.games.nidhimatka.Adapter.TableAdaper;
import in.games.nidhimatka.AppController;
import in.games.nidhimatka.Config.BaseUrls;
import in.games.nidhimatka.Config.Constants;
import in.games.nidhimatka.Config.URLs;
import in.games.nidhimatka.Intefaces.GetAppSettingData;
import in.games.nidhimatka.Intefaces.GetRemainWallet;
import in.games.nidhimatka.Intefaces.UpdateBidAmount;
import in.games.nidhimatka.Intefaces.UpdateTotalBidAmount;
import in.games.nidhimatka.Intefaces.VolleyCallBack;
import in.games.nidhimatka.Model.AppSettingModel;
import in.games.nidhimatka.Model.MatkasObjects;
import in.games.nidhimatka.Model.Starline_Objects;
import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.Model.WalletObjects;
import in.games.nidhimatka.Prevalent.Prevalent;
import in.games.nidhimatka.R;
import in.games.nidhimatka.Util.ConnectivityReceiver;
import in.games.nidhimatka.Util.CustomJsonRequest;
import in.games.nidhimatka.Util.CustomVolleyJsonArrayRequest;
import in.games.nidhimatka.Util.LoadingBar;
import in.games.nidhimatka.Util.Module;
import in.games.nidhimatka.Util.Session_management;
import in.games.nidhimatka.Util.ToastMsg;

import static in.games.nidhimatka.Activity.PanaActivity.total;
import static in.games.nidhimatka.Config.BaseUrls.URL_INSERT_DATA;
import static in.games.nidhimatka.Config.BaseUrls.URL_MOBILE;
import static in.games.nidhimatka.Config.Constants.KEY_ID;
import static in.games.nidhimatka.Config.Constants.KEY_NAME;
import static in.games.nidhimatka.Config.Constants.KEY_WALLET;
import static in.games.nidhimatka.Config.Constants.REV_BEFORE_VALUE;
import static in.games.nidhimatka.Config.Constants.REV_BET_TYPE;
import static in.games.nidhimatka.Config.Constants.REV_FRAG_POSITION;
import static in.games.nidhimatka.Config.Constants.REV_POINTS;
import static in.games.nidhimatka.Config.Constants.REV_POSITION;
import static in.games.nidhimatka.Config.Constants.REV_TYPE;

public class Common {
    Context context;
  Session_management session_management;
  LoadingBar loadingBar;

    public Common(Context context) {
        this.context = context;
        session_management=new Session_management(context);
        loadingBar=new LoadingBar(context);
    }

    public void showToast(String s)
    {
        Toast.makeText(context,""+s, Toast.LENGTH_SHORT).show();
    }
    public void setMobileNumber(final TextView txt)
    {
        String json_tag_request="json_mobile_request";
        HashMap<String, String> params=new HashMap<String, String>();
        CustomJsonRequest customVolleyJsonArrayRequest=new CustomJsonRequest(Request.Method.GET, URL_MOBILE, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    JSONObject object=response;
                    String mobile=object.getString("mobile");
                    int m_cnt= Integer.parseInt(object.getString("count"));
                    txt.setText(mobile);
                    Constants.Matka_count=m_cnt;
                }
                catch (Exception ex)
                {
                    Toast.makeText(context,""+ex.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);

            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonArrayRequest,json_tag_request);
    }

    public String VolleyErrorMessage(VolleyError error) {
        String str_error = "";
        if (error instanceof TimeoutError) {
            str_error = "Connection Timeout";
        } else if (error instanceof AuthFailureError) {
            str_error = "Session Timeout";
            //TODO
        } else if (error instanceof ServerError) {
            str_error = "Server not responding please try again later";
            //TODO
        } else if (error instanceof NetworkError) {
            str_error = "No Internet Connection";
            //TODO
        } else if (error instanceof ParseError) {
            //TODO
            str_error = "An Unknown error occur";
        } else if (error instanceof NoConnectionError) {
            str_error = "No Internet Connection";
        } else {
            str_error = "Something Went Wrong";
        }

        return str_error;
    }
    public void showVolleyError(VolleyError error)
    {
        String msg=VolleyErrorMessage(error);
        if(!msg.isEmpty())
        {
            showToast(""+msg);
        }
    }

    public void errorMessageDialog(String message)
    {

        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error_message_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtMessage=(TextView)dialog.findViewById(R.id.txtmessage);
        Button btnOk=(Button)dialog.findViewById(R.id.btnOK);
        dialog.setCanceledOnTouchOutside(false);
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
        dialog.show();

        txtMessage.setText(message);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    //Function for net Connectivity.......

    public boolean isConnected()
    {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo=cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting())
        {
            NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileNet=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobileNet != null && mobileNet.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
            {
                return true;
            }
            else {
                return false;
            }

        }
        else
        {
            return false;
        }
    }

    public long getTimeDifference(String time) {
        long diff_e_s=0;
        Date date = new Date();
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
        String cur_time = parseFormat.format(date);
        try {
            final Date s_time = parseFormat.parse(cur_time.trim());
            Date e_time = parseFormat.parse(time.trim());
            diff_e_s = e_time.getTime() - s_time.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return diff_e_s;
    }

    public void setBetTypeTooText(Dialog dialog, final TextView txt_timer, RadioButton txtOpen, RadioButton txtClose, String m_id, final Button btnType, LoadingBar progressDialog, final String c_date, final TextView tv_timer)
    {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bettype);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

       txtOpen=dialog.findViewById(R.id.rd_open);
        txtClose=dialog.findViewById(R.id.rd_close);

//        setDataTo(txtOpen,txtClose,m_id,progressDialog,c_date);

        final Dialog finalDialog = dialog;
        final TextView finalTxtOpen = txtOpen;
        final  boolean is_open =true;
        final  boolean is_close =true;
//      true  txtOpen.setOnCheckedChangeListener();
        txtOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnType.setText(finalTxtOpen.getText().toString());
                txt_timer.setVisibility(View.VISIBLE);
                tv_timer.setVisibility(View.GONE);
                finalDialog.dismiss();

            }
        });

        final TextView finalTxtClose = txtClose;
        final Dialog finalDialog1 = dialog;
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnType.setText(finalTxtClose.getText().toString());
                tv_timer.setVisibility(View.VISIBLE);
                txt_timer.setVisibility(View.GONE);
                finalDialog1.dismiss();
            }
        });
    }
//    public void setBetTypeDialog(Dialog dialog, TextView txtOpen, TextView txtClose, String m_id, final TextView btnType, LoadingBar progressDialog, final String c_date)
//    {
//        dialog=new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_bettype);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//        txtOpen=dialog.findViewById(R.id.rd_open);
//        txtClose=dialog.findViewById(R.id.rd_close);
//        final Dialog finalDialog = dialog;
//        final TextView finalTxtOpen = txtOpen;
//        final  boolean is_open =true;
//        final  boolean is_close =true;
//        txtOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnType.setText(finalTxtOpen.getText().toString());
//                btnType.setTextColor(context.getResources().getColor(R.color.black));
//                  finalDialog.dismiss();
//
//            }
//        });
//
//        final TextView finalTxtClose = txtClose;
//        final Dialog finalDialog1 = dialog;
//        txtClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnType.setText(finalTxtClose.getText().toString());
//                btnType.setTextColor(context.getResources().getColor(R.color.black));
//                finalDialog1.dismiss();
//            }
//        });
//    }

    public String getBetType(String gdate,String strt_time,String end_time)
    {
        String bet="";
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String cDate=simpleDateFormat.format(date);
        String g_d = gdate.substring(0, 10);
        if(cDate.equals(g_d))
        {
            long sDiff=getTimeDifference(strt_time);
            long eDiff=getTimeDifference(end_time);
            if(sDiff>=0 && eDiff>=0)
            {
                bet="both open";
            }
            else if(sDiff<0 && eDiff<0)
            {
                bet="both close";
            }
            else
            {
                if(sDiff>=0)
                {
                    bet="open";
                }
                else if(eDiff>=0)
                {
                    bet="close";
                }
                else
                {
                    bet="both close";
                }

            }

        }
        else
        {
            bet="both open";
        }
        Log.e("asdsadasd",""+bet);
        return bet;
    }
    public void setBetTypeDialog(Dialog dialog, TextView txtOpen, TextView txtClose, final TextView btnType,String gameDate,String stime,String eTime)
    {
        if(gameDate.equalsIgnoreCase("Select Date"))
        {
          showToast("Select Date");
        }
        else {
            String betType = getBetType(gameDate, stime, eTime);
            if (betType.equalsIgnoreCase("both close")) {
                errorMessageDialog("BID IS CLOSED FOR TODAY");
            } else {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_bettype);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                txtOpen = dialog.findViewById(R.id.rd_open);
                txtClose = dialog.findViewById(R.id.rd_close);
                final Dialog finalDialog = dialog;
                final TextView finalTxtOpen = txtOpen;
                if (betType.equalsIgnoreCase("open") || betType.equalsIgnoreCase("both open")) {
                    if (txtOpen.getVisibility() == View.GONE) {
                        txtOpen.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (txtOpen.getVisibility() == View.VISIBLE) {
                        txtOpen.setVisibility(View.GONE);
                    }
                }

                if (betType.equalsIgnoreCase("close") || betType.equalsIgnoreCase("both open")) {
                    if (txtClose.getVisibility() == View.GONE) {
                        txtClose.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (txtClose.getVisibility() == View.VISIBLE) {
                        txtClose.setVisibility(View.GONE);
                    }
                }
                txtOpen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnType.setText(finalTxtOpen.getText().toString());
                        btnType.setTextColor(context.getResources().getColor(R.color.black));
                        finalDialog.dismiss();

                    }
                });

                final TextView finalTxtClose = txtClose;
                final Dialog finalDialog1 = dialog;
                txtClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnType.setText(finalTxtClose.getText().toString());
                        btnType.setTextColor(context.getResources().getColor(R.color.black));
                        finalDialog1.dismiss();
                    }
                });
            }
        }
    }

    public void addData(String digit, String point, String type, List<TableModel> list, TableAdaper tableAdaper, ListView list_table, Button btnSave) {

        list.add(new TableModel(digit, point, type));
        tableAdaper = new TableAdaper(list, context, btnSave);
        list_table.setAdapter(tableAdaper);
        tableAdaper.notifyDataSetChanged();
        int we = list.size();
        int points = Integer.parseInt(point);
        int tot_pnt = Integer.parseInt(getSumOfPoints(list));

        btnSave.setText("(BIDS=" + we + ")(Points=" + tot_pnt + ")");
    }

    public String getSumOfPoints(List<TableModel> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum = sum + Integer.parseInt(list.get(i).getPoints());
        }

        return String.valueOf(sum);
    }

    public void setBidsDialog(int wallet_amount , final List<TableModel> list, final String m_id, final String c, final String game_id, final String w, final String dashName, final LoadingBar progressDialog, final Button btnSave, final String start_time, final String end_time)
    {
        TableRow tr1;
        ListItemAdapter listItemAdapter = new ListItemAdapter(list,context);
        TextView txtD1,txtP1,txtT1;

        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.starline_save_layout);
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
        dialog.show();
        final TableLayout tableLayout=(TableLayout)dialog.findViewById(R.id.tblLayout1);
        ListView listView = dialog.findViewById(R.id.list_item);
        Button btn_dialog_add=(Button)dialog.findViewById(R.id.btnOk);
        TextView btnDialogCancel=(Button)dialog.findViewById(R.id.btnCancel);
        TextView txtCountBids=(TextView)dialog.findViewById(R.id.txtCountBids);
        TextView txtAmount=(TextView)dialog.findViewById(R.id.txtAmount);
        TextView txtBeforeAmount=(TextView)dialog.findViewById(R.id.txtBeforeAmount);
        TextView txtAfterAmount=(TextView)dialog.findViewById(R.id.txtAfterAmount);
        TextView matka_name = dialog.findViewById( R.id.matka_name );
        matka_name.setText( dashName );

        listView.setAdapter(listItemAdapter);

        int amt=0;
        for(int j=0;j<list.size();j++)
        {
            amt=amt+Integer.parseInt(list.get(j).getPoints());
        }

        int bid_count=list.size();
        txtCountBids.setText(String.valueOf(String.valueOf(bid_count)));
        txtAmount.setText(String.valueOf(amt));
        int w_a=wallet_amount;
        int after_amt=w_a-amt;
        txtBeforeAmount.setText(String.valueOf(w_a));
        txtAfterAmount.setText(String.valueOf(after_amt));
        btn_dialog_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(list,m_id,c,game_id,w,dashName,progressDialog,btnSave,start_time,end_time);

            }
        });

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
//                list.clear();
            }
        });
    }


    public void insertData(List<TableModel> list, String m_id, String c, String game_id, String w, String dashName, LoadingBar progressDialog, Button btnSave, final String start_time, final String end_time) {
        int er = list.size();
        if (er <= 0) {
            String message = "Please Add Some Bids";
            errorMessageDialog(message);
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

                    String asd = tableModel.getDigits().toString();
                    String asd1 = tableModel.getPoints().toString();
                    String asd2 = tableModel.getType().toString();
                    int b = 9;

                    if (asd2.equalsIgnoreCase("Close")) {
                        b = 1;
                    } else if (asd2.equalsIgnoreCase("Open")) {
                        b = 0;
                    }


                    amt = amt + Integer.parseInt(asd1);

                    char quotes = '"';
                    list_digits.add(quotes + asd + quotes);
                    list_points.add(asd1);
                    list_type.add(b);

          }


                String id = session_management.getUserDetails().get(KEY_ID).toString().trim();
                String matka_id = m_id.toString().trim();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("points", list_points);
                jsonObject.put("digits", list_digits);
                jsonObject.put("bettype", list_type);
                jsonObject.put("user_id", id);
                jsonObject.put("matka_id", matka_id);
                jsonObject.put("game_date", c);
                jsonObject.put("game_id", game_id);

                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);

                int wallet_amount = Integer.parseInt(w);
                if (wallet_amount < amt) {
                    String message = "Insufficient Amount";
                    errorMessageDialog(message);
                    return;

                } else {
                    btnSave.setEnabled(false);
                    int sWallet=Integer.parseInt(session_management.getUserDetails().get(KEY_WALLET));
                    int rem=sWallet-amt;
                    session_management.updateWallet(String.valueOf(rem));
                    if(context.toString().contains("MainActivity"))
                    {
                        ((MainActivity)context).setWallet_Amount(String.valueOf(rem));

                    }
                    else
                    {
                        ((PanaActivity)context).setWalletCounter(String.valueOf(rem));
                    }
                    updateWalletAmount(jsonArray, progressDialog, dashName, m_id,start_time,end_time);

                }
            } catch (Exception ex) {
             new ToastMsg(context).toastIconError( "Err" + ex.getMessage());
            }

        }

    }

    public void setDateDialog(Dialog dialog, final String m_id, TextView txtCurrentDate, TextView txtNextDate, TextView txtAfterNextDate, TextView txtDate_id, final TextView btnGameType ,LoadingBar loadingBar)
    {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_date_bet);
        txtCurrentDate=(TextView)dialog.findViewById(R.id.currentDate);
        txtNextDate=(TextView)dialog.findViewById(R.id.nextDate);
        txtAfterNextDate=(TextView)dialog.findViewById(R.id.afterNextDate);
        txtDate_id=(TextView)dialog.findViewById(R.id.date_id);

        txtDate_id.setVisibility(View.GONE);
        dialog.setCanceledOnTouchOutside(false);

        //setData(txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,m_id,progressDialog,OddEvenActivity.this);
        dialog.show();
        //getMatkaDateData(context,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,progressDialog);
        getDateData(m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,loadingBar);


        final Dialog finalDialog = dialog;
        final TextView finalTxtCurrentDate = txtCurrentDate;
        txtCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c= finalTxtCurrentDate.getText().toString();

                ///   String as=getDataString(c);
                btnGameType.setText(c.toString());
                btnGameType.setTextColor(context.getResources().getColor(R.color.black));
                finalDialog.dismiss();
            }
        });

        final Dialog finalDialog1 = dialog;
        final TextView finalTxtNextDate = txtNextDate;
        txtNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String c= finalTxtNextDate.getText().toString();

                // String as=getDataString(c);
                btnGameType.setText(c.toString());
                btnGameType.setTextColor(context.getResources().getColor(R.color.black));
                finalDialog1.dismiss();
            }
        });

        final Dialog finalDialog2 = dialog;
        final TextView finalTxtAfterNextDate = txtAfterNextDate;
        txtAfterNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String c= finalTxtAfterNextDate.getText().toString();

                // String as=getDataString(c);
                btnGameType.setText(c.toString());
                btnGameType.setTextColor(context.getResources().getColor(R.color.black));
                finalDialog2.dismiss();
            }
        });
    }

    // Function for Get Game Date and Day

    public void getDateData(final String m_id, final TextView txtCurrentDate, final TextView txtNextDate, final TextView txtAfterNextDate , final LoadingBar loadingBar)
    {
        loadingBar.show();
        String json_tag="json_matka_id";
        HashMap<String, String> params=new HashMap<String, String>();
        params.put("id",m_id);

        postRequest(BaseUrls.URL_MATKA_WITH_ID, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (loadingBar.isShowing())
                    {
                        loadingBar.dismiss();
                    }
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        MatkasObjects matkasObjects = new MatkasObjects();
                        matkasObjects.setId(object.getString("id"));
                        matkasObjects.setName(object.getString("name"));
                        matkasObjects.setStart_time(object.getString("start_time"));
                        matkasObjects.setEnd_time(object.getString("end_time"));
                        matkasObjects.setStarting_num(object.getString("starting_num"));
                        matkasObjects.setNumber(object.getString("number"));
                        matkasObjects.setEnd_num(object.getString("end_num"));
                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
                        matkasObjects.setSat_start_time(object.getString("sat_start_time"));
                        matkasObjects.setSat_end_time(object.getString("sat_end_time"));
                        matkasObjects.setCreated_at(object.getString("created_at"));
                        matkasObjects.setUpdated_at(object.getString("updated_at"));
                        matkasObjects.setStatus(object.getString("status"));

                        String bid_start = "";
                        String bid_end = "";
                        String dt = new SimpleDateFormat("EEEE").format(new Date());

                        String st_time = "";
                        String st_time1 = "";
                        String st_time2 = "";

                        if (dt.equals("Saturday")) {
                            st_time = matkasObjects.getSat_start_time();
                        } else if (dt.equals("Sunday")) {
                            st_time = matkasObjects.getStart_time();
                        } else {
                            st_time = matkasObjects.getBid_start_time();
                        }

                        String dt1 = getNextDay(dt);
                        if (dt1.equals("Saturday")) {
                            st_time1 = matkasObjects.getSat_start_time();
                        } else if (dt1.equals("Sunday")) {
                            st_time1 = matkasObjects.getStart_time();
                        } else {
                            st_time1 = matkasObjects.getBid_start_time();
                        }

                        String dt2 = getNextDay(dt1);
                        if (dt2.equals("Saturday")) {
                            st_time2 = matkasObjects.getSat_start_time();
                        } else if (dt2.equals("Sunday")) {
                            st_time2 = matkasObjects.getStart_time();
                        } else {
                            st_time2 = matkasObjects.getBid_start_time();
                        }


                        String nd="";
                        String and="";
                        String cd="";


                        if (st_time.equals("") && st_time.equals("null")) {

//                            txtCurrentDate.setText(dt + " Bet Close");
                            txtCurrentDate.setText("\n"+dt );
                            cd="c";

                            if (st_time1.equals("") && st_time1.equals("null")) {
//                                txtNextDate.setText(dt1 + " Bet Close");
                                txtNextDate.setText("\n"+dt1);
                                nd="c";
                            } else {
//                                txtNextDate.setText(dt1 + " Bet Open");
                                txtNextDate.setText("\n"+dt1);
                                nd="o";
                            }
                            if (st_time2.equals("") && st_time2.equals("null")) {
//                                txtAfterNextDate.setText(dt2 + " Bet Close");
                                txtAfterNextDate.setText("\n"+dt2);
                                and="c";
                            } else {
//                                txtAfterNextDate.setText(dt2 + " Bet Open");
                                txtAfterNextDate.setText("\n"+dt2);
                                and="o";
                            }


                            //  Toast.makeText(context,"Somtehin",Toast.LENGTH_LONG).show();
                        } else {
//                                Toast.makeText(context,""+matkasObjects.getSat_start_time(),Toast.LENGTH_LONG).show();

                            bid_start = st_time;
                            bid_end = matkasObjects.getBid_end_time().toString();

                            String time1 = bid_start.toString();
                            String time2 = bid_end.toString();

                            Date cdate = new Date();


                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            String time3 = format.format(cdate);
                            Date date1 = null;
                            Date date2 = null;
                            Date date3 = null;
                            try {
                                date1 = format.parse(time1);
                                date2 = format.parse(time2);
                                date3 = format.parse(time3);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            long difference = date3.getTime() - date1.getTime();
                            long as = (difference / 1000) / 60;

                            long diff_close = date3.getTime() - date2.getTime();
                            long c = (diff_close / 1000) / 60;

                            Date c_dat = new Date();
                            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy EEEE");
                            String s_dt = dateFormat2.format(c_dat);
                            String n_dt = getNextDate(s_dt);
                            String a_dt = getNextDate(n_dt);
                            if (as < 0) {
//                                progressDialog.dismiss();
                                //btn.setText(s_dt+" Bet Open");
//                                txtCurrentDate.setText(s_dt + " Bet Open");
                                txtCurrentDate.setText(s_dt);

                                //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Open",Toast.LENGTH_LONG).show();
                            } else if (c > 0) {
//                                progressDialog.dismiss();
//                                txtCurrentDate.setText(s_dt + " Bet Close");
                                txtCurrentDate.setText(s_dt );

                                // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
                            } else {
//                                progressDialog.dismiss();
                                //btn.setText(s_dt+" Bet Open");
//                                txtCurrentDate.setText(s_dt + " Bet Open");
                                txtCurrentDate.setText(s_dt );
                            }

                            if(nd.equals("c"))
                            {
                                txtNextDate.setText(n_dt );

                            }
                            else
                            {
//                                txtNextDate.setText(n_dt + " Bet Open");
                                txtNextDate.setText(n_dt);

                            }

                            if(and.equals("c"))
                            {
                                txtAfterNextDate.setText(a_dt);
//                                txtAfterNextDate.setText(a_dt + " Bet Close");

                            }
                            else
                            {
                                txtAfterNextDate.setText(a_dt);
//                                txtAfterNextDate.setText(a_dt + " Bet Open");

                            }

                        }



                    }
                } catch(Exception ex){
                    if (loadingBar.isShowing())
                    {
                        loadingBar.dismiss();
                    }
//                    progressDialog.dismiss();
                    Toast.makeText(context, "Something went wrong" + ex.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });



    }
    //Function for get Next Day
    public String getNextDay(String currentDate)
    {
        String nextDate="";

        try
        {
            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
            Date c=simpleDateFormat.parse(currentDate);
            calendar.setTime(c);
            calendar.add(Calendar.DAY_OF_WEEK,1);
            nextDate=simpleDateFormat.format(calendar.getTime());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //Toast.makeText(OddEvenActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return nextDate.toString();
    }

    // Function for get Next Date
    public String getNextDate(String currentDate)
    {
        String nextDate="";
        try
        {
            Calendar calendar= Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
            Date c=simpleDateFormat.parse(currentDate);
            calendar.setTime(c);
            calendar.add(Calendar.DAY_OF_WEEK,1);
            nextDate=simpleDateFormat.format(calendar.getTime());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //Toast.makeText(OddEvenActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return nextDate.toString();
    }

    public void  setCounterTimer(long diff,final TextView txt_timer)
    {
        CountDownTimer countDownTimer = new CountDownTimer(diff, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                txt_timer.setText(text);

            }

            @Override
            public void onFinish() {

                txt_timer.setText("Bid Closed");

            }
        }.start();

    }

    public void setEndCounterTimer(long diff,final TextView txt_timer)
    {
        CountDownTimer countDownTimer = new CountDownTimer(diff, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), " %02d : %02d : %02d ",

                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 60, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);

                txt_timer.setText(text);
            }
            @Override
            public void onFinish() {
                txt_timer.setText("Bid Closed");
            }
        }.start();

    }



    public void getStarlineGameData(final String m_id, final Button btnType, final LoadingBar progressDialog)
    {
        progressDialog.show();

        String json_tag="json_starline";
        HashMap<String, String> params=new HashMap<>();
        params.put("id",m_id);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_StarLine_id, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    JSONObject jsonObject=response;
                    Starline_Objects matkasObjects=new Starline_Objects();
                    matkasObjects.setId(jsonObject.getString("id"));
                    matkasObjects.setS_game_time(jsonObject.getString("s_game_time"));
                    matkasObjects.setS_game_number(jsonObject.getString("s_game_number"));

                    progressDialog.dismiss();
                    btnType.setText(matkasObjects.getS_game_time());

                }
                catch(Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Something "+ex.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);



    }

    //Funtion for getDateDataTo
    public void setDateAndBetTpeTo(Dialog dialog, final String m_id, TextView txtCurrentDate, TextView txtNextDate, TextView txtAfterNextDate, TextView txtDate_id, final Button btnGameType, LoadingBar progressDialog)
    {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_date_bet);


        txtCurrentDate=(TextView)dialog.findViewById(R.id.currentDate);
        txtNextDate=(TextView)dialog.findViewById(R.id.nextDate);
        txtAfterNextDate=(TextView)dialog.findViewById(R.id.afterNextDate);
        txtDate_id=(TextView)dialog.findViewById(R.id.date_id);

        txtDate_id.setVisibility(View.GONE);
        dialog.setCanceledOnTouchOutside(false);

        //setData(txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,m_id,progressDialog,OddEvenActivity.this);
        dialog.show();
        getDateDataTo(m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,progressDialog);


        final Dialog finalDialog = dialog;
        final TextView finalTxtCurrentDate = txtCurrentDate;
        txtCurrentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c= finalTxtCurrentDate.getText().toString();

                ///   String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog.dismiss();
            }
        });

        final Dialog finalDialog1 = dialog;
        final TextView finalTxtNextDate = txtNextDate;
        txtNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String c= finalTxtNextDate.getText().toString();

                // String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog1.dismiss();
            }
        });

        final Dialog finalDialog2 = dialog;
        final TextView finalTxtAfterNextDate = txtAfterNextDate;
        txtAfterNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String c= finalTxtAfterNextDate.getText().toString();

                // String as=getDataString(c);
                btnGameType.setText(c.toString());
                finalDialog2.dismiss();
            }
        });
    }
    //Function for button btnGametype in RedBracket

    public void getDateDataTo(final String m_id, final TextView txtCurrentDate, final TextView txtNextDate, final TextView txtAfterNextDate, final LoadingBar progressDialog)
    {

        progressDialog.show();

        String json_tag="json_getDtat";
        HashMap<String, String> params=new HashMap<>();
        params.put("id",m_id);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response;
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        MatkasObjects matkasObjects = new MatkasObjects();
                        matkasObjects.setId(object.getString("id"));
                        matkasObjects.setName(object.getString("name"));
                        matkasObjects.setStart_time(object.getString("start_time"));
                        matkasObjects.setStarting_num(object.getString("starting_num"));
                        matkasObjects.setNumber(object.getString("number"));
                        matkasObjects.setEnd_num(object.getString("end_num"));
                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
                        matkasObjects.setCreated_at(object.getString("created_at"));
                        matkasObjects.setUpdated_at(object.getString("updated_at"));
                        matkasObjects.setStatus(object.getString("status"));

                        String bid_start = matkasObjects.getBid_start_time();
                        String bid_end=matkasObjects.getBid_end_time().toString();

                        String time1 = bid_start.toString();
                        String time2 = bid_end.toString();

                        Date cdate=new Date();



                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        String time3=format.format(cdate);
                        Date date1 = null;
                        Date date2=null;
                        Date date3=null;
                        try {
                            date1 = format.parse(time1);
                            date2 = format.parse(time2);
                            date3=format.parse(time3);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        long difference = date3.getTime() - date1.getTime();
                        long as=(difference/1000)/60;

                        long diff_close=date3.getTime()-date2.getTime();
                        long c=(diff_close/1000)/60;

                        Date c_dat=new Date();
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                        String s_dt=dateFormat.format(c_dat);
                        String n_dt= getNextDate(s_dt);
                        String a_dt= getNextDate(n_dt);
                        if(as<0)
                        {
                            progressDialog.dismiss();
                            //btn.setText(s_dt+" Bet Open");
                            txtCurrentDate.setText(s_dt+" Bet Open");
                            txtNextDate.setText(n_dt+" Bet Open");
                            txtAfterNextDate.setText(a_dt+" Bet Open");

                            //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Open",Toast.LENGTH_LONG).show();
                        }
                        else //if(c>0)
                        {progressDialog.dismiss();
                            txtCurrentDate.setText(s_dt+" Bet Close");
                            txtNextDate.setText(n_dt+" Bet Open");
                            txtAfterNextDate.setText(a_dt+" Bet Open");

                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Something erong", Toast.LENGTH_LONG).show();


                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Something erong"+ex.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);

            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);



    }

    //Function for red_bracket,jodi group

    public void setBetDateDayTo(final String m_id, final Button btnGameType, final LoadingBar progressDialog) {

        progressDialog.show();

        String json_tag="json_bet";
        HashMap<String, String> params=new HashMap<>();
        params.put("id",m_id);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response;
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        MatkasObjects matkasObjects = new MatkasObjects();
                        matkasObjects.setId(object.getString("id"));
                        matkasObjects.setName(object.getString("name"));
                        matkasObjects.setStart_time(object.getString("start_time"));
                        matkasObjects.setStarting_num(object.getString("starting_num"));
                        matkasObjects.setNumber(object.getString("number"));
                        matkasObjects.setEnd_num(object.getString("end_num"));
                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
                        matkasObjects.setCreated_at(object.getString("created_at"));
                        matkasObjects.setUpdated_at(object.getString("updated_at"));
                        matkasObjects.setStatus(object.getString("status"));

                        String bid_start = matkasObjects.getBid_start_time();
                        String bid_end=matkasObjects.getBid_end_time().toString();

                        String time1 = bid_start.toString();
                        String time2 = bid_end.toString();

                        Date cdate=new Date();



                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        String time3=format.format(cdate);
                        Date date1 = null;
                        Date date2=null;
                        Date date3=null;
                        try {
                            date1 = format.parse(time1);
                            date2 = format.parse(time2);
                            date3=format.parse(time3);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        long difference = date3.getTime() - date1.getTime();
                        long as=(difference/1000)/60;

                        long diff_close=date3.getTime()-date2.getTime();
                        long c=(diff_close/1000)/60;

                        Date c_dat=new Date();
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
                        String s_dt=dateFormat.format(c_dat);
                        String n_dt= getNextDate(s_dt);
                        String a_dt= getNextDate(n_dt);

                        if(as>0)
                        {progressDialog.dismiss();
                            btnGameType.setText(s_dt+" Bet Close");


                            // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            btnGameType.setText(s_dt+" Bet Open");

                        }


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Something wrong", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Something wrong"+ex.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);
    }

    //New function for getBetType and Bet Date
    public void getBetSession(final String m_id, final LoadingBar progressDialog, final VolleyCallBack callBack)
    {
        String json_request_tag="matka_with_id";
        HashMap<String, String> params=new HashMap<String, String>();
        params.put("id",m_id);
        progressDialog.show();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, BaseUrls.URL_MATKA_WITH_ID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    String status=response.getString("status");
                    if(status.equals("success"))
                    {
                        JSONObject jsonObject=response.getJSONObject("data");
                        MatkasObjects matkasObjects = new MatkasObjects();
                        matkasObjects.setId(jsonObject.getString("id"));
                        matkasObjects.setName(jsonObject.getString("name"));
                        matkasObjects.setStart_time(jsonObject.getString("start_time"));
                        matkasObjects.setEnd_time(jsonObject.getString("end_time"));
                        matkasObjects.setStarting_num(jsonObject.getString("starting_num"));
                        matkasObjects.setNumber(jsonObject.getString("number"));
                        matkasObjects.setEnd_num(jsonObject.getString("end_num"));
                        matkasObjects.setBid_start_time(jsonObject.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(jsonObject.getString("bid_end_time"));
                        matkasObjects.setCreated_at(jsonObject.getString("created_at"));
                        matkasObjects.setUpdated_at(jsonObject.getString("updated_at"));
                        matkasObjects.setSat_start_time(jsonObject.getString("sat_start_time"));
                        matkasObjects.setSat_end_time(jsonObject.getString("sat_end_time"));
                        matkasObjects.setStatus(jsonObject.getString("status"));

                        String dt=new SimpleDateFormat("EEEE").format(new Date());
                        String bid_start = "";
                        String bid_end="";
//                        String bid_start = matkasObjects.getBid_start_time();
//                        String bid_end=matkasObjects.getBid_end_time().toString();

                        if(dt.equals("Sunday"))
                        {
                            bid_start=matkasObjects.getStart_time();
                            bid_end=matkasObjects.getEnd_time();
                        }
                        else if(dt.equals("Saturday"))
                        {
                            bid_start=matkasObjects.getSat_start_time();
                            bid_end=matkasObjects.getSat_end_time();

                        }
                        else
                        {
                            bid_start=matkasObjects.getBid_start_time();
                            bid_end=matkasObjects.getBid_end_time();

                        }


                        Module module=new Module();
                        String time1 = bid_start.toString();
                        String time2 = bid_end.toString();
                        long start_diff=getTimeDifference(time1);
                        long end_diff=getTimeDifference(time2);
                        HashMap<String, String> map=new HashMap<>();
                        map.put("s_diff", String.valueOf(start_diff));
                        map.put("e_diff", String.valueOf(end_diff));
                        callBack.getTimeDiffrence(map);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Something wrong", Toast.LENGTH_LONG).show();


                    }
           }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(context,"something went wrong ", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);
            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);
    }

    public void setSessionTimeOut(final Context context)
    {
        try
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    sessionTimeOut();

                }
            },600000);
        }
        catch (Exception ex)
        {

        }

    }

    public void sessionTimeOut()
    {

        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error_message_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtMessage=(TextView)dialog.findViewById(R.id.txtmessage);
        Button btnOk=(Button)dialog.findViewById(R.id.btnOK);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        txtMessage.setText("Session TimeOut");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent1=new Intent(context, LoginActivity.class);
                dialog.dismiss();
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent1);



            }
        });

    }

    public void updateWalletAmount(final JSONArray jsonArray, final LoadingBar progressDialog, final String matka_name, final String m_id, final String start_time, final String end_time )
    {
        final String data= String.valueOf(jsonArray);
        String json_request_tag="json_insert_request";
        HashMap<String, String> params=new HashMap<String, String>();
        params.put("data",data);
        Log.e("json_arr",data);


     //  Toast.makeText(context,""+data,Toast.LENGTH_LONG).show();
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
        progressDialog.show();


        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URL_INSERT_DATA, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    Log.d("insert_data",response.toString());
                    JSONObject jsonObject=response;
                    final String status=jsonObject.getString("status");
                    progressDialog.dismiss();
                    if(status.equals("success"))
                    {

                        Intent intent=new Intent(context, MainActivity.class);
//                        intent.putExtra("matkaName",matka_name);
//                        intent.putExtra("m_id",m_id);
//                        intent.putExtra("end_time",end_time);
//                        intent.putExtra("start_time",start_time);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        context.startActivity(intent);

                       new ToastMsg(context).toastIconSuccess("Bid Added Successfully.");
                    }
                    else if(status.equals("failed"))
                    {
                        String sd=status.toString();
                        errorMessageDialog(sd.toString());
                        // Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                    }
                    else if(status.equals("timeout"))
                    {

                        final Dialog myDialog=new Dialog(context);
                        myDialog.setContentView(R.layout.dialog_error_message_dialog);
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        TextView txtmessage=(TextView)myDialog.findViewById(R.id.txtmessage);
                        Button btnOK=(Button) myDialog.findViewById(R.id.btnOK);

                        txtmessage.setText("Biding closed for this date");
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                myDialog.dismiss();

                                String sd=status.toString();
                                //errorMessageDialog(context,sd.toString());
                                Intent intent=new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                            }
                        });

                        myDialog.show();
//                        String sd=status.toString();
//                        errorMessageDialog(context,sd.toString());
//                        Intent intent=new Intent(context,HomeActivity.class);
//                        context.startActivity(intent);


                    }




                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();
                    ex.printStackTrace();
                    Log.e("",ex.getStackTrace().toString());
//                    Toast.makeText(context,"Err"+ex.getMessage(), Toast.LENGTH_LONG).show();
                    new ToastMsg(context).toastIconError("Err"+ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                String msg=VolleyErrorMessage(error);
                errorMessageDialog(msg);


            }
        });
        customJsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);




    }

    public String getRandomKey(int i)
    {
        final String characters="0123456789";
        StringBuilder stringBuilder=new StringBuilder();
        while (i>0)
        {
            Random ran=new Random();
            stringBuilder.append(characters.charAt(ran.nextInt(characters.length())));
            i--;
        }
        return stringBuilder.toString();
    }

    public String checkNull(String s)
    {
        String str="";
        if(s==null || s.isEmpty() || s.equalsIgnoreCase("null"))
        {
            str="";
        }
        else
        {
            str=s;
        }
        return str;
    }

    public void setDataEditText(EditText edt, String data)
    {
        String s=data.toString();
        if(data.equalsIgnoreCase("null"))
        {

        }
        else
        {
            edt.setText(data.toString());
        }
    }
    public String getUserId()
    {
        return session_management.getUserDetails().get(KEY_ID).toString();
    }
    public String getUserWallet()
    {
        return session_management.getUserDetails().get(KEY_WALLET).toString();
    }

    public void updatePoints(ArrayList<TableModel> list,int pos,String points,String betType)
    {
        TableModel tableModel=list.get(pos);
        tableModel.setPoints(points);
        tableModel.setType(betType);
    }

    public String get24To12Format(String timestr)
    {
        String tm="";
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

        try {
            Date _24Hourst = _24HourSDF.parse(timestr);
            tm = _12HourSDF.format(_24Hourst);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tm;
    }
    public void printBidList(ArrayList<TableModel> list)
    {
        for(int i=0; i<list.size();i++)
        {
            Log.e("bet_list_data",""+list.get(i).getDigits()+" - "+list.get(i).getPoints()+" - "+list.get(i).getType());
        }
    }

    public void setPanaPoints(HashMap<String,String> map, int totAmt, ArrayList<TableModel> list,String game_name, final UpdateTotalBidAmount updateTotalBidAmount)
    {

         int pos = 0;
         if(game_name.equalsIgnoreCase("Single Pana"))
         {
             switch (map.get(REV_FRAG_POSITION).toString())
             {
                 case "1":
                     pos =Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "2":
                     pos =12+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "3":
                     pos =24+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "4":
                     pos =36+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "5":
                     pos =48+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "6":
                     pos =60+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "7":
                     pos =72+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "8":
                     pos =84+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "9":
                     pos =96+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "10":
                     pos =108+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
             }

         }
         else
         {
             switch (map.get(REV_FRAG_POSITION).toString())
             {
                 case "1":
                     pos =Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "2":
                     pos =10+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "3":
                     pos =20+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "4":
                     pos =30+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "5":
                     pos =40+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "6":
                     pos =50+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "7":
                     pos =60+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "8":
                     pos =70+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "9":
                     pos =80+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
                 case "10":
                     pos =90+Integer.parseInt(map.get(REV_POSITION).toString());
                     break;
             }

         }

            if(map.get(REV_TYPE).toString().equalsIgnoreCase("add"))
            {
                addBidToList(map.get(REV_POINTS).toString(), map.get(REV_BET_TYPE).toString(), pos, map.get(REV_BEFORE_VALUE).toString(), totAmt, list, new UpdateBidAmount() {
                    @Override
                    public void updateBidAmount(int amt) {

                        updateTotalBidAmount.updateTotalBidAmount(amt);
                    }
                });
            }
            else if(map.get(REV_TYPE).toString().equalsIgnoreCase("sub"))
            {
                removeBidToList(map.get(REV_POINTS).toString(), map.get(REV_BET_TYPE).toString(), pos, map.get(REV_BEFORE_VALUE).toString(), totAmt, list, new UpdateBidAmount() {
                    @Override
                    public void updateBidAmount(int amt) {
                        updateTotalBidAmount.updateTotalBidAmount(amt);
                    }
                });
            }

    }

    private void removeBidToList(String pnts, String bet_type, int pos, String beforevalue, int tot, ArrayList<TableModel> bet_list, UpdateBidAmount updateBidAmount) {
        if(!pnts.isEmpty())
        {
            if(tot!=0)
            {
                int tx= Integer.parseInt(pnts);
                int beforeValue=Integer.parseInt(beforevalue);

                if(pnts.length()==1)
                {
                    tot = (tot)-beforeValue;
                }
                else if(pnts.length()==2)
                {
                    tot = (tot+tx)-beforeValue;
                }
                else if(pnts.length() == 3)
                {
                    tot = (tot+tx)-beforeValue;
                }
                else if(pnts.length()==4)
                {

                    tot = (tot+tx)-beforeValue;
                }
                else if(pnts.length()==5)
                {

                    tot = (tot+tx)-beforeValue;
                }
                updateBidAmount.updateBidAmount(tot);
                if (bet_type.toString().equals(context.getResources().getString(R.string.select_type)))
                {
                }
                else {
                    if(pnts.length()>1)
                    {
                        updatePoints(bet_list,pos,pnts,bet_type.toString());
                    }else
                    {
                        updatePoints(bet_list,pos,"0",bet_type.toString());
                    }
                }
            }
        }

    }

    private void addBidToList(String points, String bet_type, int position, String beforevalue,int tot,ArrayList<TableModel> bet_list,UpdateBidAmount updateBidAmount) {
        if (points.length() != 0) {

            if (points.isEmpty()) {

            }
            else {
                int ps = Integer.parseInt(points);
                if(points.length()==2)
                {
                    tot = tot + ps;
                }
                else if(points.length() == 3)
                {
                    tot = (tot + ps)-Integer.parseInt(beforevalue);
                }
                else if(points.length()==4)
                {
                    tot = (tot + ps)-Integer.parseInt(beforevalue);
  }
                else if(points.length()==5)
                {
                    tot = (tot + ps)-Integer.parseInt(beforevalue);
                }
                updateBidAmount.updateBidAmount(tot);
                if (bet_type.toString().equals(context.getResources().getString(R.string.select_type)))
                {
                }
                else {
                    updatePoints(bet_list,position,points,bet_type.toString());
                }
            }
        }
    }

    public int getIndexFromFragmentPosition(int fragPos,String game_name)
    {
        int pos=0;
        if(game_name.equalsIgnoreCase("Single Pana"))
        {
            switch (fragPos)
            {
                case 1:
                    pos=0;
                    break;
                case 2:
                    pos=12;
                    break;
                case 3:
                    pos=24;
                    break;
                case 4:
                    pos=36;
                    break;
                case 5:
                    pos=48;
                    break;
                case 6:
                    pos=60;
                    break;
                case 7:
                    pos=72;
                    break;
                case 8:
                    pos=84;
                    break;
                case 9:
                    pos=96;
                    break;
                case 10:
                    pos=108;
                    break;
            }

        }
        else if(game_name.equalsIgnoreCase("Double Pana"))
        {
            switch (fragPos)
            {
                case 1:
                    pos=0;
                    break;
                case 2:
                    pos=10;
                    break;
                case 3:
                    pos=20;
                    break;
                case 4:
                    pos=30;
                    break;
                case 5:
                    pos=40;
                    break;
                case 6:
                    pos=50;
                    break;
                case 7:
                    pos=60;
                    break;
                case 8:
                    pos=70;
                    break;
                case 9:
                    pos=80;
                    break;

            }

        }
        return pos;
    }
   public String getPointsOnIndex(ArrayList<TableModel> list,int pox)
   {
       String str="";
       if(!(list.size()<=0)) {
           String pnt = list.get(pox).getPoints().toString();
           if (!pnt.equals("0")) {
               str = pnt;
           }
       }
       return str;
   }

    public void getWalletAmount()
    {
        loadingBar.show();
        HashMap<String,String> params=new HashMap<>();
        params.put("user_id",session_management.getUserDetails().get(KEY_ID).toString());
        CustomVolleyJsonArrayRequest jsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.POST, BaseUrls.URL_GET_WALLET_AMOUNT, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loadingBar.dismiss();
                Log.e("wallet",response.toString());
                try {
                    JSONObject object=response.getJSONObject(0);
                    String wamt=object.getString("wallet_points");
                    session_management.updateWallet(wamt);
                    Log.e("Common_wallet","wallet_amt_-- "+session_management.getUserDetails().get(KEY_WALLET));
                    ((MainActivity)context).setWallet_Amount(String.valueOf(wamt));

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                showVolleyError(error);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    public boolean checkNullString(String str){

        if(str == null || str.isEmpty() || str.equalsIgnoreCase("null")){
            return true;
        }else{
            return false;
        }
    }

    public void appSettingData(final GetAppSettingData settingData){
        loadingBar.show();
        HashMap<String,String> params=new HashMap<>();
        CustomVolleyJsonArrayRequest arrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.POST, BaseUrls.URL_INDEX, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loadingBar.dismiss();
                try {
                    List<AppSettingModel> list=new ArrayList<>();
                    list.clear();
                    Gson gson=new Gson();
                    Type listType=new TypeToken<List<AppSettingModel>>(){}.getType();
                    list=gson.fromJson(response.toString(),listType);
                    settingData.getAppSettingData(list.get(0));

                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                showVolleyError(error);
            }
        });
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

    public void postRequest(final String url, final HashMap<String,String> params, Response.Listener<String> listener, Response.ErrorListener errorListener){
        if(!ConnectivityReceiver.isConnected()){
            showToast("No Internet Connection");
            return;
        }

        StringRequest request=new StringRequest(Request.Method.POST,url,listener,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.e("url_params", ""+url+" - "+params);
                return params;
            }
        };
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(mRetryPolicy);
        AppController.getInstance().addToRequestQueue(request,"req");
    }

    public void whatsapp(String phone, String message) {
        PackageManager packageManager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        try {
            String url = "whatsapp://send?phone=91"+ phone +"&text=" + URLEncoder.encode(message, "UTF-8");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}



