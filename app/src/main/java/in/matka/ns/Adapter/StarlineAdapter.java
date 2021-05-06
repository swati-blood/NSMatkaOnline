package in.matka.ns.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.matka.ns.Model.Starline_Objects;
import in.matka.ns.R;
import in.matka.ns.Util.ToastMsg;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 14,April,2021
 */
public class StarlineAdapter extends RecyclerView.Adapter<StarlineAdapter.ViewHolder> {
    Activity activity;
    private ArrayList<Starline_Objects> list;

    public StarlineAdapter(Activity activity, ArrayList<Starline_Objects> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.play_games_items_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Starline_Objects postion=list.get(position);
        Date date=new Date();
        SimpleDateFormat format1=new SimpleDateFormat("hh:mm aa");
        String dr=format1.format(date);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH");
        String ddt=simpleDateFormat.format(date);
        int c_tm= Integer.parseInt(ddt);
        holder.txtTime.setText(postion.getS_game_time());

        String tm=getCloseStatus(postion.getS_game_end_time().toString(),dr);
        String[] end_time=tm.split(":");
        int h= Integer.parseInt(end_time[0].toString());
        int m= Integer.parseInt(end_time[1].toString());
        Log.e("starline", "getView: "+h+"  : "+m );
        if(h<=0 && m<0){
            holder.txtId.setText("Bid Is Running ");
            holder.txtNumber.setText("***-**");
            holder.txtId.setTextColor(Color.parseColor("#053004"));
        }
        else{
            holder.txtId.setText("Bid is Closed ");
            holder.txtId.setTextColor(Color.parseColor("#b31109"));
            holder.txtNumber.setText(""+postion.getS_game_number());

            holder.img.setImageAlpha(88);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumber,txtTime,txtId;
        ImageView img;
        RelativeLayout rl_change;
        public ViewHolder(@NonNull View view) {
            super(view);
             txtNumber=(TextView)view.findViewById(R.id.pg_Number);
             txtTime=(TextView)view.findViewById(R.id.pg_Time);
             txtId=(TextView)view.findViewById(R.id.pg_title);
             img=(ImageView)view.findViewById(R.id.pg_image);
             rl_change=(RelativeLayout)view.findViewById(R.id.rlchange);
        }
    }

    public String getCloseStatus(String gm_time, String current_time)
    {
        int h=0;
        int m=0;
        try {
            int days=0,hours=0,min=0;

            Date date1=new Date();
            Date date2=new Date();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
            boolean st_time=getStatusTime(current_time);
            if(st_time)
            {
                date1 = simpleDateFormat.parse(formatTime(gm_time));
                date2 = simpleDateFormat.parse(current_time);

            }
            else
            {
                date1 = simpleDateFormat.parse(gm_time);
                date2 = simpleDateFormat.parse(current_time);

            }

            long difference = date2.getTime() - date1.getTime();
            days = (int) (difference / (1000*60*60*24));
            hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

//        hours = (hours < 0 ? -hours : hours);
//        min = (min < 0 ? -min : min);
            h=hours;
            m=min;
            Log.i("======= Hours"," :: "+hours);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            new ToastMsg(activity).toastIconError("Something went Wrong");
//            Toast.makeText(context,"err :--  "+ex.getMessage()+"\n "+gm_time+"\n "+current_time, Toast.LENGTH_LONG).show();
        }
        String d=""+h+":"+m;
        return String.valueOf(d);
    }

    public boolean getStatusTime(String current_tim)
    {
        boolean st=false;
        String t[]=current_tim.split(" ");
        String time_type=t[1].toString();
        if(time_type.equals("a.m.") || time_type.equals("p.m."))
        {
            st=true;
        }
        else if(time_type.equals("AM") || time_type.equals("PM"))
        {
            st=false;
        }
        return st;
    }

    public String formatTime(String time)
    {
        String tm="";
        String t[]=time.split(" ");
        String time_type=t[1].toString();

        if(time_type.equals("PM"))
        {
            tm="p.m.";
        }
        else if(time_type.equals("AM"))
        {
            tm="a.m.";
        }
        else
        {
            tm=time_type;
        }

        String c_tm=t[0].toString()+" "+tm;
        return c_tm;
    }
}
