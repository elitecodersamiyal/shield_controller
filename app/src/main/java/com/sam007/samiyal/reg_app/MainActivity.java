package com.sam007.samiyal.reg_app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sam007.samiyal.reg_app.sharedP.ShardePref;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //private FirebaseAuth fa;
    Context ctx;
    Intent mServiceIntent;
    private BackServ mService;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* ===========================================================================*/
        if(!isMyServiceRunning(BackServ.class)) {
            ctx = this;
            setContentView(R.layout.activity_main);
            mService = new BackServ(getCtx());
            mServiceIntent = new Intent(getCtx(), mService.getClass());

            ShardePref store = ShardePref.getInstance(getApplicationContext());
            store.putbool("lockedstate",false);

            startService(mServiceIntent);
            Log.d("servTest","service started ");
        }else {
            Log.d("servTest","service is already running");
        }
        /*=============================================================================*/
        /*if (!isMyServiceRunning(mService.getClass())) {
        }*/

        //Button logout = findViewById(R.id.button);
        //fa = FirebaseAuth.getInstance();


        //String uName = fa.getCurrentUser().getEmail();
        /*
        if() {

            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }else {*/
        //String temp = "Hello " + uName;
        //Toast.makeText(getApplicationContext(),uName,Toast.LENGTH_SHORT).show();

        /* ------------ new code -----------
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        }, 100);
        */
        /* --------- using java Timer ------------  */
/*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                //startActivity(new Intent(getApplicationContext(), SideActivity.class));
            }
        }, 2000);
/*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
        /* ------------ old code -------------
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });*/

        //}

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
