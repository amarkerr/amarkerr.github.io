package com.zybooks.cs360_project2;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class TodayDecorator implements DayViewDecorator {

    private final CalendarDay today;
    private final Drawable highlightDrawable;

    public TodayDecorator(CalendarDay today, Drawable highlightDrawable) {
        this.today = today;
        this.highlightDrawable = highlightDrawable;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day != null && day.equals(today);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(highlightDrawable);
    }
}