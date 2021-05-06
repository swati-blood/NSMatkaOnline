package in.matka.ns.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.matka.ns.Common.Common;
import in.matka.ns.Model.EarnCommisionModel;
import in.matka.ns.R;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 03,April,2021
 */
public class EarnCommisionAdapter extends RecyclerView.Adapter<EarnCommisionAdapter.ViewHolder>{
    ArrayList<EarnCommisionModel> list;
    Context context;
    Common common;

    public EarnCommisionAdapter(ArrayList<EarnCommisionModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_commision,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      EarnCommisionModel model=list.get(position);
      holder.tv_id.setText("#"+model.getId());
      holder.tv_name.setText(common.checkNull(model.getName()));
      holder.tv_date.setText(getFormattedDateTime(common.checkNull(model.getCreated_at())));
      holder.tv_amount.setText(common.checkNull(model.getRefer_amount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id,tv_date,tv_name,tv_amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_amount=itemView.findViewById(R.id.tv_amount);
            common=new Common(context);
        }
    }
    private String getFormattedDateTime(String str){
        String[] arr=str.split(" ");
        String time=common.get24To12Format(arr[1].toString());
        return (arr[0].toString()+" "+time);
    }
}
