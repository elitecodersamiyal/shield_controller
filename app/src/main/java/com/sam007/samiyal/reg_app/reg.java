package com.sam007.samiyal.reg_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sam007.samiyal.reg_app.model.data;

public class reg extends AppCompatActivity {

    private Integer counter = 0;
    private EditText email,name;
    private EditText pass;
    private EditText re_pass;
    private Button btnreg;

    private TextView login_txt;

    private DatabaseReference mdb; // --
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mAuth = FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance().getReference().child("ParentsTable"); // --

        mDialog = new ProgressDialog(this);

        email = findViewById(R.id.editText3);
        name = findViewById(R.id.u_name);
        pass = findViewById(R.id.editText2);
        re_pass = findViewById(R.id.editText4);

        btnreg =findViewById(R.id.button2);

        login_txt =findViewById(R.id.textView3);

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mEmail = email.getText().toString().trim();
                final String mName = name.getText().toString().trim();
                final String mPass = pass.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail)) {
                    email.setError("Required Field..");
                    return;
                }else if (TextUtils.isEmpty(mName)) {
                    name.setError("Required Field.");
                    return;
                }else if(TextUtils.isEmpty(mPass)) {
                    pass.setError("Required Field..");
                    return;
                }else if(re_pass.equals(pass)){
                    re_pass.setError("Password must be metch");
                    return;
                }else {
                    mDialog.setMessage("processing..");
                    mDialog.show();
                    /*counter++;
                    String id = mdb.push().getKey();
                    //data d = new data(mName,mEmail,mPass);
                    mAuth.getCurrentUser().getUid();
                    data d = new data(mEmail, mName, mPass);

                    mdb.child(counter.toString()).setValue(d);
                    Toast.makeText(getApplicationContext()," User creating .. ",Toast.LENGTH_SHORT).show();*/


                    mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {

                                /*---------------------------------------------*/
                                //counter++;
                                //String id = mdb.push().getKey();
                                //data d = new data(mName,mEmail,mPass);
                                String uid = mAuth.getCurrentUser().getUid();
                                data d = new data(mEmail, mName, mPass);

                                mdb.child(uid).setValue(d);
                                Toast.makeText(getApplicationContext()," User creating .. ",Toast.LENGTH_SHORT).show();

                                /*---------------------------------------------*/

                                Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),login.class));

                                mDialog.dismiss();
                            }else {
                                Toast.makeText(getApplicationContext(),"Problem In Connection",Toast.LENGTH_SHORT).show();

                                mDialog.dismiss();
                            }
                        }
                    });

                }

            }
        });

    }
}
