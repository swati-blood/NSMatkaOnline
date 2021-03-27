package in.matka.ns.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.matka.ns.Activity.MainActivity;
import in.matka.ns.Adapter.NewMatkaAdpater;
import in.matka.ns.Common.Common;
import in.matka.ns.Config.BaseUrls;
import in.matka.ns.Fragments.starline.StarlineFragment;
import in.matka.ns.Intefaces.GetAppSettingData;
import in.matka.ns.Model.AppSettingModel;
import in.matka.ns.Model.MatkasObjects;
import in.matka.ns.R;
import in.matka.ns.Util.ConnectivityReceiver;
import in.matka.ns.Util.CustomVolleyJsonArrayRequest;
import in.matka.ns.Util.LoadingBar;
import in.matka.ns.Util.Module;
import in.matka.ns.Util.Session_management;
import in.matka.ns.networkconnectivity.NoInternetConnection;

import static in.matka.ns.Activity.splash_activity.app_link;
import static in.matka.ns.Activity.splash_activity.dialog_image;
import static in.matka.ns.Activity.splash_activity.ver_code;


import static in.matka.ns.Config.BaseUrls.URL_Matka;

public class HomeFragment extends Fragment implements View.OnClickListener {

    TextView tv_admin,tv_number ,text_number;

    public final String TAG=HomeFragment.class.getSimpleName();
    NewMatkaAdpater matkaAdapter ;
    private ArrayList<MatkasObjects> matkaList;
    private RecyclerView rv_matka;
    LoadingBar progressDialog;
    Common common;
    Session_management session_management;
    Module module;
    public static String mainName="";
    int flag =0 ;
    float version_code ;
    SliderLayout home_slider ;
    CardView card_starline,card_home;
    String wNo,wMsg;
    RelativeLayout rl_whatsapp;
    TextView tv_starline;
    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_home2, container, false);
       initViews(view);

       view.setFocusableInTouchMode(true);
       view.requestFocus();
       view.setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                   AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                   builder.setTitle("Confirmation");
                   builder.setMessage("Are you sure want to exit?");
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                           //((MainActivity) getActivity()).finish();
                           getActivity().finishAffinity();


                       }
                   })
                           .setNegativeButton("No", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   dialogInterface.dismiss();
                               }
                           });
                   final AlertDialog dialog=builder.create();
                   dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                       @Override
                       public void onShow(DialogInterface arg0) {
                           dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                           dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                       }
                   });
                   dialog.show();
                   return true;
               }
               return false;
           }
       });


