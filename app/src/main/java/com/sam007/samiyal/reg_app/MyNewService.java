package com.sam007.samiyal.reg_app;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

public class MyNewService extends Service {
    public MyNewService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyForeground();
        else{
            //startForeground(1, new Notification());
        }

    }

    private void startMyForeground() {



    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
