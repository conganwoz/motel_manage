package com.edu.anlu.motel_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Motel extends AppCompatActivity {

    public static final String EXTRA_MOTEL_ID = "com.edu.anlu.motelid";
    public static final String EXTRA_NUM_FLOOR = "com.edu.anlu.numfloor";
    public static final String EXTRA_WATER = "com.edu.anlu.water";
    public static final String EXTRA_ELEC = "com.edu.anlu.elec";
    public static final String EXTRA_ROOM_PRICE = "com.edu.anlu.roomprice";
    public static final String EXTRA_OTHER_FEE = "com.edu.anlu.otherfee";
    public static final String EXTRA_USERID = "com.edu.anlu.userid";

    private String uId;
    private String uName;

    TextView tvName;
    Button btnAdd;
    EditText motelAddress;
    EditText numFloor;
    RadioGroup motelKind;
    EditText motelRule;
    EditText priceRoom;

    DatabaseReference databaseMotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__motel);

        Intent intent = getIntent();
        uName = intent.getStringExtra(MainBoard.EXTRA_USER_NAME);
        uId = intent.getStringExtra(MainBoard.EXTRA_USER_ID);

        databaseMotel = FirebaseDatabase.getInstance().getReference("motels").child(uId);

        tvName = (TextView) findViewById(R.id.username);
        btnAdd = (Button) findViewById(R.id.bt_add_motel);
        motelAddress = (EditText) findViewById(R.id.motel_address);
        numFloor = (EditText) findViewById(R.id.num_floor);
        motelKind = (RadioGroup) findViewById(R.id.motel_kind);
        motelRule = (EditText) findViewById(R.id.motel_rule);
        priceRoom = (EditText) findViewById(R.id.price);

        tvName.setText(uName);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmotel();
            }
        });



    }


    void addmotel(){
        String mtAddr = motelAddress.getText().toString();
        int numFlr = Integer.parseInt(numFloor.getText().toString());
        int mtKind = 1;
        switch (motelKind.getCheckedRadioButtonId()){
            case R.id.person_home:{
                mtKind = 1;
                break;
            }
            case R.id.apartment: {
                mtKind = 2;
                break;
            }
        }

        String rule = motelRule.getText().toString();
        double price = Integer.parseInt(priceRoom.getText().toString());

        Motel motel = new Motel(numFlr,mtAddr,rule,mtKind,2.0,1.8,price,15000);
        String id = databaseMotel.push().getKey();
        databaseMotel.child(id).setValue(motel);
        Toast.makeText(this, "Lưu nhà trọ thành công!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,Add_Room.class);
        intent.putExtra(EXTRA_MOTEL_ID,id);
        intent.putExtra(EXTRA_NUM_FLOOR,numFlr);
        intent.putExtra(EXTRA_WATER,2.0);
        intent.putExtra(EXTRA_ELEC,1.8);
        intent.putExtra(EXTRA_ROOM_PRICE,price);
        intent.putExtra(EXTRA_OTHER_FEE,15000);
        intent.putExtra(EXTRA_USERID,uId);
        startActivity(intent);
    }
}
