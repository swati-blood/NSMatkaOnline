package in.matka.ns.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import in.matka.ns.Fragments.MainFragment;
import in.matka.ns.Model.MatkasObjects;
import in.matka.ns.R;
import in.matka.ns.Util.Module;
import in.matka.ns.Util.ToastMsg;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 09,September,2020
 */
public class NewMatkaAdpater extends RecyclerView.Adapter<NewMatkaAdpater.ViewHolder> {
    private final String TAG= NewMatkaAdpater.class.getSimpleName();
    private Activity context;
    Module common ;
    private ArrayList<MatkasObjects> list;
    private int flag=0;
    Vibrator vibe;

    public NewMatkaAdpater(Activity context, ArrayList<MatkasObjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(context).inflate(R.layout.new_matka_layout,null);
         return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MatkasObjects model=list.get(position);
        holder.txtMatkaName.setText(model.getName());
        String startTime="";
        String endTime="";
        String dy=new SimpleDateFormat("EEEE").format(new Date());
//        String dy="Sunday";
        if(dy.equalsIgnoreCase("Sunday")){
          if(getValidTime(model.getStart_time().toString(),model.getEnd_time().toString())){
              startTime=model.getStart_time();
              endTime=model.getEnd_time();
          }else{
              startTime=model.getBid_start_time();
              endTime=model.getBid_end_time();
          }
        }else if(dy.equalsIgnoreCase("Saturday")){
            if(getValidTime(model.getSat_start_time().toString(),model.getSat_end_time().toString())){
                startTime=model.getSat_start_time();
                endTime=model.getSat_end_time();
            }else{
                startTime=model.getBid_start_time();
                endTime=model.getBid_end_time();
            }
        }else{
            startTime=model.getBid_start_time();
            endTime=model.getBid_end_time();
        }
        Log.e("matka_time", "onBindViewHolder: "+startTime+"\n "+endTime );
        holder.txtmatkaBid_openTime.setText(common.get24To12Format(startTime));
        holder.txtmatkaBid_closeTime.setText(common.get24To12Format(endTime));

            holder.txtMatka_startingNo.setText(getValidNumber(model.getStarting_num(),1)+"-"+getValidNumber(model.getNumber(),2)+"-"+getValidNumber(model.getEnd_num(),3));

        Date date=new Date();
        SimpleDateFormat sim=new SimpleDateFormat("HH:mm:ss");
        String time1 = startTime.toString();
        String time2 = endTime.toString();

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
      if(dy.equalsIgnoreCase("Sunday")){
          if(getValidTime(model.getStart_time().toString(),model.getEnd_time().toString())){
              getPlayButton(as,c,holder.txtStatus,holder.btnPlay);
          }else
          {
              holder.btnPlay.setVisibility(View.GONE);
          }
      }else if(dy.equalsIgnoreCase("Saturday")){
          if(getValidTime(model.getSat_start_time().toString(),model.getSat_end_time().toString())){
              getPlayButton(as,c,holder.txtStatus,holder.btnPlay);
          }else
          {
              holder.btnPlay.setVisibility(View.GONE);
          }
      }else{
          getPlayButton(as,c,holder.txtStatus,holder.btnPlay);
      }

        holder.rel_matka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dyClick=new SimpleDateFormat("EEEE").format(new Date());
//                String dyClick="Sunday";
                Log.e("asdaee",""+dyClick);
                String stime ="";
                String etime ="";
                int err=0;
                boolean is_error=false;
                if(dyClick.equalsIgnoreCase("Sunday"))
                {
                  if(getValidTime(model.getStart_time(),model.getEnd_time()))
                  {err=1;
                      stime=model.getStart_time().toString();
                      etime=model.getEnd_time().toString();
                      Log.e(TAG, "onClick: "+etime );

                  }else{
                      err=2;
                      is_error=true;
                  }


                }
                else if(dyClick.equalsIgnoreCase("Saturday"))
                {
                    if(getValidTime(model.getSat_start_time(),model.getSat_end_time()))
                    {
                        err=3;
                        stime=model.getSat_start_time().toString();
                        etime=model.getSat_end_time().toString();
                    }else{
                        err=4;
                        is_error=true;
                    }
                }
                else
                {
                    stime=model.getBid_start_time().toString();
                    etime=model.getBid_end_time().toString();
                }

                long endDiff=common.getTimeDifference(etime);
//              common.showToast(""+err);
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                if(endDiff<0 || is_error==true)
                {
                    new ToastMsg(context).toastIconError("Bid is Closed for today");
                }
                else
                {
                    Log.e(TAG, "onClick: "+stime+" == "+etime );
                    Bundle bundle = new Bundle();

                    bundle.putString("matka_name",model.getName());
                    bundle.putString("m_id",model.getId());
                    bundle.putString("start_number",model.getStarting_num());
                    bundle.putString("number",model.getNumber());
                    bundle.putString("end_number",model.getEnd_num());
                    bundle.putString("end_time",model.getBid_end_time());
                    bundle.putString("start_time",model.getBid_start_time());
                    Fragment fm  = new MainFragment();
                    fm.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                            .addToBackStack(null).commit();
                }

                // Toast.makeText(context,"Position"+m_id,Toast.LENGTH_LONG).show();

            }
        });
//        holder.rel_matka.setBackgroundColor(Color.parseColor(list.get(position).getColor_code()));

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
        Button btnPlay;
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
            btnPlay = itemView.findViewById(R.id.btnPlay);
            txtStatus=(TextView)itemView.findViewById(R.id.matkaBettingStatus);
            imageGame=(ImageView)itemView.findViewById(R.id.matka_image);
            txtMatka_id=(TextView) itemView.findViewById(R.id.matka_id);
            vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


        }
    }

    public boolean getValidTime(String sTime, String eTime){

        if(sTime.equalsIgnoreCase("00:00:00") && eTime.equalsIgnoreCase("00:00:00")){
          return false;
        }else if(sTime.equalsIgnoreCase("00:00:00.000000") && eTime.equalsIgnoreCase("00:00:00.000000")){
         return false;
        }else{
            return true;
        }
    }

    public String getValidNumber(String str, int palace){
        String validStr="";
        if(str ==null || str.isEmpty() || str.equalsIgnoreCase("null")){
            if(palace==1){
                validStr="***";
            }else if(palace==2){
                validStr="**";
            }else{
                validStr="***";
            }
        }else{
            validStr=str;
        }
        return validStr;
    }
public void getPlayButton(long as, long c, TextView tv_status, Button btnPlay){
    if(as<0)
    {
        flag=2;
       tv_status.setVisibility(View.VISIBLE);
        tv_status.setTextColor(Color.parseColor("#316D35"));
        tv_status.setText("Bidding is running for Today");
        btnPlay.setVisibility(View.GONE);

    }


    else if(c>0)
    {
        flag=3;
        tv_status.setTextColor(Color.parseColor("#FFA44546"));
        tv_status.setText("Bidding is close for today");
        tv_status.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.GONE);
    }
    else
    {
        flag=1;
        tv_status.setVisibility(View.GONE);
        btnPlay.setVisibility(View.GONE);
    }
}
}
