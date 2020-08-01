package in.games.nidhimatka.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import in.games.nidhimatka.R;


public class ToastMsg {

    Context context;
  LayoutInflater layoutInflater;
    public ToastMsg(Context context) {
        this.context = context;
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void toastIconError(String s)
    {
        Toast toast=new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        View view=layoutInflater.inflate(R.layout.toast_icon_text,null);
        ((TextView)view.findViewById(R.id.message)).setText(s);
        ((ImageView) view.findViewById(R.id.icon)).setImageResource(R.drawable.icons8_error_96px);
        ((CardView) view.findViewById(R.id.parent_view)).setCardBackgroundColor(context.getResources().getColor(R.color.error));
        toast.setView(view);
        toast.show();

    }
    public void toastInfo(String s)
    {
        Toast toast=new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        View view=layoutInflater.inflate(R.layout.toast_icon_text,null);
        ((TextView)view.findViewById(R.id.message)).setText(s);
        ((ImageView) view.findViewById(R.id.icon)).setImageResource(R.drawable.icons8_medium_priority_52px);
        ((CardView) view.findViewById(R.id.parent_view)).setCardBackgroundColor(context.getResources().getColor(R.color.info));
        toast.setView(view);
        toast.show();

    }

    public void toastIconSuccess(String s) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = layoutInflater.inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText(s);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.icons8_ok_96px);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(context.getResources().getColor(R.color.succes));

        toast.setView(custom_view);
        toast.show();
    }

}
