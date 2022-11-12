package com.mahsin.calendar.calendar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mahsin.calendar.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalendarFragment extends Fragment implements OnMonthChangedListener {
    //date decorator
    public static OneDayDecorator oneDayDecorator = new OneDayDecorator();

    private MaterialCalendarView calendarView;
    private ListView listView;
    private Calendar calendar;

    private List<CalendarDay> calEvents = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private HashMap<Integer, List<Event>> map = new HashMap<>();
    private CalendarAdapter calAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setDivider(null);
        calAdapter = new CalendarAdapter(getActivity(), eventList);
        listView.setAdapter(calAdapter);

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setDateTextAppearance(View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE);

        //calendar formation
        calendarView.state().edit()
//                .setFirstDayOfWeek(DayOfWeek.of(1))

                .setMinimumDate(CalendarDay.from(2022, 1, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2022, 12, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        //decorate Add
        calendarView.addDecorators(
                new SaturdayDecorator(),
                new FridayDecorator(),
                oneDayDecorator);
        final Calendar calendar = Calendar.getInstance();
//        calendarView.setSelectedDate(calendar.getTime());
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                calendarView.setHeaderTextAppearance(R.style.Theme_CalenDar);
            }
        });

        calendarView.setOnMonthChangedListener(this);

//        makeJsonObjectRequest();
        jsonEventObjectRequest();
        return view;
    }

    private void jsonEventObjectRequest() {

        String response = loadJSONFromAsset();
        List<Event> events = new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(response);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);
                String StartDate = jsonObject.getString("StartDate");
                LocalDate date = LocalDate.parse(StartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US));

                String eventID = jsonObject.getString("EventID");
                String title = jsonObject.getString("Title");
                String eventColor = jsonObject.getString("eventcolor");

                int month = jsonObject.getInt("Month");
//                Log.d("eventID->", "" + eventID+"date->"+date+"title->"+title+"eventColor->"+eventColor+"month"+month);
                CalendarDay day = CalendarDay.from(date);
                Event event = new Event(date, eventID, title, eventColor, month);

                calendar = Calendar.getInstance();

//                if (!map.containsKey(month)) {
////                    List<Event> events = new ArrayList<>();
//                    events.add(event);
//                    map.put(month, events);
//                    Log.d("month->", "" + month);
//                    Log.d("events->", "" + events);
//                }
//                else {
////                    List<Event> events = map.get(month);
//                    events.add(event);
//                    map.put(month, events);
//                    Log.d("month1->", "" + month);
//                    Log.d("events2->", "" + events);
//
//                }

//                events.add(event);
                events.add(event);
                map.put(month, events);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // after parsing
        calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        List<Event> eventList = new ArrayList<>();
//        List<Event> eventOne = map.get(month + 1);
//        for (Event eventTwo : eventOne) {
//            Log.d("eventOne->", "" + eventTwo.getDate());
//
//        }

//        if (eventOne != null && eventOne.size() > 0) {
//            calAdapter.addItems(eventOne);
//        } else {
//            calAdapter.clear();
//        }

        for (Event event : events) {
//            Log.d("getEvents->", "" + event.getEvents());
            if (event.getMonth() == month + 1) {
                eventList.add(new Event(event.getDate(), event.getEvents(), event.getEventColor()));
            }
            if (event.getEventColor().equals("BLUE")) {
                EventDecorator eventDecorator = new EventDecorator(calendarView, event.getDate(), Color.BLUE);
                calendarView.addDecorator(eventDecorator);
            } else if (event.getEventColor().equals("RED")) {
                EventDecorator eventDecorator = new EventDecorator(calendarView, event.getDate(), Color.RED);
                calendarView.addDecorator(eventDecorator);
            }
        }
        if (eventList != null && eventList.size() > 0) {
            calAdapter.addItems(this, eventList);
        } else {
            calAdapter.clear();
        }
//        Log.d("colorEvent->", "" + event.getEvents());

    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
//            InputStream is = getActivity().getAssets().open("holydayCalendar.json");
            InputStream is = getActivity().getAssets().open("calendar.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        Calendar cal = Calendar.getInstance();
//        cal.setTime(date.getDate());

        int month = date.getMonth();

        List<Event> event = map.get(month);


//        Set<Event> set = new HashSet<>();
        List<Event> eventList1 = new ArrayList<>();
//        List<Event> eventList2 = new ArrayList<>();
        try {

            for (Event events : event) {

              

                if (events.getMonth() == month) {
                    eventList1.add(new Event(events.getDate(), events.getEvents(), events.getEventColor()));
//                    eventList2.add(new Event(events.getDate(), events.getEvents(), events.getEventColor()));

                }

            }
            for (int i = 0; i < eventList1.size(); i++){
                for (int j = i + 1; j < eventList1.size(); j++) {
                    if (eventList1.get(i).getEvents().equals(eventList1.get(j).getEvents())) {
                        eventList1.remove(j);
                        j--;
                    }
                }
            }

        } catch (Exception e) {
            Log.d("exception->", "" + e);
        }
        if (eventList1 != null && eventList1.size() > 0) {
//                Log.d("eventList->", "" + eventList.toString());
            calAdapter.addItems(this, eventList1);
        } else {
            calAdapter.clear();
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    private void makeJsonObjectRequest() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
//
//        String response = loadJSONFromAsset();
//        try {
//            JSONArray jArray = new JSONArray(response);
//            for (int i = 0; i < jArray.length(); i++) {
//                JSONObject jsonObject = jArray.getJSONObject(i);
//                String StartDate = jsonObject.getString("StartDate");
//                LocalDate date = LocalDate.parse(StartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US));
//
//                String title = jsonObject.getString("Title");
//                int month = jsonObject.getInt("Month");
//
//
//                CalendarDay day = CalendarDay.from(date);
//                Event event = new Event(date, title);
//
//                calendar = Calendar.getInstance();
////                cal.setTime(date);
////                int month = cal.get(Calendar.MONTH);
//
//                if (!map.containsKey(month)) {
//                    List<Event> events = new ArrayList<>();
//                    events.add(event);
//                    map.put(month, events);
//                    Log.d("month->", "" + month);
//                    Log.d("events->", "" + events);
//                } else {
//                    List<Event> events = map.get(month);
//                    events.add(event);
//                    map.put(month, events);
//
//                }
//
//                calEvents.add(day);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // after parsing
//        calendar = Calendar.getInstance();
//        int month = calendar.get(Calendar.MONTH);
//        List<Event> event = map.get(month + 1);
//        for (Event eventOne : event) {
//            Log.d("eventOne->", "" + eventOne.getDate());
//
//        }
//
//        if (event != null && event.size() > 0) {
//            calAdapter.addItems(event);
//        } else {
//            calAdapter.clear();
//        }
//        EventDecorator eventDecorator = new EventDecorator(Color.BLUE, calEvents, calendarView);
//        calendarView.addDecorator(eventDecorator);
//
//    }
}