package in.matka.ns.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import in.matka.ns.R;

public class LoadingBar {
    Context context;
    Dialog dialog;

    public LoadingBar(Context context) {
        this.context = context;
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.loading_layout);
        dialog.setCanceledOnTouchOutside(false);
    }
    public void show()
    {
        dialog.show();
    }

    public void dismiss()
    {
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }

    }

    public boolean isShowing()
    {
       return dialog.isShowing();
    }
}
