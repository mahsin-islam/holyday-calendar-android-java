package com.mahsin.calendar.calendar;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;

public class SaturdayDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();

    public SaturdayDecorator() {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
//        day.copyTo(calendar);
//        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
//        return weekDay == Calendar.SATURDAY;

        int saturday = day.getDate().with(DayOfWeek.SATURDAY).getDayOfMonth();
        return saturday == day.getDay();
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}