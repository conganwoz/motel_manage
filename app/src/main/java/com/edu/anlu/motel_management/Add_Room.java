package com.edu.anlu.motel_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Add_Room extends AppCompatActivity {

    private List<Integer> listFloor = new ArrayList<>();
    private double electro;
    private double water;
    private double otherFee;
    private double roomPrice;
    private int numFloor;
    private String motelId;
    private String uId;
    private int floorSelected = 1;
    Spinner spFloor;
    EditText numRoom;
    EditText roomArea;

    private List<MotelRoom> listRoom;


    Button btAdd;
    Button btComplete;

    DatabaseReference databaseRoom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__room);

        Intent intent = getIntent();
        electro = intent.getDoubleExtra(Add_Motel.EXTRA_ELEC,0);
        water = intent.getDoubleExtra(Add_Motel.EXTRA_WATER,0.0);
        otherFee = intent.getDoubleExtra(Add_Motel.EXTRA_OTHER_FEE,0.0);
        roomPrice = intent.getDoubleExtra(Add_Motel.EXTRA_ROOM_PRICE,0.0);
        numFloor = intent.getIntExtra(Add_Motel.EXTRA_NUM_FLOOR,0);
        motelId = intent.getStringExtra(Add_Motel.EXTRA_MOTEL_ID);
        uId = intent.getStringExtra(Add_Motel.EXTRA_USERID);

        databaseRoom = FirebaseDatabase.getInstance().getReference("rooms").child(uId).child(motelId);

        numRoom = (EditText) findViewById(R.id.numroom);
        roomArea = (EditText) findViewById(R.id.room_area);
        btAdd = (Button) findViewById(R.id.bt_update);
        btComplete = (Button) findViewById(R.id.bt_complete);

        for(int i = 1; i <= numFloor;i++){
            listFloor.add(i);
        }

        spFloor = (Spinner) findViewById(R.id.num_room_perFloor);
        ArrayAdapter<Integer> floorApdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,listFloor);
        floorApdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFloor.setAdapter(floorApdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoom();
            }
        });

        btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainBoard(Static_Variable.currentUser);
            }
        });






    }


    private void gotoMainBoard(FirebaseUser user) {
        String userId = user.getUid();
        String userName = user.getDisplayName();
        String userEmail = user.getEmail();
        Intent intent = new Intent(this, MainBoard.class);
        intent.putExtra(MainActivity.EXTRA_USER_ID, userId);
        intent.putExtra(MainActivity.EXTRA_USER_NAME, userName);
        intent.putExtra(MainActivity.EXTRA_USER_EMAIL, userEmail);
        startActivity(intent);
        Log.d("check123", "mainboard");
    }


    void saveRoom(){
        int nRoom = Integer.parseInt(numRoom.getText().toString());
        double areaR = Double.parseDouble(roomArea.getText().toString());

        floorSelected = Integer.parseInt(spFloor.getSelectedItem().toString());

        for(int i = 1; i <= nRoom;i++){
            MotelRoom newRoom = new MotelRoom(areaR, electro, water, roomPrice, otherFee, floorSelected+"0"+i);
            String id = databaseRoom.push().getKey();
            databaseRoom.child(id).setValue(newRoom);

        }

        Toast.makeText(this, "Cập nhật phòng thành công!", Toast.LENGTH_LONG).show();

    }
}
