package com.edu.anlu.motel_management;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageList extends AppCompatActivity {


    ListView list_message;
    List<Message> messages = new ArrayList<>();


    String liveinKey = "";
    String hostId;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        // get view
        list_message = (ListView) findViewById(R.id.list_message);

        // get data from previous activity
        Intent intent = getIntent();
        liveinKey = intent.getStringExtra(MainBoard.EXTRA_LIVEIN_KEY);
        Toast.makeText(this, liveinKey, Toast.LENGTH_SHORT).show();
        uId = intent.getStringExtra(MainBoard.EXTRA_GUEST_ID);
        //hostId = intent.getStringExtra(MainBoard.EXTRA_HOST_ID);
        getDataMessage();
    }


    void getDataMessage(){
        DatabaseReference databaseMessages = FirebaseDatabase.getInstance().getReference("liveins");
        databaseMessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot msgSnapshot : dataSnapshot.getChildren()){
                        LiveIn liveIn = msgSnapshot.getValue(LiveIn.class);
                        if(uId.equals(liveIn.getGuestId())){
                            messages.addAll(liveIn.getMessages());
                            MessageListAdaptor adaptor = new MessageListAdaptor(MessageList.this, messages, liveIn.getHostId(), uId);
                            list_message.setAdapter(adaptor);
                        }else if(uId.equals(liveIn.getHostId())){
                            messages.addAll(liveIn.getMessages());
                            MessageListAdaptor adaptor = new MessageListAdaptor(MessageList.this, messages, uId, liveIn.getGuestId());
                            list_message.setAdapter(adaptor);
                        }

                    }




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
