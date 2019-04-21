package com.edu.anlu.motel_management;

import android.app.Activity;
import android.app.TabActivity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainBoard extends TabActivity implements NavigationView.OnNavigationItemSelectedListener {
    // khởi tạo các view
    TextView userName;
    TextView email;
    ListView lvListMotel;
    ViewGroup noMotel;
    Button btnAdd;
    GoogleSignInClient mGoogleSignInClient;

    DatabaseReference databaseMotel;
    // dữ liệu của người dùng
    String uName;
    String uEmail;
    String uId;


    public static final String EXTRA_USER_ID = "com.edu.anlu.userid";
    public static final String EXTRA_USER_NAME = "com.edu.anlu.username";

    List<Motel> listMotel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);
        NavigationView navigationView = (NavigationView) findViewById(R.id.naviga);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        NavigationMenu menuI = (NavigationMenu) navigationView.getMenu();


        userName = (TextView) headerView.findViewById(R.id.username_s);
        email = (TextView) headerView.findViewById(R.id.email_s);


        Intent intent = getIntent();
        uName = intent.getStringExtra(MainActivity.EXTRA_USER_NAME);
        uEmail = intent.getStringExtra(MainActivity.EXTRA_USER_EMAIL);
        uId = intent.getStringExtra(MainActivity.EXTRA_USER_ID);

        databaseMotel = FirebaseDatabase.getInstance().getReference("motels").child(uId);
        userName.setText(uName);
        email.setText(uEmail);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken("296953961643-k978r61g9pa8dnstvm4llab9v3de82ud.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);



        // truy van cac view cac view

        noMotel = findViewById(R.id.no_motel);
        lvListMotel = findViewById(R.id.list_motel);
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddMotel();
            }
        });

        // end set
        TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.motel_manage);
        spec.setIndicator("Quản lý khu trọ");

        getTabHost().addTab(spec);


        spec = getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.motel_detail);
        spec.setIndicator("Chi tiết khu trọ");
        getTabHost().addTab(spec);


        spec = getTabHost().newTabSpec("tag3");
        spec.setContent(R.id.my_room);
        spec.setIndicator("Phòng trọ của bạn");
        getTabHost().addTab(spec);

        for (int i = 0; i < getTabHost().getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) getTabHost().getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setAllCaps(false);
        }
        getTabHost().setCurrentTab(0);

        getData();
        //presentData();


    }


    public void getData(){
        databaseMotel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMotel.clear();
                for(DataSnapshot motelSnapshot : dataSnapshot.getChildren()){
                    Motel artist = motelSnapshot.getValue(Motel.class);

                    listMotel.add(artist);

                }
                MotelList adaptor = new MotelList(MainBoard.this,listMotel);
                lvListMotel.setAdapter(adaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void presentData(){
        if(listMotel.size() == 0){
            noMotel.setVisibility(View.VISIBLE);
            lvListMotel.setVisibility(View.INVISIBLE);
        }else {
            noMotel.setVisibility(View.INVISIBLE);
            lvListMotel.setVisibility(View.VISIBLE);
        }
    }

    public void goToAddMotel(){
        Intent intent = new Intent(this, Add_Motel.class);
        intent.putExtra(EXTRA_USER_ID,uId);
        intent.putExtra(EXTRA_USER_NAME,uName);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Log.d("inhere","navi");
        switch (menuItem.getItemId()) {

            case R.id.dashboard: {
                Log.d("dashBoard","get Click");
                break;
            }
            case R.id.messages: {
                break;
            }
            case R.id.logout: {

                Logout();

                Log.d("logout","get Click");
                break;
            }
            default:{

            }
        }
        return true;
    }

    void Logout(){

            FirebaseAuth.getInstance().signOut();
            Static_Variable.isLoggout = true;
            mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });

            finish();
    }
}
