package com.sam007.samiyal.reg_app;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sam007.samiyal.reg_app.sharedP.ShardePref;

public class Settings extends AppCompatActivity {

    private ToggleButton tbtn;
    private ImageButton backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tbtn = findViewById(R.id.t_btn);
        tbtn.setTextOff("Lock On");
        tbtn.setTextOn("Lock Off");
        String tt = tbtn.getTextOff().toString();

        ShardePref store = ShardePref.getInstance(getApplicationContext());
        Boolean l_state = store.getbool("lockedstate");

        backbtn = findViewById(R.id.back_btn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SideActivity.class));
            }
        });

        if(l_state == false) {
            tbtn.setChecked(false);
        }else {
            tbtn.setChecked(true);
        }


        tbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!tbtn.isChecked()) {
                    PackageManager pm = getApplicationContext().getPackageManager();
                    ComponentName componentName = new ComponentName(getApplicationContext(), BackRec.class);
                    pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

                    Toast.makeText(getApplicationContext(), "toggle btn now on", Toast.LENGTH_LONG).show();
                    Log.d("servTest","toggle btn is now on");


                    BackServ mService = new BackServ(getApplicationContext());
                    Intent mServiceIntent = new Intent(getApplicationContext(), mService.getClass());

                    startService(mServiceIntent);

                }else if(tbtn.isChecked()) {
                    PackageManager pm = getApplicationContext().getPackageManager();
                    ComponentName componentName = new ComponentName(getApplicationContext(), BackRec.class);
                    pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);

                    Toast.makeText(getApplicationContext(), "toggle btn is now off", Toast.LENGTH_LONG).show();
                    Log.d("servTest","toggle btn is  now off");


                    BackServ mService = new BackServ(getApplicationContext());
                    Intent mServiceIntent = new Intent(getApplicationContext(), mService.getClass());

                    stopService(mServiceIntent);

                }
                if(!isMyServiceRunning(BackServ.class)) {
                    Log.d("servTest","service is stop");
                }else {
                    Log.d("servTest","service is stop");
                }
            }
        });

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
