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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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

    public static final String EXTRA_ROOM_ID = "com.edu.anlu.roomid";
    public static final String EXTRA_MOTEL_ID = "com.edu.anlu.motelid";
    public static final String EXTRA_AREA_ROOM = "com.edu.anlu.arearoom";
    public static final String EXTRA_ELEC_MONTH = "com.edu.anlu.elecmonth";
    public static final String EXTRA_LOCATE_ROOM = "com.edu.anlu.locateroom";
    public static final String EXTRA_NOT_PAID = "com.edu.anlu.notpaid";
    public static final String EXTRA_OTHER_FEE = "com.edu.anlu.otherfee";
    public static final String EXTRA_PRICE_MONTH = "com.edu.anlu.pricemonth";
    public static final String EXTRA_WATER_MONTH = "com.edu.anlu.watermonth";
    public static final String EXTRA_LIVEIN_KEY = "com.edu.anlu.liveinkey";
    public static final String EXTRA_HOST_ID = "com.edu.anlu.hostid";
    public static final String EXTRA_GUEST_ID = "com.edu.anlu.guestid";
    // khởi tạo các view
    TextView userName;
    TextView email;
    ListView lvListMotel;
    ViewGroup noMotel;
    Button btnAdd;
    GoogleSignInClient mGoogleSignInClient;
    GridView gridListRoom;

    TextView addrDetail;
    TextView kindDetail;
    TextView numFloorDetail;
    TextView ruleDetail;
    TextView priceDetail;
    TextView otherPriceDetail;

    TextView motel_address;
    TextView room_number;
    TextView electro_month;
    TextView water_month;
    TextView place_bill;
    TextView other_bill;
    TextView not_pay_till_now;
    Button btn_sendMessage_to_host;
    EditText message_send_to_host;

    DatabaseReference databaseMotel;
    // dữ liệu của người dùng
    String uName;
    String uEmail;
    String uId;
    List<String> key_motels = new ArrayList<>();
    List<String> key_rooms = new ArrayList<>();
    String liveInKey;
    String hostId;


    public static final String EXTRA_USER_ID = "com.edu.anlu.userid";
    public static final String EXTRA_USER_NAME = "com.edu.anlu.username";

    List<Motel> listMotel = new ArrayList<>();
    List<MotelRoom> listRoom = new ArrayList<>();
    String currentMotelId = "";
    String currentRoomId = "";

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



        // truy van cac view

        noMotel = findViewById(R.id.no_motel);
        lvListMotel = findViewById(R.id.list_motel);
        btnAdd = findViewById(R.id.btn_add);

        motel_address = (TextView) findViewById(R.id.motel_address);
        room_number = (TextView) findViewById(R.id.room_number);
        electro_month = (TextView) findViewById(R.id.electro_month);
        water_month = (TextView) findViewById(R.id.water_month);
        place_bill = (TextView) findViewById(R.id.place_bill);
        other_bill = (TextView) findViewById(R.id.other_bill);
        not_pay_till_now = (TextView) findViewById(R.id.not_pay_till_now);
        message_send_to_host = (EditText) findViewById(R.id.message_send_to_host);
        btn_sendMessage_to_host = (Button) findViewById(R.id.btn_sendMessage_to_host);

        addrDetail = (TextView) findViewById(R.id.addrDetail);
        kindDetail = (TextView) findViewById(R.id.kindDetail);
        numFloorDetail = (TextView) findViewById(R.id.numFloorDetail);
        ruleDetail = (TextView) findViewById(R.id.ruleDetail);
        priceDetail = (TextView) findViewById(R.id.price_detail);
        otherPriceDetail = (TextView) findViewById(R.id.other_price_detail);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddMotel();
            }
        });



        gridListRoom = (GridView) findViewById(R.id.gridViewRoom);
        gridListRoom.setAdapter(new CustomGridRoom(this, listRoom));

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




        // set ListView motel item click
        lvListMotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDetailMotelSelected(position);
            }
        });


        // set gridView motel item click
        gridListRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Color.parseColor("#4451c4"));
                currentRoomId = key_rooms.get(position);
                gotoRoomActivity(position);
            }
        });

        getDataMyRoom();


        btn_sendMessage_to_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message(uId, hostId, message_send_to_host.getText().toString());
                DatabaseReference databaseLiveInOne = FirebaseDatabase.getInstance().getReference("liveins").child(liveInKey);
                databaseLiveInOne.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        LiveIn liveIn = dataSnapshot.getValue(LiveIn.class);
                        liveIn.addMessage(msg);
                        databaseLiveInOne.setValue(liveIn);
                        Toast.makeText(MainBoard.this, "Đã gửi!", Toast.LENGTH_SHORT).show();
                        message_send_to_host.setText("");
                        databaseLiveInOne.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }


    public void getDataMyRoom(){
        DatabaseReference databaseLiveIn;
        databaseLiveIn = FirebaseDatabase.getInstance().getReference("liveins");

        databaseLiveIn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot motelSnapshot : dataSnapshot.getChildren()){
                    LiveIn liveIn = motelSnapshot.getValue(LiveIn.class);
                    if(liveIn.getGuestId().equals(uId)){
                        liveInKey = motelSnapshot.getKey();
                        hostId = liveIn.getHostId();
                        // get addres motel
                        DatabaseReference motel = FirebaseDatabase.getInstance().getReference("motels").child(liveIn.getHostId()).child(liveIn.getMotelId());
                        motel.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Motel motel = dataSnapshot.getValue(Motel.class);
                                motel_address.setText(motel.getAddress());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        // get info room
                        DatabaseReference databaseRoom = FirebaseDatabase.getInstance().getReference("rooms").child(liveIn.getHostId()).child(liveIn.getMotelId()).child(liveIn.getRoomId());
                        databaseRoom.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                MotelRoom room = dataSnapshot.getValue(MotelRoom.class);
                                room_number.setText(room.getLocate());
                                electro_month.setText(room.getElectroMonth()+"đ/1 số");
                                water_month.setText(room.getWaterMonth()+"đ/1 số");
                                place_bill.setText(room.getRoomMonth()+" VNĐ");
                                other_bill.setText(room.getOtherFee()+" VNĐ");
                                not_pay_till_now.setText(room.getNotPaidTillNow()+" VNĐ");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onResume() {
        Log.d("inhere_c","inhere");
        super.onResume();
        DatabaseReference detailAllRoom = FirebaseDatabase.getInstance().getReference("rooms").child(uId).child(currentMotelId);
        listRoom.clear();
        key_rooms.clear();
        detailAllRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot motelSnapshot : dataSnapshot.getChildren()){
                    MotelRoom room = motelSnapshot.getValue(MotelRoom.class);
                    listRoom.add(room);
                    key_rooms.add(motelSnapshot.getKey());
                }
                gridListRoom.setAdapter(new CustomGridRoom(MainBoard.this, listRoom));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void gotoRoomActivity(int position){
        String id_room = key_rooms.get(position);
        String id_motel = currentMotelId;
        Log.d("idmotel1",id_motel);
        Intent intent = new Intent(this, RoomManage.class);
        intent.putExtra(MainActivity.EXTRA_USER_ID, uId);
        intent.putExtra(MainActivity.EXTRA_USER_NAME, uName);
        intent.putExtra(MainActivity.EXTRA_USER_EMAIL, uEmail);
        intent.putExtra(this.EXTRA_ROOM_ID,id_room);
        intent.putExtra(this.EXTRA_MOTEL_ID,id_motel);
        intent.putExtra(this.EXTRA_AREA_ROOM, listRoom.get(position).getArea()+"");
        intent.putExtra(this.EXTRA_ELEC_MONTH, listRoom.get(position).getElectroMonth()+"");
        intent.putExtra(this.EXTRA_LOCATE_ROOM, listRoom.get(position).getLocate()+"");
        intent.putExtra(this.EXTRA_NOT_PAID, listRoom.get(position).getNotPaidTillNow()+"");
        intent.putExtra(this.EXTRA_OTHER_FEE, listRoom.get(position).getOtherFee()+"");
        intent.putExtra(this.EXTRA_PRICE_MONTH, listRoom.get(position).getRoomMonth()+"");
        intent.putExtra(this.EXTRA_WATER_MONTH, listRoom.get(position).getWaterMonth()+"");
        startActivity(intent);

    }


    public void getDetailMotelSelected(int position){
        String id_motel = key_motels.get(position);
        currentMotelId = id_motel;
        Log.d("idmotel2",id_motel);
        DatabaseReference detailMotel = FirebaseDatabase.getInstance().getReference("motels").child(uId).child(id_motel);
        detailMotel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Motel motel = dataSnapshot.getValue(Motel.class);
                addrDetail.setText("Địa chỉ: "+motel.getAddress());
                kindDetail.setText(motel.getKind() == 1? "Loại: Nhà riêng" : "Loại: Khu tập thể");
                numFloorDetail.setText("Số tầng: "+motel.getNumberFloor());
                ruleDetail.setText("Quy tắc khu trọ: "+motel.getRuleDescription());
                priceDetail.setText("Giá phòng (chung): "+motel.getRoomPerMonth()+" VNĐ");
                otherPriceDetail.setText("Giá điện: "+motel.getElectroPerMonth()+", giá nước: "+motel.getWaterPerMonth()+" (số/tháng)");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference detailAllRoom = FirebaseDatabase.getInstance().getReference("rooms").child(uId).child(id_motel);
        listRoom.clear();
        key_rooms.clear();
        detailAllRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot motelSnapshot : dataSnapshot.getChildren()){
                    MotelRoom room = motelSnapshot.getValue(MotelRoom.class);
                    listRoom.add(room);
                    key_rooms.add(motelSnapshot.getKey());
                }
                gridListRoom.setAdapter(new CustomGridRoom(MainBoard.this, listRoom));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        getTabHost().setCurrentTab(1);

    }


    public void getData(){
        listMotel.clear();
        key_motels.clear();
        databaseMotel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMotel.clear();
                key_motels.clear();
                for(DataSnapshot motelSnapshot : dataSnapshot.getChildren()){
                    Motel artist = motelSnapshot.getValue(Motel.class);
                    listMotel.add(artist);
                    key_motels.add(motelSnapshot.getKey());
                    Log.d("key_motel",motelSnapshot.getKey());

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
                gotoMessageActivity();
                break;
            }
            case R.id.logout: {

                Logout();

                Log.d("logout","get Click");
                break;
            }
            case R.id.detail_infomation: {
                gotoInfoUser();
                break;
            }
            default:{

            }
        }
        return true;
    }


    void gotoInfoUser(){
        Intent intent = new Intent(this, user_infor_ac.class);
        intent.putExtra(MainActivity.EXTRA_USER_ID,uId);
        startActivity(intent);
    }


    void gotoMessageActivity(){
        Intent intent = new Intent(this, MessageList.class);
        intent.putExtra(EXTRA_LIVEIN_KEY, liveInKey);
        intent.putExtra(EXTRA_GUEST_ID,uId);
        startActivity(intent);
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


    private AdapterView.OnItemClickListener onListMotelClick = (parent, view, position, id) -> {

    };
}
