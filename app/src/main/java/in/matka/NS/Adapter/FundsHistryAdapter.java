package in.matka.NS.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.matka.NS.Model.RequestModel;
import in.matka.NS.R;

public class FundsHistryAdapter extends RecyclerView.Adapter<FundsHistryAdapter.ViewHolder> {
    ArrayList<RequestModel> list ;
    Activity activity ;

    public FundsHistryAdapter(ArrayList<RequestModel> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(activity).inflate(R.layout.row_fundshistry,null);
       ViewHolder holder = new ViewHolder(view);
       return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestModel model = list.get(position);
        String req_time = model.getTime();
        String d = req_time.substring(0,10);
        String dd[] =d.split("-");
        holder.tv_date.setText(dd[2]+"/"+dd[1]+"/"+dd[0]);

        String t = req_time.substring(11,req_time.length());
        SimpleDateFormat tformat = new SimpleDateFormat("hh:mm:ss");
        try {
            Date tt = tformat.parse(t);
            SimpleDateFormat f = new SimpleDateFormat("hh:mm a");
            String ttt = f.format(tt);
            holder.tv_time.setText(ttt);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.tv_amount.setText(activity.getResources().getString(R.string.currency)+""+model.getRequest_points());
        if (model.getType().equals("Add")) {

            holder.tv_amount.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            holder.tv_type.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            holder.tv_type.setText("Points Added");
        }
        else
        {
            holder.tv_amount.setTextColor(activity.getResources().getColor(R.color.redColor));
            holder.tv_type.setTextColor(activity.getResources().getColor(R.color.redColor));
            holder.tv_type.setText("Points Withdrawn");
        }

        if (model.getRequest_status().equals("pending"))
        {
            holder.tv_status.setText("Status:\nPending" );
        }
        else
        {
            holder.tv_status.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_amount ,tv_type,tv_matka,tv_game ,tv_date,tv_time,tv_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_amount = itemView.findViewById(R.id.amount);
            tv_time = itemView.findViewById(R.id.time);
            tv_type = itemView.findViewById(R.id.type);
            tv_matka = itemView.findViewById(R.id.matka_name);
            tv_game = itemView.findViewById(R.id.game_name);
            tv_date = itemView.findViewById(R.id.date);
            tv_status = itemView.findViewById(R.id.status);
        }
    }
}
