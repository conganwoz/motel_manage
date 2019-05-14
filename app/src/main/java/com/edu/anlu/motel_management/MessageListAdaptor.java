package com.edu.anlu.motel_management;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageListAdaptor extends ArrayAdapter<Message> {

    private Activity context;
    private List<Message> messageList1;
    private String hostId;
    private String guestId;

    public MessageListAdaptor(Activity context, List<Message> messageList1, String idHost, String idGuest){
        super(context,R.layout.list_message_live, messageList1);
        this.context = context;
        this.messageList1 = messageList1;
        this.hostId = idHost;
        this.guestId = idGuest;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_message_live,null,true);

        TextView textViewFrom = (TextView) listViewItem.findViewById(R.id.from_user);
        TextView textViewNumTo = (TextView) listViewItem.findViewById(R.id.to_user);
        TextView textViewContent = (TextView) listViewItem.findViewById(R.id.content_user);

        Message msg = messageList1.get(position);
        textViewFrom.setText(msg.getFrom().equals(guestId)?   "From: Khách":"From: Chủ");
        textViewNumTo.setText(msg.getTo().equals(guestId)? "To: Khách":"To: Chủ");
        textViewContent.setText(msg.getContent());

        return listViewItem;
    }
}
