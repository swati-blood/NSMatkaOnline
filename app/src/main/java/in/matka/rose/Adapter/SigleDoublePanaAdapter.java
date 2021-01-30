package in.matka.rose.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.matka.rose.Common.Common;
import in.matka.rose.Model.TableModel;
import in.matka.rose.R;

import static in.matka.rose.Activity.PanaActivity.txt_type;


public class SigleDoublePanaAdapter extends RecyclerView.Adapter<SigleDoublePanaAdapter.ViewHolder> {
    List<String> digit_list ;
    Activity activity;
    Common common;
    ArrayList<TableModel> bidList;
  String game_name="";
    int tot = 0;
    int index =0 ;
    String beforeTextChangeValue="";
    int fragmentPosition=0;
    public static ArrayList<String> pannaList;
    public static Boolean is_empty = true , is_error = false ;
    public SigleDoublePanaAdapter(List<String> digit_list, Activity activity, int fragmentPosition,ArrayList<TableModel> bidList,String game_name) {
        this.digit_list = digit_list;
        this.activity = activity;
        this.fragmentPosition=fragmentPosition;
        this.bidList=bidList;
        this.game_name=game_name;
    }

    @NonNull
    @Override
    public SigleDoublePanaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(activity).inflate(R.layout.layout_new_digits,null);
      ViewHolder holder = new ViewHolder(view);
      return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull SigleDoublePanaAdapter.ViewHolder holder, final int position) {
        holder.txt_digits.setText(digit_list.get(position));

       int edtPosition=common.getIndexFromFragmentPosition(fragmentPosition,game_name)+position;
       Log.e("frag_no:- "+fragmentPosition," - "+edtPosition+" - "+common.getPointsOnIndex(bidList,edtPosition));
      if(!common.getPointsOnIndex(bidList,edtPosition).isEmpty())
      {
          holder.et_points.setText(common.getPointsOnIndex(bidList,edtPosition).toString());
      }
        holder.et_points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextChangeValue=s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txt_type.getText().toString().equalsIgnoreCase(activity.getResources().getString(R.string.select_type))) {
                    common.showToast("Select Bid Type");
                } else {
                    boolean backSpace = false;
                    if (beforeTextChangeValue.length() > s.toString().length()) {
                        backSpace = true;
                    }

                    if (backSpace) {
                        String pnts = s.toString();
//                        deleteFromList(pnts, position, beforeTextChangeValue);
                        updateintent("sub",String.valueOf(fragmentPosition),pnts,beforeTextChangeValue,txt_type.getText().toString(),String.valueOf(position));
                    } else {
                        String points = s.toString();
                        updateintent("add",String.valueOf(fragmentPosition),points,beforeTextChangeValue,txt_type.getText().toString(),String.valueOf(position));
//                        addToBetList(points, position);
                    }


                }
            }

        });
//
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

    private void updateintent(String type,String fragPos,String point,String beforeTextChangeValue,String betType,String pos) {
        Intent updates = new Intent("Points");
        updates.putExtra("type", type);
        updates.putExtra("fragment_position", fragPos);
        updates.putExtra("point",point);
        updates.putExtra("beforevalue",beforeTextChangeValue);
        updates.putExtra("bet_type",betType);
        updates.putExtra("position",pos);
        activity.sendBroadcast(updates);
    }
}
