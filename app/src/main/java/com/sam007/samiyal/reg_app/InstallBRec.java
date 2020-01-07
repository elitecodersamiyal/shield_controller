package com.sam007.samiyal.reg_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InstallBRec extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        context.startService(new Intent(context, BackServ.class));

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
