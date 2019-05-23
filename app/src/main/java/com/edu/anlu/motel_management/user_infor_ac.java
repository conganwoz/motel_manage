package com.edu.anlu.motel_management;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_infor_ac extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor_ac);

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userAddress = (TextView) findViewById(R.id.userAddress);
        TextView idCard = (TextView) findViewById(R.id.userIdCard);
        TextView userPhoneNumber = (TextView) findViewById(R.id.userPhoneNumber);
        if(MainActivity.userShare == null){

        }
        else{
            userName.setText(MainActivity.userShare.getUserName());
            userAddress.setText(MainActivity.userShare.getAddress());
            idCard.setText(MainActivity.userShare.getIdCard());
            userPhoneNumber.setText(MainActivity.userShare.getPhoneNumber());
        }

    }
}
