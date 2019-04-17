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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainBoard extends TabActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView userName;
    TextView email;
    GoogleSignInClient mGoogleSignInClient;
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
        String uName = intent.getStringExtra(MainActivity.EXTRA_USER_NAME);
        String uEmail = intent.getStringExtra(MainActivity.EXTRA_USER_EMAIL);
        String uId = intent.getStringExtra(MainActivity.EXTRA_USER_ID);

        userName.setText(uName);
        email.setText(uEmail);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken("296953961643-k978r61g9pa8dnstvm4llab9v3de82ud.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.motel_manage);
        spec.setIndicator("quản lý khu trọ");

        getTabHost().addTab(spec);

        spec = getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.my_room);
        spec.setIndicator("phòng trọ của bạn");

        getTabHost().addTab(spec);
        for (int i = 0; i < getTabHost().getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) getTabHost().getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
        }
        getTabHost().setCurrentTab(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
                break;
            }
        }
        return true;
    }

    void Logout(){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            Static_Variable.isLoggout = true;
    }
}
