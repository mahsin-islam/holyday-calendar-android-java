package com.mahsin.calendar.calendar;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mahsin.calendar.R;

import java.util.List;

public class CalendarAdapter extends ArrayAdapter<Event> {
    private  List<Event> list;
    private LayoutInflater mInflater;

    public CalendarAdapter(Context context, List<Event> list) {
        super(context, R.layout.row_calendar, list);
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    static class ViewHolder {
        TextView text;

    }

    public void addItems(CalendarFragment calendarFragment, List<Event> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row_calendar, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.label);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(list.get(position).getEvents());
//        viewHolder.text.setTextColor(Color.RED);
        if (list.get(position).getEventColor().equals("BLUE")){

            viewHolder.text.setTextColor(Color.BLUE);
        }
        else if(list.get(position).getEventColor().equals("RED")){

            Log.d("getEventColor->", "" + list.get(position).getEventColor());
            viewHolder.text.setTextColor(Color.RED);
        }


//

        return convertView;
    }

}
