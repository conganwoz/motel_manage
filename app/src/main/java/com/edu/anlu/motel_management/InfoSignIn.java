package com.edu.anlu.motel_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class InfoSignIn extends AppCompatActivity {


    public static final String EXTRA_USER_ID = "com.edu.anlu.userid";
    public static final String EXTRA_USER_NAME = "com.edu.anlu.username";
    public static final String EXTRA_USER_EMAIL = "com.edu.anlu.useremail";
    TextView tvEmail;
    EditText edAddress;
    EditText edUserName;
    EditText edPhoneNumber;
    EditText edIdCard;
    Button btnUpdate;
    String userId;

    DatabaseReference databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sign_in);


        tvEmail = (TextView) findViewById(R.id.userinfo);
        edAddress = (EditText) findViewById(R.id.address);
        edUserName = (EditText) findViewById(R.id.username);
        edPhoneNumber = (EditText) findViewById(R.id.phonenumber);
        edIdCard = (EditText) findViewById(R.id.idcard);
        btnUpdate = (Button) findViewById(R.id.bt_update);
        databaseUser = FirebaseDatabase.getInstance().getReference("uses");
        Intent intent = getIntent();

        userId = intent.getStringExtra(MainActivity.EXTRA_USER_ID);
        String userName = intent.getStringExtra(MainActivity.EXTRA_USER_NAME);

        edUserName.setText(userName);
        if(Static_Variable.currentUser != null){
            tvEmail.setText(Static_Variable.currentUser.getEmail());
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo(userId);
            }
        });


    }

    void updateInfo(String userId){
        String userName = edUserName.getText().toString().trim();
        String address = edAddress.getText().toString().trim();
        String phoneNumber = edPhoneNumber.getText().toString().trim();
        String noCard = edIdCard.getText().toString().trim();

        if(TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(address)||
                TextUtils.isEmpty(phoneNumber)||
                TextUtils.isEmpty(noCard)){

            if(TextUtils.isEmpty(userName)){
                edUserName.setError("Nhập đầy đủ tên");
            }

            if(TextUtils.isEmpty(address)){
                edUserName.setError("Nhập đầy đủ địa chỉ");
            }

            if(TextUtils.isEmpty(phoneNumber)){
                edUserName.setError("Nhập đầy đủ SĐT");
            }

            if(TextUtils.isEmpty(noCard)){
                edUserName.setError("Nhập đầy đủ số CMND");
            }

            return;
        }

        User newUser = new User(userName, address,phoneNumber,0,noCard,userId, true);
        databaseUser.child(userId).setValue(newUser);
        Toast.makeText(this,"cập nhật thành công", Toast.LENGTH_SHORT).show();
        gotoMainBoard(Static_Variable.currentUser);


    }

    private void gotoMainBoard(FirebaseUser user){
        String userId = user.getUid();
        String userName = user.getDisplayName();
        String userEmail = user.getEmail();
        Intent intent = new Intent(this,MainBoard.class);
        intent.putExtra(EXTRA_USER_ID,userId);
        intent.putExtra(EXTRA_USER_NAME,userName);
        intent.putExtra(EXTRA_USER_EMAIL,userEmail);
        startActivity(intent);
    }
}
