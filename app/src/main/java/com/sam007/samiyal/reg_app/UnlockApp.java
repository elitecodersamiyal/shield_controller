package com.sam007.samiyal.reg_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sam007.samiyal.reg_app.sharedP.ShardePref;

public class UnlockApp extends AppCompatActivity {

    private Button cnl;
    private EditText pass;
    private PackageManager pm;

    Context ctx;
    private BackServ bs;
    public Context getCtx() {
        return ctx;
    }

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_app);

        pass = findViewById(R.id.u_pass);
        cnl = findViewById(R.id.u_cnl);
        pm = getPackageManager();

        ctx = this;
        bs = new BackServ(getCtx());

        fAuth = FirebaseAuth.getInstance();

        cnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p = pass.getText().toString().trim();
                String uid = fAuth.getCurrentUser().getEmail();

                if(TextUtils.isEmpty(p)) {
                    pass.setError(" PASSWORD REQUIRED");
                }else {

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(uid, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                /* ----------------- code for stoping the time  ---------------- */
                                ShardePref store = ShardePref.getInstance(getApplicationContext());


                                String s = "go" + store.get("key_t");
                                Log.d("loking","get value from key_t after unlocking s = " + s);
                                store.put("key_a",s);

                                bs.stoptimertask();
                                Log.d("timer", " --------------- unlockap.java -------------");
                                /* ------------------------------------------------------------- */

                                /* ---------- code for the activity termination ---------- */
                                Toast.makeText(getApplicationContext(), " finish ", Toast.LENGTH_SHORT).show();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    finishAndRemoveTask();
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    finishAffinity();
                                }
                                finishActivity(1);
                                /* -------------------------------------------------------- */

                                /* ---------- code for stop service(not needed) ---------- *//*
                            bs = new BackServ(getApplicationContext());
                            Intent mServiceIntent = new Intent(getApplicationContext(), bs.getClass());
                            stopService(mServiceIntent);
                            *//* ------------------------------------------------------- */

                                Toast.makeText(getApplicationContext(), "App Unlocked", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
