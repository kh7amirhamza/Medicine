package com.example.medicine.InternetConnection;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class ConnectivityDetection extends BroadcastReceiver {
    CustomDialog customDialog;
    Activity activity;

    public ConnectivityDetection(Activity activity){
        this.activity = activity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY,false
            );

            if (noConnectivity){
            if (customDialog==null){
                customDialog = new CustomDialog(context);
                customDialog.setCancelable(false);
                customDialog.show();
            }
           // Toast.makeText(context,"Disconnected",Toast.LENGTH_SHORT).show();
                }
            else {
                if (customDialog!=null){
                    customDialog.dismiss();
                    customDialog.cancel();

                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    activity.startActivity(activity.getIntent().putExtra("checkedFragment","History"));
                    activity.overridePendingTransition(0, 0);

                }

               // Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
