package com.sam007.samiyal.reg_app;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SideActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

/*
    private Integer counter = 0;

    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    private EditText test;
    private Button l_btn,l_cnl;
    private ImageView l_out;
    private DatabaseReference mDb;
    private DatabaseReference oDb;

    private ValueEventListener mPostListener;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog pd;
*/
    //private Button btn;

    Button lock,time,monitor;
    FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_side);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();
        String e = mAuth.getCurrentUser().getEmail();
        Toast.makeText(getApplicationContext(),e,Toast.LENGTH_SHORT).show();

        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*---------------------------------------------------------------------------------*/

        lock = findViewById(R.id.lock_btn);
        time = findViewById(R.id.time_btn);
        monitor = findViewById(R.id.monitor_btn);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"time",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),UnlockApps.class));
            }
        });


        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LockApps.class));
            }
        });


/*
        mAuth = FirebaseAuth.getInstance();
        packageManager = getPackageManager();
        pd = new ProgressDialog(this);
        pd.setMessage("Loding");
        pd.show();
        new LoadApplications().execute();
*/
        /*
        btn = findViewById(R.id.d_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
            }
        });*/

/*
        mAuth = FirebaseAuth.getInstance();
        packageManager = getPackageManager();

        //d =  geActivity().findViewById(R.id.drawer_layout);
        //LoadApplicatoins().execute();

        this.LoadApplications.execute();

        l_out = findViewById(R.id.i_logout);


        l_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

*/
                /*
                AlertDialog.Builder logOutDialog = new AlertDialog.Builder(p_home.this);
                LayoutInflater inflater = LayoutInflater.from(p_home.this);
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
*/

        /*---------------------------------------------------------------------------------*/

    }// onCreate ended

    /*---------------------------------------------------------------------------------*/
/*
            private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
                ArrayList<ApplicationInfo> applist = new ArrayList<>();
                for (ApplicationInfo info : list) {
                    try {
                        if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                            applist.add(info);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return applist;
            }

            class LoadApplications extends AsyncTask<Void, Void, Void> {


                @Override
                protected Void doInBackground(Void... params) {
                    applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
                    listadaptor = new ApplicationAdapter(getApplicationContext(),
                            R.layout.snippet_list_row, applist);

                    return null;
                }

                @Override
                protected void onCancelled() {
                    super.onCancelled();
                }

                @Override
                protected void onPostExecute(Void result) {
                    setListAdapter(listadaptor);
                    pd.dismiss();
                    super.onPostExecute(result);
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                }
            }



            //@Override
            protected void onListItemClick(ListView l, View v, int position, long id) {
                //super.onListItemClick(l, v, position, id);

                mAuth = FirebaseAuth.getInstance();
                final String eMail = mAuth.getCurrentUser().getEmail();
                FirebaseUser user = mAuth.getCurrentUser();


                final ApplicationInfo app = applist.get(position);
                try {
                    final String p_name = app.packageName;
                    //startActivity(new Intent(getApplicationContext(),lock_check.class)); // ..... my working code

                    //-------------------  new working code for invoking the pass dialog  ------------------- **

                    AlertDialog.Builder setLockDialog = new AlertDialog.Builder(SideActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(SideActivity.this);
                    final View myview = inflater.inflate(R.layout.set_lock,null);
                    setLockDialog.setView(myview);
                    final AlertDialog dialog = setLockDialog.create();

                    final EditText test = myview.findViewById(R.id.c_pass);
                    Button go = myview.findViewById(R.id.l_btn);
                    Button cnl = myview.findViewById(R.id.l_cnl);



                    go.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String pass = test.getText().toString().trim();
                            String appName = p_name;
                            if(TextUtils.isEmpty(pass)) {
                                test.setError("Password required.");
                                return;
                            }else {
                                String email = mAuth.getCurrentUser().getEmail();
                                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()) {
                                            // ----- insertng app to table ----
                                            String uid = mAuth.getCurrentUser().getUid();
                                            String c = FirebaseDatabase.getInstance().getReference().push().getKey();
                                            //counter++;
                                            //FirebaseDatabase.getInstance().getReference().child("Appps").child(uid).child(counter.toString()).setValue(p_name);
                                            FirebaseDatabase.getInstance().getReference().child("LockedApps").child(uid).child(c).setValue(p_name);
                                            // --------------------------------
                                            Toast.makeText(getApplicationContext(),"App is locked",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"Password is Wrong",Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                });

                            }
                        }
                    });

                    cnl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }

*/
            /*---------------------------------------------------------------------------------*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fman = getFragmentManager();
        if (id == R.id.nav_logout) {

            AlertDialog.Builder logOutDialog = new AlertDialog.Builder(SideActivity.this);
            LayoutInflater inflater = LayoutInflater.from(SideActivity.this);
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

        } else if (id == R.id.nav_about) {

            //fman.beginTransaction().replace(R.id.content_frame,new AboutFragment()).commit();
            Intent a = new Intent(getApplicationContext(),About.class);
            startActivity(a);

        } else if (id == R.id.nav_manage) {

            //.beginTransaction().replace(R.id.content_frame,new SettingsFragment()).commit();
            Intent a = new Intent(getApplicationContext(),Settings.class);
            startActivity(a);

        } else if (id == R.id.nav_lockapp) {
            Intent a = new Intent(getApplicationContext(),LockApps.class);
            startActivity(a);
        } else if(id == R.id.nav_ulockapp) {
            Intent a = new Intent(getApplicationContext(),UnlockApps.class);
            startActivity(a);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
