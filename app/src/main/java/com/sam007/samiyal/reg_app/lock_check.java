package com.sam007.samiyal.reg_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class lock_check extends AppCompatActivity {

    // Start ------ local var for elements  ------
        private Button btn,btn2;
        private TextView tv;
        private EditText et;
    // End

    // Start ------ local var for database  ------
        FirebaseDatabase fdb;
        DatabaseReference dbRef;
    // End

    View.OnClickListener onclk;
    Integer counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_check);

        Toast.makeText(getApplicationContext()," activity launched ",Toast.LENGTH_SHORT).show();

        fdb = FirebaseDatabase.getInstance();
        dbRef = fdb.getReference().child("LockedApps");

        tv = findViewById(R.id.dtxt);
        et = findViewById(R.id.itxt);
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         ArrayList<String> val = new ArrayList<>(); // declaring srray list for storing the datasnapshot data

                         for(DataSnapshot i : dataSnapshot.getChildren()) {
                             String n = i.getValue().toString(); // it gives one string value to var
//                             String app = i.child("appname").getValue().toString();
                             val.add(n); // added to  array list
//                             val.add(app); // added to  array list
                         }
                         for (String j: val) {
                             Toast.makeText(getApplicationContext(),"j.v11 = " + j, Toast.LENGTH_SHORT).show(); // sequencialy displaying the data of array list
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) { }
                 }
                );
        /*
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //long c = dataSnapshot.getChildrenCount(); // this is for getting the numbers of the child uder the node.
                        //Toast.makeText(getApplicationContext(),"c = "+ c, Toast.LENGTH_SHORT).show(); // to display the no of child.

                        ArrayList<String> val = new ArrayList<>(); // declaring srray list for storing the datasnapshot data

                        for(DataSnapshot i : dataSnapshot.getChildren()) {
                            //newT n = i.getValue(newT.class);
                            String n = i.getValue().toString(); // it gives one string value to var
                            val.add(n); // added to  array list
                            //Toast.makeText(getApplicationContext(),"n = " + n, Toast.LENGTH_SHORT).show();
                        }


                        //String temp = "";
                        for (String j: val) {
                            //temp += j;
                            Toast.makeText(getApplicationContext(),"j = " + j, Toast.LENGTH_SHORT).show(); // sequencialy displaying the data of array list
                        }

                        //Toast.makeText(getApplicationContext(),"temp = " + temp,Toast.LENGTH_SHORT).show(); // this line of code displays the concatinated string of all data

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"ERROR = " + databaseError,Toast.LENGTH_SHORT).show();
                    }
                });

        */
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * under given code is for entering data;
                 */

                /* ----------- this code is for entering two string values in to table  --------- */
                String s = et.getText().toString().trim();
                counter++;
                dbRef.child(counter.toString()).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Done Successfully ",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext()," Not Done ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                /* ------------------------------------------------------------------------------ */

                /* ----------- this code is for entering two string values in to table  --------- *//*
                String s = et.getText().toString().trim();
                String t = "hey";
                newT n = new newT(s,t);
                counter++;
                dbRef.child(counter.toString()).setValue(n).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Done Successfully ",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext()," Not Done ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                *//* ------------------------------------------------------------------------------ */
            }
        });

    }

}
