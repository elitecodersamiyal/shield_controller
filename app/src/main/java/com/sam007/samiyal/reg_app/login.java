package com.sam007.samiyal.reg_app;

import android.app.AppOpsManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    private TextView reg;
    private Button login;

    private EditText id;
    private EditText pass;

    private FirebaseAuth fb;
    private ProgressDialog pd;

    private DatabaseReference mdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Toast.makeText(getApplicationContext(),"User already logged in",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),SideActivity.class));
        }

        if (!isAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

        mdb = FirebaseDatabase.getInstance().getReference().child("Parents");

        reg = findViewById(R.id.textView3);
        login = findViewById(R.id.button2);
        id = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);

        fb = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),reg.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getApplicationContext(),home.class));


                final String uid = id.getText().toString().trim();
                String pw = pass.getText().toString().trim();
                String tUid,tPass;

                if(TextUtils.isEmpty(uid)) {
                    id.setError("User id can not be empty");
                    return;
                }else if(TextUtils.isEmpty(pw)) {
                    pass.setError("password can not be null");
                    return;
                }else {

                    pd.setMessage("Authenticating..");
                    pd.show();



                    fb.signInWithEmailAndPassword(uid,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Authenticated successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),SideActivity.class));
                                pd.dismiss();
                            }else {
                                Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }
                    });

                }
            }
        });


    }


    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            }
            int mode = 0;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
