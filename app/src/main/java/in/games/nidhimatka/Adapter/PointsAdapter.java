package in.games.nidhimatka.Adapter;

import android.app.Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.games.nidhimatka.Model.TableModel;
import in.games.nidhimatka.R;

import static in.games.nidhimatka.Fragments.PanaFragment.bet_list;
import static in.games.nidhimatka.Fragments.PanaFragment.total;
import static in.games.nidhimatka.Fragments.PanaFragment.txt_type;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

  public static  ArrayList<TableModel> b_list ;
 List<String> digit_list ;
    Activity activity;

    TextView tv_total;
    int tot = 0;
    int index =0 ;

public static Boolean is_empty = true , is_error = false ;

    public PointsAdapter(List<String> digit_list, Activity activity ,TextView tv_total) {
        this.digit_list = digit_list;
        this.activity = activity;

        this.tv_total = tv_total;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_new_digits,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.txt_digits.setText(digit_list.get(i));


        viewHolder.et_points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String points = s.toString();
               int p = Integer.parseInt(points);

                if (s.length() != 0) {

                    if (points.isEmpty()) {
                        is_empty = true;
                    } else {
                        is_empty = false;
                        int pints = Integer.parseInt(points);
                        if (pints < 10) {
//                        viewHolder.et_points.setError("Minimum bid points is 10");
//                        viewHolder.et_points.requestFocus();
                            is_error = true;

                        }

                        else {
                            is_empty = false;
                            is_error = false;
                            int ps = Integer.parseInt(points);
                            tot = tot + ps;
                            total.setText(String.valueOf(tot));
                                if (txt_type.getText().toString().equals(activity.getResources().getString(R.string.select_type)))
                                {
//                                    Toast.makeText(activity, "Select game type", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    bet_list.add(new TableModel(digit_list.get(i), points, txt_type.getText().toString()));



                                }
                        }
                    }
                }
//                else if (s.length()==0)
//                {
//                  String d =  digit_list.get(i);
//                    Toast.makeText(activity, "digit"+d, Toast.LENGTH_LONG).show();
//
//                    if (tot>0) {
//                        tot = tot - p;
//                        tv_total.setText(String.valueOf(tot));
//                    }
//
//                }
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
            txt_digits = itemView.findViewById(R.id.txt_digit);
            et_points = itemView.findViewById(R.id.et_points);

         b_list = new ArrayList<>();
        }
    }

    public static void resetControls()
    {

    }
    public static ArrayList<TableModel> getBetlist()
    {
        return b_list;
    }
}