//       rv_matka.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_matka, new RecyclerTouchListener.OnItemClickListener() {
//           @Override
//           public void onItemClick(View view, int position) {
//               MatkasObjects objects = matkaList.get(position);
//               Bundle bundle = new Bundle();
//
//               bundle.putString("matka_name",objects.getName());
//               bundle.putString("m_id",objects.getId());
//               bundle.putString("start_number",objects.getStarting_num());
//               bundle.putString("number",objects.getNumber());
//               bundle.putString("end_number",objects.getEnd_num());
//               bundle.putString("end_time",objects.getBid_end_time());
//               bundle.putString("start_time",objects.getBid_start_time());
//               Fragment fm  = new MainFragment();
//               fm.setArguments(bundle);
//               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//               fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                       .addToBackStack(null).commit();
//
//           }
//
//           @Override
//           public void onLongItemClick(View view, int position) {
//
//           }
//       }));
       return  view;
    }

   private void initViews(View v)
   {
      // text_number=(TextView)v.findViewById (R.id.number);

       tv_number=v.findViewById (R.id.tv_number);
       rl_whatsapp=v.findViewById (R.id.rl_whatsapp);
       tv_starline=v.findViewById (R.id.tv_starline);
       rl_whatsapp.setOnClickListener(this);


       matkaList = new ArrayList<>();
    session_management=new Session_management(getActivity());
       ((MainActivity) getActivity()).setTitle(getActivity().getResources().getString(R.string.app_name));
    rv_matka= v.findViewById(R.id.listView);
    home_slider= v.findViewById(R.id.home_slider);
       card_home= v.findViewById(R.id.card_home);
    progressDialog = new LoadingBar(getActivity());
    common = new Common(getActivity());
    module = new Module();
    card_starline = v.findViewById(R.id.card_starline);

       tv_admin=v.findViewById (R.id.tv_admin);
    card_starline.setOnClickListener(this);

   }



    public void getMatkaData()
    {
        progressDialog.show();
        HashMap<String,String>params = new HashMap<> (  );
        CustomVolleyJsonArrayRequest jsonArrayRequest = new CustomVolleyJsonArrayRequest (Request.Method.POST, URL_Matka, params, new com.android.volley.Response.Listener<JSONArray> ( ) {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, "onResponse: "+response.toString() );
                matkaList.clear();
                for(int i=0; i<response.length();i++)
                {
                    try
                    {
                        JSONObject jsonObject=response.getJSONObject(i);
                        MatkasObjects matkasObjects=new MatkasObjects();
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
                        matkasObjects.setLoader(jsonObject.getString("loader"));
                        matkasObjects.setText(jsonObject.getString("text"));
                        matkasObjects.setText_status(jsonObject.getString("text_status"));
                        matkaList.add(matkasObjects);
                        matkaAdapter.notifyDataSetChanged();


                    }
                    catch (Exception ex)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                        return;
                    }
                }

                progressDialog.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                String msg=common.VolleyErrorMessage(error);
                if(!msg.isEmpty())
                {
                    common.showToast(""+msg);
                }

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);



    }



    @Override
    public void onStart() {
        super.onStart();
       // common.status ();
        if(ConnectivityReceiver.isConnected()) {
           // common.status ();
            common.appSettingData(new GetAppSettingData() {
                @Override
                public void getAppSettingData(AppSettingModel model) {

                    tv_admin.setText(Html.fromHtml(model.getHome_text ()));
                    if(common.checkNullString(model.getWhatsapp_no()) && common.checkNullString(model.getHome_text())){
                        if(card_home.getVisibility()==View.VISIBLE)
                            card_home.setVisibility(View.GONE);
                    }else{
                        if(card_home.getVisibility()==View.GONE)
                            card_home.setVisibility(View.VISIBLE);

                        if(common.checkNullString(model.getHome_text())){
                            tv_admin.setVisibility(View.GONE);
                        }else{
                            tv_admin.setVisibility(View.VISIBLE);
                            tv_admin.setText(Html.fromHtml(common.checkNull(model.getHome_text())));
                        }

//                        if(common.checkNullString(model.getWhatsapp_no())){
//                            rl_whatsapp.setVisibility(View.GONE);
//                        }
                         if(common.checkNullString(model.getWithdraw_no ())){
                            rl_whatsapp.setVisibility(View.GONE);
                        }else{
                            rl_whatsapp.setVisibility(View.VISIBLE);
    //                        wMsg=model.getWhatsapp_msg();
//                            wNo=model.getWhatsapp_no();
                             wMsg=model.getWhatsapp_msg ();
                             wNo=model.getWithdraw_no ();
                        }


                    }
//                text_number.setText (Html.fromHtml (model.getWhatsapp_no ()));

                    if(model.getStarline_status().equals("0")){
                        card_starline.setVisibility(View.VISIBLE);
                    }else{
                        card_starline.setVisibility(View.VISIBLE);
                        tv_starline.setText(model.getStarline_name());
                    }
                }
            });

            //makeSliderRequest();
            getMatkaData();
            common.getWalletAmount();
//            Log.e(TAG,""+session_management.getUserDetails().get(KEY_WALLET));
//        showUpdateDialog();
//        if (!dialog_image.isEmpty())
//        {
//            if(!session_management.isDialogStatus())
//            {
//                showImageDialog(dialog_image);
//                session_management.updateDilogStatus(true);
//            }
//
//        }

        } else
        {
            Intent intent = new Intent(getActivity(), NoInternetConnection.class);
            startActivity(intent);
        }

        rv_matka.setLayoutManager(new LinearLayoutManager(getActivity()));
        matkaAdapter = new NewMatkaAdpater(getActivity(),matkaList);
        rv_matka.setAdapter(matkaAdapter);
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version_code = pInfo.versionCode;
            Log.e(TAG,""+ver_code+" - "+version_code);
            // Toast.makeText(splash_activity.this,""+version,Toast.LENGTH_LONG).show();
            if(version_code==ver_code)
            {

            }
            else {
               // showUpdateDialog();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        ((MainActivity)getActivity()).setWalletCounter(session_management.getUserDetails().get(Constants.KEY_WALLET));

    }

    void showUpdateDialog()
    {
        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_version);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width= WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
       Button btnCancel = dialog.findViewById(R.id.cancel);
       Button btnUpdate = dialog.findViewById(R.id.update);
       btnCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
               getActivity().finishAffinity();

           }
       });
       btnUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String url = null;
if (app_link==null || app_link.isEmpty())
{
    url ="https://play.google.com/store?hl=en_IN";
}
else
{
    url = app_link;
}
               Log.e("äsdsd",""+url);
               Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
           }
       });
        dialog.show();
    }

    public void showImageDialog(String img)
    {

        final Dialog dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ImageView imageView=dialog.findViewById(R.id.dialog_img);
        ImageView img_close=dialog.findViewById(R.id.img_close);
        dialog.setCanceledOnTouchOutside(false);
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
        dialog.show();

        Glide.with(getActivity()).load(BaseUrls.IMG_DIALOG_URL+dialog_image).asBitmap().into(imageView);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.card_starline)
        {
            Fragment fm  = new StarlineFragment();

               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                       .addToBackStack(null).commit();
        }
        else if(v.getId() == R.id.rl_whatsapp){
            common.whatsapp(wNo,wMsg);
        }

    }
}
