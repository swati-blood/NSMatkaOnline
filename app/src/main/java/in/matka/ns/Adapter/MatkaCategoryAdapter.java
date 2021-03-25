package in.matka.ns.Adapter;

import android.app.Dialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.matka.ns.Model.MatkasObjects;
import in.matka.ns.R;

public class MatkaCategoryAdapter extends RecyclerView.Adapter<MatkaCategoryAdapter.ViewHolder> {

    private Context context;
    private Dialog dialog;
    private ArrayList<MatkasObjects> list;

    public MatkaCategoryAdapter(Context context, ArrayList<MatkasObjects> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        final View view= LayoutInflater.from(context).inflate(R.layout.category_matka_layout,null);

        final ViewHolder viewHolder=new ViewHolder(view);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String matka_id=viewHolder.txtId.getText().toString().trim();
//                Intent intent=new Intent(context, BidActivity.class);
//                intent.putExtra("matka_id",matka_id);
////                CustomIntent.customType(context,"top-to-bottom");
//                context.startActivity(intent);

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        MatkasObjects postion=list.get(i);

        viewHolder.txtId.setText(postion.getId());
        viewHolder.txtName.setText(postion.getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtId,txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId=(TextView)itemView.findViewById(R.id.txtId);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
        }
    }
}
