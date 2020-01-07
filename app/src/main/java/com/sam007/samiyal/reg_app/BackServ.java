package com.sam007.samiyal.reg_app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sam007.samiyal.reg_app.sharedP.ShardePref;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class BackServ extends Service {

    FirebaseDatabase fb;
    DatabaseReference mRef;

    Boolean unlock = false;

    public int counter=0;
    public String aName = "",newapp = "",lastapp = "";
    private Dialog dialog;
    private Context context = null;
    private WindowManager windowManager;
    public ArrayList<String> toplist = new ArrayList<>();



    /* ------------------------------- code for timer ----------------------------------- */

    private Timer timer =  new Timer();

    public TimerTask tt = new TimerTask() {
        @Override
        public void run() {
            getAppName();
            Log.d("timer","my timer on");
        }
    };

    /* ------------------------------- ***************** ----------------------------------- */

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d("servTest","service startCommand");
        fb = FirebaseDatabase.getInstance();
        DatabaseReference mRef = fb.getReference();

        List<String> apppack = (List<String>) mRef.child("LockedApps");

        //startTimer();

        if(timer != null) {
            timer.schedule(tt, 1000, 1000); // the old code
        }
        return START_STICKY;
    }

    /* ------------------------------- code for stop timer ----------------------------------- */

    public void stoptimertask() {
        Log.d("timer"," ----------- service -------------");
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            //aName = lastapp;

            Log.d("timer"," ---------- timer off ------------");
        }
    }

    /* ------------------------------- ******************** ----------------------------------- */

    public BackServ(Context applicationContext) {
        super();
        context = applicationContext;
        Log.d("HERE","here i am!");
        Log.d("time","here i am! constructor");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Log.d("chkapps","out now");
        Log.d("timer","out now");

        Intent broadcastIntent = new Intent(this, BackRec.class);
        Log.d("servTest","service destroyed");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    public void getTopActivtyFromLolipopOnwards() {
        String topPackageName;
        Log.e("GOBI", "fun call");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("GOBI", "ver pass");
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            // We get usage stats for the last 10 seconds
            List < UsageStats > stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
            // Sort the stats by the last time used
            if (stats != null) {
                SortedMap < Long, UsageStats > mySortedMap = new TreeMap < Long, UsageStats > ();
                for (UsageStats usageStats: stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    Log.e("GOBI", " app -> " + topPackageName);
                }else { Log.e("GOBI", "  ----- else ---"); }
            }
        }
        Log.e("GOBI", "fun call end");
    }

    public Boolean getAppName() {


        String TAG = "GOBI";


        ActivityManager activityManager = (ActivityManager)    getApplicationContext().getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task = activityManager.getRunningTasks(5);

        if (Build.VERSION.SDK_INT <= 20) {
            if (task.size() > 0) {
                ComponentName componentInfo = task.get(0).topActivity;

                Log.d(TAG, " --> 1 .:->" + componentInfo.getClassName());
            }
        } else {
            String mpackageName = activityManager.getRunningAppProcesses().get(0).processName;
            UsageStatsManager usage = (UsageStatsManager) getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);

            long time = System.currentTimeMillis();

            List<UsageStats> stats = usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, time);
            if (stats != null) {
                SortedMap<Long, UsageStats> runningTask = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : stats) {
                    runningTask.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (runningTask.isEmpty()) {
                    Log.d(TAG,"isEmpty Yes");
                    mpackageName = "";
                }else {
                    mpackageName = runningTask.get(runningTask.lastKey()).getPackageName();
                    Log.d(TAG,"isEmpty No : "+mpackageName);
                }
            }

            final String pn = mpackageName;

            allowapp(pn);
            newapp = pn;

        }

        return true;
    }

    public  void allowapp(final String cApp) {
        final Boolean flag = false;
        mRef = FirebaseDatabase.getInstance().getReference().child("LockedApps");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot i : dataSnapshot.getChildren()) {
                    for(DataSnapshot j: i.getChildren()) {
                        String temp = j.getValue().toString();
                        //Log.d("test", temp);
                        toplist.add(temp);
                    }
                }
                allowoneapp(cApp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getUnlockApp() {


    }

    public void allowoneapp(String cA) {

        ShardePref store = ShardePref.getInstance(getApplicationContext());

        String s = store.get("key_a");
        String appstate = s.substring(0,2);
        String app = s.substring(2);

        Log.d("loking","  app -:> "+ app + "|-------| state :-> " + appstate);

        if((cA.equals(app) && appstate.equals("go")) || cA.equals("com.sam007.samiyal.reg_app")  ) {

        }else if(!(cA.equals(app))) {

            String putapp = "no" + cA;
            store.put("key_a",putapp);

            for (String j : toplist) {
                Log.d("chkapps", " if " + j + " = " + cA);
                if (j.toLowerCase().equals(cA.toLowerCase())) {
                    Context ctx = null;
                    try {
                        ctx = createPackageContext(cA,0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(ctx != null) {
                        Intent i = new Intent(getApplicationContext(),UnlockApp.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY );
                        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY );
                        startActivity(i);

                        Log.d("bspref","set key_t value from aName = " + cA);
                        store.put("key_t",cA);
                    }

                }
            }
        }

    }


    /* ===================================== not in use =========================================== */

    public BackServ() {

    }

    public void removeapp() {
        toplist.remove(aName);
    }

    public void showdialog() {
        /*
        if (context == null)
            context = getApplicationContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptsView = layoutInflater.inflate(R.layout.activity_unlock_app, null);
        Lock9View lock9View = (Lock9View) promptsView.findViewById(R.id.lock_9_view);
        FlatButton forgetPassword = (FlatButton) promptsView.findViewById(R.id.forgetPassword);
        lock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                if (password.matches(sharedPreference.getPassword(context))) {
                    dialog.dismiss();
                    AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_CHECK_SCREEN, "Correct Password", "correct_password", "");
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Pattern Try Again", Toast.LENGTH_SHORT).show();
                    AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_CHECK_SCREEN, "Wrong Password", "wrong_password", "");
                }
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppCheckServices.this, PasswordRecoveryActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                dialog.dismiss();
                AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_CHECK_SCREEN, "Forget Password", "forget_password", "");
            }
        });

        dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(promptsView);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                }
                return true;
            }
        });

        dialog.show();
        */
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
