package in.matka.rose.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.matka.rose.Common.Common;
import in.matka.rose.R;
import in.matka.rose.Util.LoadingBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenerateMpinFragment extends Fragment implements View.OnClickListener {
    LoadingBar progressDialog;
    private Dialog dialog;
    Common common;
    private TextView btn_back;
    private RelativeLayout cvGenPin,cvForPin;
    private Button dialog_btnMpin;

    EditText dialog_etEmail;

    public GenerateMpinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generate_mpin, container, false);
        initViews(view);
        return  view ;
    }
    public void initViews(View v)
    {
        common=new Common(getActivity());
        cvGenPin=v.findViewById(R.id.cvGenPin);
        cvForPin=v.findViewById(R.id.cvForPin);
        progressDialog=new LoadingBar(getActivity());
        cvForPin.setOnClickListener(this);
        cvGenPin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvGenPin) {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("Generate MPIN")
                    .setMessage("Please Press ok to generate MPIN")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String email = "";
//                            getMPINNumber(email);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

            builder.show();
        }
        else if (v.getId() == R.id.cvForPin)
        {
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setContentView(R.layout.dialog_foraget_mpin_layout);
            dialog_btnMpin=(Button)dialog.findViewById(R.id.forget_mpin);
            dialog_etEmail=(EditText)dialog.findViewById(R.id.etForget_email);

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            dialog_btnMpin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(TextUtils.isEmpty(dialog_etEmail.getText().toString()))
                    {
                        dialog_etEmail.setError("Enter registered Email Id");
                        dialog_etEmail.requestFocus();
                        return;
                    }
                    else
                    {
                        String mail=dialog_etEmail.getText().toString().trim();
//                        getForgotMpin(mail);
                    }

                }
            });

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        common.setSessionTimeOut(getActivity());
    }
//    private void getForgotMpin(final String mail) {
//
//
//        progressDialog.show();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_forgot_mpin,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try
//                        {
//                            JSONObject jsonObject=new JSONObject(response);
//                            String success=jsonObject.getString("status");
//                            if(success.equals("success"))
//                            {
//                                progressDialog.dismiss();
//                                Toast.makeText(getActivity(), "Mail sent to your email address!!!", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                            else if(success.equals("unsuccessful"))
//                            {
//                                progressDialog.dismiss();
//                                String msg=jsonObject.getString("message");
//                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                                return;
//                            }
//
//
//
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                            progressDialog.dismiss();
//                            Toast.makeText(getActivity(), "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                            //  btnReg.setVisibility(View.VISIBLE);
//
//
//                        }
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        progressDialog.dismiss();
//                        Toast.makeText(getActivity(), "Updation failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
//                        //  pb.setVisibility(View.GONE);
//
//                    }
//                }
//        )
//        {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//
//                params.put("email",mail);
//                // params.put("phonepay",phonepaynumber);
//
//
//                return params;
//            }
//
//        };
//        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//
//
//
//    }


//    private void getMPINNumber(final String email) {
//
//        progressDialog.show();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, BaseUrls.URL_MPIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try
//                        {
//                            JSONObject jsonObject=new JSONObject(response);
//                            String status=jsonObject.getString("status");
//
//                            if(status.equals("success"))
//                            {
//
//                                String mpin=jsonObject.getString("ans");
//                                progressDialog.dismiss();
//
//                                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
//                                alertDialog.setMessage(mpin)
//                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//
//                                                dialog.dismiss();
//
//                                            }
//                                        });
//                                alertDialog.show();
//                                // builDialog(DrawerGenMpinActivity.this,mpin);
//
//
//                            }
//                            else if(status.equals("unsuccessful"))
//                            {
//                                progressDialog.dismiss();
//                                String mpin=jsonObject.getString("ans");
//                                common.errorMessageDialog(""+mpin);
//                            }
//                            else
//                            {
//
//                                progressDialog.dismiss();
//                                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                        }
//                        catch (Exception ex)
//                        {
//                            progressDialog.dismiss();
//                            Toast.makeText(getActivity(),"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                    }
//                }
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(),"Error :"+error.getMessage(),Toast.LENGTH_LONG).show();
//                return;
//            }
//        })
//        {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//
//                params.put("email",email);
//
//
//
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//
//    }

}
