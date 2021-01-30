package in.matka.rose.Adapter;

import android.app.Dialog;
import android.content.Context;

import android.graphics.Color;

import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.matka.rose.Common.Common;
import in.matka.rose.Fragments.MainFragment;
import in.matka.rose.Model.MatkasObjects;
import in.matka.rose.R;
import in.matka.rose.Util.ToastMsg;

public class MatkaAdapter extends RecyclerView.Adapter<MatkaAdapter.ViewHolder> {

    private Context context;
    private Dialog dialog;
    Common common ;
    private ArrayList<MatkasObjects> list;
    private String m_id;
    private int flag=0;
    Vibrator vibe;
    public MatkaAdapter(Context context, ArrayList<MatkasObjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {

       final View view= LayoutInflater.from(context).inflate(R.layout.new_matka_layout,null);

       final ViewHolder viewHolder=new ViewHolder(view);


       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // Toast.makeText(context,"Position"+viewHolder.getAdapterPosition(),Toast.LENGTH_LONG).show();
              // int p=viewHolder.getAdapterPosition();
               String st=viewHolder.txtStatus.getText().toString();
               String m_id=viewHolder.txtMatka_id.getText().toString().trim();
               if(flag==1)
               {
                   // Toast.makeText(context,"Position"+m_id,Toast.LENGTH_LONG).show();
//                   Intent intent=new Intent(context, GameActivity.class);
//                   intent.putExtra("tim",viewHolder.getAdapterPosition());
//                   intent.putExtra("matkaName",viewHolder.txtMatkaName.getText().toString());
//                   intent.putExtra("m_id",m_id);
//                   intent.putExtra("bet","cb");
//                   context.startActivity(intent);
               }
              else if(flag==2)
               {
//                   Intent intent=new Intent(context, GameActivity.class);
//                   intent.putExtra("tim",viewHolder.getAdapterPosition());
//                   intent.putExtra("matkaName",viewHolder.txtMatkaName.getText().toString());
//                   intent.putExtra("m_id",m_id);
//                   intent.putExtra("bet","ocb");
//                   context.startActivity(intent);
               }

               else if(flag==3)
               {
//                   Intent intent=new Intent(context, GameActivity.class);
//                   intent.putExtra("tim",viewHolder.getAdapterPosition());
//                   intent.putExtra("matkaName",viewHolder.txtMatkaName.getText().toString());
//                   intent.putExtra("m_id",m_id);
//                   intent.putExtra("bet","c");
//                   context.startActivity(intent);
               }

           }
       });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        MatkasObjects postion=list.get(i);

        viewHolder.txtMatkaName.setText(postion.getName());
        String s_time=null;
        String e_time=null;
        String s=postion.getBid_start_time().toString();
        String e=postion.getBid_end_time().toString();
        try {
            Date date=new SimpleDateFormat("HH:mm:ss").parse(s);
            Date date1=new SimpleDateFormat("HH:mm:ss").parse(e);
             s_time=new SimpleDateFormat("h:mm a").format(date);
             e_time=new SimpleDateFormat("h:mm a").format(date1);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }



        viewHolder.txtmatkaBid_openTime.setText(s_time.toString());
       // viewHolder.txtmatkaBid_openTime.setText(stat_time.toString());
        //viewHolder.txtmatkaBid_closeTime.setText(postion.getBid_end_time());
        viewHolder.txtmatkaBid_closeTime.setText(e_time.toString());
        viewHolder.txtMatka_startingNo.setText(postion.getStarting_num());
        viewHolder.txtMatka_resNo.setText(postion.getNumber());
        String end_number=postion.getEnd_num().toString();

        if(TextUtils.isEmpty(end_number))
        {
            viewHolder.txtMatka_endNo.setText("a");
        }
        else if(end_number.equals("null"))
        {
            viewHolder.txtMatka_endNo.setText("");

        }
        else
        {
            viewHolder.txtMatka_endNo.setText(end_number);
        }

        if(postion.getLoader().equals("1")){
            viewHolder.rlNum.setVisibility(View.GONE);
            viewHolder.tv_loader.setVisibility(View.VISIBLE);
        }else{

            viewHolder.rlNum.setVisibility(View.VISIBLE);
            viewHolder.tv_loader.setVisibility(View.GONE);
        }
        viewHolder.tv_text.setText(common.checkNull(postion.getText()));
        if(postion.getText_status().equals("1")){
            viewHolder.tv_text.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tv_text.setVisibility(View.GONE);
        }
        viewHolder.txtMatka_id.setText(postion.getId());
        String status=postion.getStatus();

        Date date=new Date();
        SimpleDateFormat sim=new SimpleDateFormat("HH:mm:ss");
        String ct=sim.format(date);

        String time1 = s.toString();
        String time2 = e.toString();

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

        long current_time=date3.getTime();
        long cur=(current_time/1000)/60;
       // viewHolder.txtStatus.setText("cu "+cur+"\n o  "+as+"\n c "+c);
        if(as<0)
        {
                flag=2;
                viewHolder.txtStatus.setTextColor(Color.parseColor("#316D35"));
                viewHolder.txtStatus.setText("Bidding is running for Today");




        }


        else if(c>0)
        {
            flag=3;
            viewHolder.txtStatus.setTextColor(Color.parseColor("#FFA44546"));
            viewHolder.txtStatus.setText("Bidding is close for today");
        }
        else
        {
            flag=1;
           // viewHolder.txtStatus.setText("a");
            viewHolder.txtStatus.setVisibility(View.INVISIBLE);
        }
