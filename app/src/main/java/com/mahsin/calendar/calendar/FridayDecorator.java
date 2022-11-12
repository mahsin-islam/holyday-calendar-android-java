package com.mahsin.calendar.calendar;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

import java.util.Calendar;

public class FridayDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();

    public FridayDecorator() {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
//        day.copyTo(calendar);
//        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
//        return weekDay == Calendar.FRIDAY;
        int friday = day.getDate().with(DayOfWeek.FRIDAY).getDayOfMonth();
        return friday == day.getDay();
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}