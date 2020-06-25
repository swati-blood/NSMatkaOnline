package in.games.nidhimatka.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import static in.games.nidhimatka.Config.Constants.*;

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
                                   String dob,String wallet) {

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

        editor.commit();
    }
//
//    /**
//     * Get stored session data
//     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, prefs.getString(KEY_ID, null));
        user.put(KEY_NAME, prefs.getString(KEY_NAME, null));
        user.put(KEY_USER_NAME, prefs.getString(KEY_USER_NAME, null));
        user.put(KEY_MOBILE, prefs.getString(KEY_MOBILE, null));
        user.put(KEY_EMAIL, prefs.getString(KEY_EMAIL, null));
        user.put(KEY_ADDRESS, prefs.getString(KEY_ADDRESS, null));
        user.put(KEY_CITY, prefs.getString(KEY_CITY, null));
        user.put(KEY_PINCODE, prefs.getString(KEY_PINCODE, null));
        user.put(KEY_ACCOUNNO, prefs.getString(KEY_ACCOUNNO, ""));
        user.put(KEY_BANK_NAME, prefs.getString(KEY_BANK_NAME, null));
        user.put(KEY_IFSC, prefs.getString(KEY_IFSC, null));
        user.put(KEY_HOLDER, prefs.getString(KEY_HOLDER, null));
        user.put(KEY_PAYTM, prefs.getString(KEY_PAYTM, null));
        user.put(KEY_TEZ, prefs.getString(KEY_TEZ, null));
        user.put(KEY_PHONEPAY, prefs.getString(KEY_PHONEPAY, null));
        user.put(KEY_WALLET, prefs.getString(KEY_WALLET, null));
        user.put(KEY_DOB, prefs.getString(KEY_DOB, null));
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
    public void updatePaymentSection(String tez,String paytm,String phonepay)
    {
        editor.putString(KEY_TEZ, tez);
        editor.putString(KEY_PAYTM, paytm);
        editor.putString(KEY_PHONEPAY, phonepay);
        editor.apply();
    }
    public void updateEmailSection(String email,String dob)
    {
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_DOB, dob);
        editor.apply();
    }
    public void updateWallet(String wallet)
    {
        editor.putString(KEY_WALLET, wallet);
        editor.apply();
    }

//    public void logoutSession() {
//        editor.clear();
//        editor.commit();
//
//        cleardatetime();
//
//        Intent logout = new Intent(context, MainActivity.class);
//        // Closing all the Activities
//        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        context.startActivity(logout);
//    }
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
//    public void updateProfile(String image, String name, String cnt)
//    {
//        editor.putString(KEY_IMAGE,image);
//        editor.putString(KEY_NAME,name);
//        editor.putString(KEY_CNT,cnt);
//        editor.commit();
//    }
//
//    public HashMap<String, String> getUpdateProfile()
//    {
//        HashMap<String, String> map=new HashMap<>();
//        map.put(KEY_IMAGE,prefs.getString(KEY_IMAGE,null));
//        map.put(KEY_NAME,prefs.getString(KEY_NAME,null));
//        map.put(KEY_CNT,prefs.getString(KEY_CNT,null));
//        return map;
//    }
//
//    public void updateUserName(String name)
//    {
//        editor.putString(KEY_NAME,name);
//        editor.commit();
//    }
//    public void setCategoryId(String id)
//    {
//        editor.putString(KEY_CAT,id);
//        editor.commit();
//    }
//
//    public String getCategoryId()
//    {
//        return prefs.getString(KEY_CAT,"");
//    }

}