//
        viewHolder.imageGame.setImageResource(R.drawable.circled_play);

      viewHolder.rel_matka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(100);
                String dy=new SimpleDateFormat("EEEE").format(new Date());
                Log.e("asda",""+dy);
                MatkasObjects objects=list.get(i);
                String stime ="";
                String etime ="";
                if(dy.equalsIgnoreCase("Sunday"))
                {
                    stime=objects.getStart_time().toString();
                    etime=objects.getEnd_time().toString();
                }
                else if(dy.equalsIgnoreCase("Saturday"))
                {
                    stime=objects.getSat_start_time().toString();
                    etime=objects.getSat_end_time().toString();
                }
                else
                {
                    stime=objects.getBid_start_time().toString();
                    etime=objects.getBid_end_time().toString();
                }

                long endDiff=common.getTimeDifference(etime);
                Log.e("time_differrrr"," - e : "+common.getTimeDifference(etime)+" - "+objects.getName()+" -- "+etime.toString());
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                String cur_time = format.format(date);

                String m_id=objects.getId().toString().trim();
                String matka_name=objects.getName().toString().trim();

//
//                else
//                {
                    if(endDiff<0)
                    {
//                 common.errorMessageDialog("BID IS CLOSED");
                       new ToastMsg(context).toastInfo("Bid is Closed for today");
                    }

               Bundle bundle = new Bundle();

               bundle.putString("matka_name",objects.getName());
               bundle.putString("m_id",objects.getId());
               bundle.putString("start_number",objects.getStarting_num());
               bundle.putString("number",objects.getNumber());
               bundle.putString("end_number",objects.getEnd_num());
               bundle.putString("end_time",objects.getBid_end_time());
               bundle.putString("start_time",objects.getBid_start_time());
               Fragment fm  = new MainFragment();
               fm.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();

               FragmentManager fragmentManager = activity.getSupportFragmentManager();
               fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                       .addToBackStack(null).commit();
//                }
                // Toast.makeText(context,"Position"+m_id,Toast.LENGTH_LONG).show();

            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtmatkaBid_openTime,txtmatkaBid_closeTime,
                txtMatkaName,txtMatka_startingNo,txtStatus,txtMatka_resNo,txtMatka_endNo,tv_text,tv_loader;
        TextView txtMatka_id;
        ImageView imageGame;
        RelativeLayout rlNum;
        RelativeLayout rel_matka;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmatkaBid_openTime=(TextView)itemView.findViewById(R.id.matka_open_bid_Time);
            txtmatkaBid_closeTime=(TextView)itemView.findViewById(R.id.matka_close_bid_Time);
            txtMatkaName=(TextView)itemView.findViewById(R.id.matkaName);
            txtMatka_startingNo=(TextView)itemView.findViewById(R.id.matka_starting_Number);
            txtMatka_resNo=(TextView)itemView.findViewById(R.id.matka_res_Number);
            txtMatka_endNo=(TextView)itemView.findViewById(R.id.matka_end_Number);
            rel_matka = itemView.findViewById(R.id.rlchange);
            rlNum = itemView.findViewById(R.id.rlNum);
            tv_loader = itemView.findViewById(R.id.tv_loader);
            tv_text = itemView.findViewById(R.id.tv_text);
            txtStatus=(TextView)itemView.findViewById(R.id.matkaBettingStatus);
            imageGame=(ImageView)itemView.findViewById(R.id.matka_image);
            txtMatka_id=(TextView) itemView.findViewById(R.id.matka_id);
            vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            common = new Common(context);

        }
    }


}
