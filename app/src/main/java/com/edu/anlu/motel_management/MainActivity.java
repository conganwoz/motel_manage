package com.edu.anlu.motel_management;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USER_ID = "com.edu.anlu.userid";
    public static final String EXTRA_USER_NAME = "com.edu.anlu.username";
    public static final String EXTRA_USER_EMAIL = "com.edu.anlu.useremail";
    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mAuth;
    Button btn_login, btn_logout;
    TextView text;
    ImageView image;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.login);
        btn_logout = findViewById(R.id.logout);
        text = findViewById(R.id.text);
        image = findViewById(R.id.image);
        progressBar = findViewById(R.id.progress_circular);

        databaseUsers = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken("296953961643-k978r61g9pa8dnstvm4llab9v3de82ud.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        btn_login.setOnClickListener(v -> SignInGoogle());
        btn_logout.setOnClickListener(v -> Logout());

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            //gotoMainBoard(user);
            //updateUI(user);
            checkLoginedBefore(user);
            Static_Variable.isLoggout = false;
        }

        if (Static_Variable.isLoggout) {
            //Static_Variable.isLoggout = false;
            Logout();
        }
    }

    void SignInGoogle() {
        progressBar.setVisibility(View.VISIBLE);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // nếu kết quả là của intent đăng nhập
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    // xử lý đăng nhập thành công
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle: " + account.getId());
        AuthCredential credencial = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credencial)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Static_Variable.isLoggout = false;
                        Log.d("TAG", "signin success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        String userId = user.getUid();
                        Static_Variable.currentUser = user;

                        checkLoginedBefore(user);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("TAG", "signin failure");
                        Toast.makeText(this, "SignIn Failed!", Toast.LENGTH_LONG);
                        //updateUI(null);
                    }
                });
    }

    // kiểm tra user đã đăng nhập vào app trước đó chưa
    private void checkLoginedBefore(FirebaseUser user) {
        Log.d("check_func","enter checkLoginedBefore");

        String userId = user.getUid();
        Log.d("user_id_",userId);
        DatabaseReference ref = databaseUsers.child("uses");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean checkIn = false;
                if (dataSnapshot.exists()) {

                    for (DataSnapshot userSnapShot : dataSnapshot.getChildren()) {
                        User user = userSnapShot.getValue(User.class);


                        if (user.getUserId().equals(userId)) {
                            checkIn = true;
                            Log.d("ID_user", user.getUserId() + "----" + userId);
                            break;
                        }
                    }


                }
                if (checkIn) {
                    gotoMainBoard(user);
                    Log.d("inhere", "mainboard");

                } else {
                    gotoAddInfor(user);
                    Log.d("inhere", "addinfo");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    // chuyển hướng sang activity mainboard của user
    private void gotoMainBoard(FirebaseUser user) {
        Static_Variable.currentUser = user;
        String userId = user.getUid();
        String userName = user.getDisplayName();
        String userEmail = user.getEmail();
        Intent intent = new Intent(this, MainBoard.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_USER_NAME, userName);
        intent.putExtra(EXTRA_USER_EMAIL, userEmail);
        startActivity(intent);
        Log.d("check123", "mainboard");
    }


    // chuyển hướng sang activity thêm thông tin
    private void gotoAddInfor(FirebaseUser user) {
        Static_Variable.currentUser = user;
        String userId = user.getUid();
        String userName = user.getDisplayName();
        Intent intent = new Intent(this, InfoSignIn.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_USER_NAME, userName);
        startActivity(intent);
        Log.d("check123", "addinfo");
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            //String photo = String.valueOf(user.getPhotoUrl());
            text.append("Info : \n");
            text.append(name);
            text.append(email);

            //Picasso.get().load(photo).into(image);
            btn_login.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.VISIBLE);
        } else {

            text.setText(getString(R.string.firebase_logout));
            Picasso.get().load(R.drawable.ic_firebase_logo).into(image);
            btn_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
        }
    }

    void Logout() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> updateUI(null));
    }

}
