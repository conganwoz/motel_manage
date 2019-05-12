package com.edu.anlu.motel_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomGridRoom extends BaseAdapter {
    private List<MotelRoom> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomGridRoom(Context aContext,  List<MotelRoom> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_room_layout, null);
            holder = new ViewHolder();
            holder.roomNumber = (TextView) convertView.findViewById(R.id.roomNumber);
            holder.roomArea = (TextView) convertView.findViewById(R.id.roomArea);
            holder.roomPrice = (TextView) convertView.findViewById(R.id.roomPrice);
            holder.roomNotpaid = (TextView) convertView.findViewById(R.id.roomNotpaid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MotelRoom room = this.listData.get(position);
        holder.roomNumber.setText("Phòng: "+room.getLocate());
        holder.roomArea.setText("Diện tích: "+room.getArea());
        holder.roomPrice.setText("Giá: "+room.getRoomMonth());
        holder.roomNotpaid.setText("Chưa trả: "+room.getNotPaidTillNow());
        return convertView;
    }

    static class ViewHolder {
        TextView roomNumber;
        TextView roomArea;
        TextView roomPrice;
        TextView roomNotpaid;
    }
}
