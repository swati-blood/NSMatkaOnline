package in.games.nidhimatka.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.R;

public class SigleDoublePanaAdapter extends RecyclerView.Adapter<SigleDoublePanaAdapter.ViewHolder> {
    List<String> digit_list ;
    Activity activity;
    Common common;
    public static ArrayList<String> pannaList;
    public SigleDoublePanaAdapter(List<String> digit_list, Activity activity) {
        this.digit_list = digit_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SigleDoublePanaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(activity).inflate(R.layout.layout_new_digits,null);
      ViewHolder holder = new ViewHolder(view);
      return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull SigleDoublePanaAdapter.ViewHolder holder, int position) {
        holder.txt_digits.setText(digit_list.get(position));
    }

    @Override
    public int getItemCount() {
        return digit_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_digits ;
        EditText et_points;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pannaList=new ArrayList<>();
            txt_digits = itemView.findViewById(R.id.txt_digit);
            et_points = itemView.findViewById(R.id.et_points);

            common=new Common(activity);
        }
    }
}
