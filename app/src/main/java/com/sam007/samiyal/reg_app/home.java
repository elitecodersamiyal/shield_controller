package com.sam007.samiyal.reg_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home extends AppCompatActivity {

    Button lock,time,monitor;
    ImageView lout;

    /* -------------------------------------------- */

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onRestart() {
        super.onRestart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){

            Toast.makeText(getApplicationContext(),"User logged in",Toast.LENGTH_SHORT).show();
        }
        else{
            startActivity(new Intent(getApplicationContext(),login.class));
            Toast.makeText(getApplicationContext(),"User not logged in",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lock = findViewById(R.id.lock_btn);
        time = findViewById(R.id.time_btn);
        monitor = findViewById(R.id.monitor_btn);
        lout = findViewById(R.id.i_logout);

        mAuth = FirebaseAuth.getInstance();

        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder logOutDialog = new AlertDialog.Builder(home.this);
                LayoutInflater inflater = LayoutInflater.from(home.this);
                View loview = inflater.inflate(R.layout.logout_dialog,null);
                logOutDialog.setView(loview);
                final AlertDialog lodialog = logOutDialog.create();

                Button go = loview.findViewById(R.id.i_lout);
                Button cnl = loview.findViewById(R.id.i_lcnl);

                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //------------------------------------------
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), login.class));

                        Toast.makeText(getApplicationContext(),"logout",Toast.LENGTH_SHORT).show();
                        //------------------------------------------

                        lodialog.dismiss();

                    }
                });

                cnl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lodialog.dismiss();
                    }
                });
                lodialog.show();

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"time",Toast.LENGTH_SHORT).show();
            }
        });

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"monitor",Toast.LENGTH_SHORT).show();
            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),p_home.class));
            }
        });

    }

}
