package com.edu.anlu.motel_management;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    private String hostId;
    private String roomId;
    private String motelId;
    private String liveInKey = "";
    private boolean checkInRoom = false;
    private String currentGuestId;

    DatabaseReference databaseLiveIn;
    ListView list_user;
    ViewGroup noUserRoom;
    ViewGroup has_user;
    ViewGroup l_out_list;
    TextView guestName;
    TextView idCardGuest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaage_and_user);

        // get view
        list_user = (ListView) findViewById(R.id.list_user);
        noUserRoom = (ViewGroup) findViewById(R.id.no_user);
        has_user = (ViewGroup) findViewById(R.id.has_user);
        l_out_list = (ViewGroup) findViewById(R.id.l_out_list);

        guestName = (TextView) findViewById(R.id.guestName);
        idCardGuest = (TextView) findViewById(R.id.idCardGuest);

        // get data from previous acticity
        Intent intent = getIntent();
        hostId = intent.getStringExtra(MainBoard.EXTRA_USER_ID);
        roomId = intent.getStringExtra(MainBoard.EXTRA_ROOM_ID);
        motelId = intent.getStringExtra(MainBoard.EXTRA_MOTEL_ID);
        // connect to database
        databaseLiveIn = FirebaseDatabase.getInstance().getReference("liveins");


        list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MeaageAndUser.this);
                builder.setMessage("Are you sure you want to do this baby?")
                        .setCancelable(false)
                        .setPositiveButton("Yuss, lets do this", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                addNewGuest(position);
                                return;
                            }
                        })
                        .setNegativeButton("Err, no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();





            }
        });






        //navigate
        checkHasguest();


    }


    public void checkHasguest(){

        liveInKey = "";
        checkInRoom = false;
        databaseLiveIn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                boolean checkIn = false;

                if (dataSnapshot.exists()) {

                    for (DataSnapshot userSnapShot : dataSnapshot.getChildren()) {
                        LiveIn liveIn = userSnapShot.getValue(LiveIn.class);


                        if (liveIn.getRoomId().equals(roomId) && liveIn.getMotelId().equals(motelId)) {
                            currentGuestId = liveIn.getGuestId();
                            liveInKey = userSnapShot.getKey();
                            checkIn = true;
                            checkInRoom = true;
                            break;
                        }
                    }


                }
                if (checkIn) {
                    DatabaseReference databaseLiveIn_1 = FirebaseDatabase.getInstance().getReference("liveins").child(liveInKey);
                    updateUIGuest(currentGuestId);
                    noUserRoom.setVisibility(View.INVISIBLE);
                    has_user.setVisibility(View.VISIBLE);
                    l_out_list.setVisibility(View.VISIBLE);
                    getUserData();
                } else {
                    noUserRoom.setVisibility(View.VISIBLE);
                    has_user.setVisibility(View.INVISIBLE);
                    l_out_list.setVisibility(View.VISIBLE);
                    getUserData();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public void updateUIGuest(String guestId){
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("uses").child(currentGuestId);

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                guestName.setText(user.getUserName());
                idCardGuest.setText(user.getIdCard());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void addNewGuest(int position){
        String guestId = userKeys.get(position);
        if(checkInRoom == true){

            DatabaseReference databaseLiveInExist = FirebaseDatabase.getInstance().getReference("liveins").child(liveInKey);
            databaseLiveInExist.child("guestId").setValue(guestId);
            Toast.makeText(this, "Cập nhật khách thành công!", Toast.LENGTH_SHORT).show();
        }else {
            LiveIn liveIn = new LiveIn(roomId, hostId, motelId, guestId);
            DatabaseReference databaseLiveIns = FirebaseDatabase.getInstance().getReference("liveins");
            String id = databaseLiveIns.push().getKey();
            databaseLiveIns.child(id).setValue(liveIn);
            Toast.makeText(this, "Thêm khách thành công!", Toast.LENGTH_SHORT).show();
        }
    }

//    public void findRoom(){
//        checkInRoom = false;
//        databaseLiveIn.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//
//
//                if (dataSnapshot.exists()) {
//
//                    for (DataSnapshot userSnapShot : dataSnapshot.getChildren()) {
//                        LiveIn liveIn = userSnapShot.getValue(LiveIn.class);
//
//
//                        if (liveIn.getRoomId().equals(roomId) && liveIn.getMotelId().equals(motelId)) {
//                            liveInKey = userSnapShot.getKey();
//                            checkInRoom = true;
//                            break;
//                        }
//                    }
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }







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
