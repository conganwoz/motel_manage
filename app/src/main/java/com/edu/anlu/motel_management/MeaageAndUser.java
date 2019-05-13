package com.edu.anlu.motel_management;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MeaageAndUser extends AppCompatActivity {


    List<User> listUser = new ArrayList<>();
    List<String> userKeys = new ArrayList<>();
    DatabaseReference databaseUser;

    ListView list_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaage_and_user);

        list_user = (ListView) findViewById(R.id.list_user);




        getUserData();
    }



    public void getUserData(){
        listUser.clear();
        userKeys.clear();
        databaseUser = FirebaseDatabase.getInstance().getReference("uses");
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot motelSnapshot : dataSnapshot.getChildren()){
                    User artist = motelSnapshot.getValue(User.class);
                    listUser.add(artist);
                    userKeys.add(motelSnapshot.getKey());
                    Log.d("key_motel",motelSnapshot.getKey());
                    UserList adaptor = new UserList(MeaageAndUser.this, listUser);
                    list_user.setAdapter(adaptor);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
