package com.edu.anlu.motel_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomManage extends AppCompatActivity {

    EditText locateRoom;
    EditText areaRoom;
    EditText elecRoom;
    EditText waterRoom;
    EditText priceRoom;
    EditText otherFeeRoom;
    EditText notPaidRoom;
    Button btnMessageRoom;
    Button btnUpdateRoom;

    // data variable
    String uName;
    String uEmail;
    String uId;
    String roomId;
    String motelId;
    String roomArea;
    String elecMonth;
    String roomLocate;
    String notPaid;
    String otherFee;
    String priceMonth;
    String waterMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_manage);


        // get view by id
        locateRoom = (EditText) findViewById(R.id.locateRoom);
        areaRoom = (EditText) findViewById(R.id.areaRoom);
        elecRoom = (EditText) findViewById(R.id.elecRoom);
        waterRoom = (EditText) findViewById(R.id.waterRoom);
        priceRoom = (EditText) findViewById(R.id.priceRoom);
        otherFeeRoom = (EditText) findViewById(R.id.otherFeeRoom);
        notPaidRoom = (EditText) findViewById(R.id.notPaidRoom);
        btnMessageRoom = (Button) findViewById(R.id.bt_message_room);
        btnUpdateRoom = (Button) findViewById(R.id.bt_update_room);

        // get excahnge value from previous activity
        Intent intent = getIntent();
        uName = intent.getStringExtra(MainActivity.EXTRA_USER_NAME);
        uEmail = intent.getStringExtra(MainActivity.EXTRA_USER_EMAIL);
        uId = intent.getStringExtra(MainActivity.EXTRA_USER_ID);
        roomId = intent.getStringExtra(MainBoard.EXTRA_ROOM_ID);
        motelId = intent.getStringExtra(MainBoard.EXTRA_MOTEL_ID);
        roomArea = intent.getStringExtra(MainBoard.EXTRA_AREA_ROOM);
        elecMonth = intent.getStringExtra(MainBoard.EXTRA_ELEC_MONTH);
        roomLocate = intent.getStringExtra(MainBoard.EXTRA_LOCATE_ROOM);
        notPaid = intent.getStringExtra(MainBoard.EXTRA_NOT_PAID);
        otherFee = intent.getStringExtra(MainBoard.EXTRA_OTHER_FEE);
        priceMonth = intent.getStringExtra(MainBoard.EXTRA_PRICE_MONTH);
        waterMonth = intent.getStringExtra(MainBoard.EXTRA_WATER_MONTH);


        // update data to edt text
        locateRoom.setText(roomLocate);
        areaRoom.setText(roomArea);
        elecRoom.setText(elecMonth);
        waterRoom.setText(waterMonth);
        priceRoom.setText(priceMonth);
        otherFeeRoom.setText(otherFee);
        notPaidRoom.setText(notPaid);


        btnUpdateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataRoom();
            }
        });

        btnMessageRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMessageUserActivity();
            }
        });


    }



    public void gotoMessageUserActivity(){
        Intent intent = new Intent(this, MeaageAndUser.class);
        startActivity(intent);
    }

    public void updateDataRoom(){
        MotelRoom room = new MotelRoom(
                Double.parseDouble(areaRoom.getText().toString()),
                Double.parseDouble(elecRoom.getText().toString()),
                Double.parseDouble(waterRoom.getText().toString()),
                Double.parseDouble(priceRoom.getText().toString()),
                Double.parseDouble(otherFeeRoom.getText().toString()),
                locateRoom.getText().toString()
        );

        room.setNotPaidTillNow(Double.parseDouble(notPaidRoom.getText().toString()));


        try {
            DatabaseReference databaseMotelRoom = FirebaseDatabase.getInstance().getReference("rooms").child(uId).child(motelId).child(roomId);
            databaseMotelRoom.setValue(room);
            Toast.makeText(this,"Cập nhật thành công", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Không thể cập nhật", Toast.LENGTH_LONG).show();
        }

    }
}
