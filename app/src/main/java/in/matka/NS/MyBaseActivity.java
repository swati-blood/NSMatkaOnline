package in.matka.NS;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import in.matka.NS.Activity.LoginActivity;


public class MyBaseActivity extends Activity {

    public static final long DISCONNECT_TIMEOUT = 600000; // 30 sec = 30 * 1000 ms

    private android.os.Handler disconnectHandler = new Handler() {
        public void handleMessage(Message msg) {
        }
    };



    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {

            final Dialog dialog=new Dialog(MyBaseActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_error_message_dialog);
            TextView txtMessage=(TextView)dialog.findViewById(R.id.txtmessage);
            Button btnOk=(Button)dialog.findViewById(R.id.btnOK);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            txtMessage.setText("Session TimeOut");

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent1=new Intent(MyBaseActivity.this, LoginActivity.class);
                 //   dialog.dismiss();
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    dialog.cancel();

                }
            });
//
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
//                    MyBaseActivity.this);
//            alertDialog.setCancelable(false);
//            alertDialog.setTitle("Alert");
//            alertDialog
//                    .setMessage("Session Timeout");
//            alertDialog.setNegativeButton("OK",
//                    new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(MyBaseActivity.this,
//                                    MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//
//                            dialog.cancel();
//                        }
//                    });
//
//            alertDialog.show();

            // Perform any required operation on disconnect
        }
    };

    public void resetDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer() {
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction() {
       // resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }
}
