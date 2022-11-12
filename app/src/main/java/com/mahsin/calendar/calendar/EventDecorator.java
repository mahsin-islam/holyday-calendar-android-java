package com.mahsin.calendar.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.mahsin.calendar.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by raghu on 2/7/17.
 */

/*
public class EventDecorator implements DayViewDecorator {
    private final Drawable drawable;
    private final CalendarDay day;
    private final String event;
    public EventDecorator(MaterialCalendarView view, Date date, String event) {
        this.day = CalendarDay.from(date);
        this.event = event;
        this.drawable = createTintedDrawable(view.getContext(), event);
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (this.day.equals(day)) {
            return true;
        }
        return false;
    }
    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
    private static Drawable createTintedDrawable(Context context, String event) {
        return applyTint(createBaseDrawable(context), event);
    }
    private static Drawable applyTint(Drawable drawable, String event) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, Integer.parseInt(event));
        return wrappedDrawable;
    }
    private static Drawable createBaseDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
    }
}
*/

//public class EventDecorator implements DayViewDecorator {
//
//    private final int color;
//    private final HashSet<CalendarDay> dates;
//
//    private final Drawable drawable;
//
//
//    public EventDecorator(int color, Collection<CalendarDay> dates, MaterialCalendarView view) {
//        this.color = color;
//        this.dates = new HashSet<>(dates);
//        this.drawable = createTintedDrawable(view.getContext(), color);
//    }
//
//    @Override
//    public boolean shouldDecorate(CalendarDay day) {
//        return dates.contains(day);
//    }
//
//
//    @Override
//    public void decorate(DayViewFacade view) {
//        view.addSpan(new DotSpan(5, color));
//        view.setSelectionDrawable(drawable);
//        view.addSpan(new ForegroundColorSpan(Color.WHITE));
//    }
//
//    private static Drawable createTintedDrawable(Context context, int color) {
//        return applyTint(createBaseDrawable(context), color);
//    }
//
//    private static Drawable applyTint(Drawable drawable, int color) {
//        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTint(wrappedDrawable, color);
//        return wrappedDrawable;
//    }
//
//    private static Drawable createBaseDrawable(Context context) {
//        return ContextCompat.getDrawable(context, R.drawable.red_circle);
//    }
//
////    @Override
////    public void decorate(DayViewFacade view) {
////        view.addSpan(new DotSpan(5, color));
////    }
//
//
//
////    @Override
////    public void decorate(DayViewFacade view) {
////        Log.d("view->", "" + view);
////        view.addSpan(new ForegroundColorSpan(color));
////        view.addSpan(new BackgroundColorSpan(R.drawable.red_circle));
//////        view.generateCircleDrawable(color);
//////        view.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.red_circle));
////
////    }
//
////    private static Drawable generateCircleDrawable(final int color) {
////        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
////        drawable.getPaint().setColor(color);
////        return drawable;
////    }
//}


public class EventDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private final CalendarDay day;
    private final int color;

    public EventDecorator(MaterialCalendarView view, LocalDate date, int color) {

        this.day = CalendarDay.from(date);
        this.color = color;
        this.drawable = createTintedDrawable(view.getContext(), color);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (this.day.equals(day)) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
        view.setSelectionDrawable(drawable);
        view.addSpan(new ForegroundColorSpan(Color.WHITE));
    }

    private static Drawable createTintedDrawable(Context context, int color) {
        return applyTint(createBaseDrawable(context, color), color);
    }

    private static Drawable applyTint(Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    private static Drawable createBaseDrawable(Context context, int color) {

        if (color == Color.RED) {
            return ContextCompat.getDrawable(context, R.drawable.red_circle);
        } else
            return ContextCompat.getDrawable(context, R.drawable.blue_circle);

    }
}