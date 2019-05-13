package com.edu.anlu.motel_management;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserList extends ArrayAdapter<User> {
    private Activity context;
    private List<User> userList;

    public UserList(Activity context, List<User> artistList){
        super(context,R.layout.list_motel, artistList);
        this.context = context;
        this.userList = artistList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_user,null,true);

        TextView textViewUserName = (TextView) listViewItem.findViewById(R.id.textViewUserName);
        TextView textViewPhoneNumber = (TextView) listViewItem.findViewById(R.id.textViewPhoneNumber);

        User user = userList.get(position);
        textViewUserName.setText(user.getUserName());
        textViewPhoneNumber.setText(user.getIdCard());
        return listViewItem;
    }
}
