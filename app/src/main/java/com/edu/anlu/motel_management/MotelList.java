package com.edu.anlu.motel_management;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MotelList extends ArrayAdapter<Motel> {
    private Activity context;
    private List<Motel> motelList;

    public MotelList(Activity context, List<Motel> artistList){
        super(context,R.layout.list_motel, artistList);
        this.context = context;
        this.motelList = artistList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_motel,null,true);

        TextView textViewAddr = (TextView) listViewItem.findViewById(R.id.textViewAddr);
        TextView textViewNumFloor = (TextView) listViewItem.findViewById(R.id.textViewNumFl);
        TextView textViewKind = (TextView) listViewItem.findViewById(R.id.textViewKind);

        Motel motel = motelList.get(position);
        textViewAddr.setText(motel.getAddress());
        textViewNumFloor.setText(motel.getNumberFloor() + " tầng");
        textViewKind.setText(motel.getKind() == 1? "Loại: Nhà riêng":"Loại: Khu tập thể");

        return listViewItem;
    }
}
