package in.matka.ns.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import in.matka.ns.Activity.LoginActivity;

import static in.matka.ns.Config.Constants.*;

public class Session_management {

    SharedPreferences prefs;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    public Session_management(Context context) {

        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();


    }

    public void createLoginSession(String id, String name, String username
            , String mobile, String email, String address, String city, String pincode, String accountno,
                                   String bank_name, String ifsc, String holder,String paytm,String tez,String phonepay,
                                   String dob,String wallet,String gender,String upi) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_PINCODE, pincode);
        editor.putString(KEY_ACCOUNNO, accountno);
        editor.putString(KEY_BANK_NAME, bank_name);
        editor.putString(KEY_IFSC, ifsc);
        editor.putString(KEY_HOLDER, holder);
        editor.putString(KEY_PAYTM, paytm);
        editor.putString(KEY_TEZ, tez);
        editor.putString(KEY_PHONEPAY, phonepay);
        editor.putString(KEY_DOB, dob);
        editor.putString(KEY_WALLET, wallet);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_UPI,upi);
        editor.putBoolean(KEY_DIALOG, false);

        editor.commit();
    }
//
//    /**
//     * Get stored session data
//     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, prefs.getString(KEY_ID, ""));
        user.put(KEY_NAME, prefs.getString(KEY_NAME, ""));
        user.put(KEY_USER_NAME, prefs.getString(KEY_USER_NAME, ""));
        user.put(KEY_MOBILE, prefs.getString(KEY_MOBILE, ""));
        user.put(KEY_EMAIL, prefs.getString(KEY_EMAIL, ""));
        user.put(KEY_ADDRESS, prefs.getString(KEY_ADDRESS, ""));
        user.put(KEY_CITY, prefs.getString(KEY_CITY, ""));
        user.put(KEY_PINCODE, prefs.getString(KEY_PINCODE, ""));
        user.put(KEY_ACCOUNNO, prefs.getString(KEY_ACCOUNNO, ""));
        user.put(KEY_BANK_NAME, prefs.getString(KEY_BANK_NAME, ""));
        user.put(KEY_IFSC, prefs.getString(KEY_IFSC, ""));
        user.put(KEY_HOLDER, prefs.getString(KEY_HOLDER, ""));
        user.put(KEY_PAYTM, prefs.getString(KEY_PAYTM, ""));
        user.put(KEY_TEZ, prefs.getString(KEY_TEZ, ""));
        user.put(KEY_PHONEPAY, prefs.getString(KEY_PHONEPAY, ""));
        user.put(KEY_WALLET, prefs.getString(KEY_WALLET, ""));
        user.put(KEY_DOB, prefs.getString(KEY_DOB, ""));
        user.put(KEY_GENDER, prefs.getString(KEY_GENDER, ""));
        user.put(KEY_UPI, prefs.getString(KEY_UPI,""));

        return user;
    }

    public void updateAccSection(String acc_no,String bank_name,String ifsc,String holder)
    {
        editor.putString(KEY_ACCOUNNO, acc_no);
        editor.putString(KEY_BANK_NAME, bank_name);
        editor.putString(KEY_IFSC, ifsc);
        editor.putString(KEY_HOLDER, holder);
        editor.apply();
    }
    public void updateAddressSection(String address,String city,String pincode)
    {
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_PINCODE, pincode);
        editor.apply();
    }
    public void updatePaymentSection(String upi, String tez,String paytm,String phonepay)
    {
        editor.putString(KEY_UPI,upi);
        editor.putString(KEY_TEZ, tez);
        editor.putString(KEY_PAYTM, paytm);
        editor.putString(KEY_PHONEPAY, phonepay);
        editor.apply();
    }
    public void updateEmailSection(String email,String dob,String gender,String name)
    {
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_DOB, dob);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }
    public void updateWallet(String wallet)
    {
        editor.putString(KEY_WALLET, wallet);
        editor.apply();
    }
    public void updateDilogStatus(boolean flag)
    {
        editor.putBoolean(KEY_DIALOG, flag);
        editor.apply();
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
        Intent logout = new Intent(context, LoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(logout);
    }
//
//    public void logoutSessionwithchangepassword() {
//        editor.clear();
//        editor.commit();
//
//        cleardatetime();
//
//        Intent logout = new Intent(context, LoginActivity.class);
//        // Closing all the Activities
//        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        context.startActivity(logout);
//    }
//
//    public void creatdatetime(String date, String time) {
//        editor2.putString(KEY_DATE, date);
//        editor2.putString(KEY_TIME, time);
//
//        editor2.commit();
//    }
//
//    public void cleardatetime() {
//        editor2.clear();
//        editor2.commit();
//    }
//
//    public HashMap<String, String> getdatetime() {
//        HashMap<String, String> user = new HashMap<String, String>();
//
//        user.put(KEY_DATE, prefs2.getString(KEY_DATE, null));
//        user.put(KEY_TIME, prefs2.getString(KEY_TIME, null));
//
//        return user;
//    }
//
//    // Get Login State
    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGIN, false);
    }
    public boolean isDialogStatus() {
        return prefs.getBoolean(KEY_DIALOG, false);
    }



}
