package com.zybooks.cs360_project2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class EventDateDecorator implements DayViewDecorator {

    private final HashSet<CalendarDay> dates;
    private final int color;

    public EventDateDecorator(Collection<CalendarDay> dates, Context context) {
        this.dates = new HashSet<>(dates);
        this.color = ContextCompat.getColor(context, R.color.calendar_dot_color); // Theme-aware color
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(8, color)); // Dot radius = 8
    }

    // Inner class to draw a custom dot
    public static class DotSpan implements LineBackgroundSpan {
        private final int radius;
        private final int color;

        public DotSpan(int radius, int color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void drawBackground(Canvas canvas, Paint paint,
                                   int left, int right, int top, int baseline, int bottom,
                                   CharSequence charSequence, int start, int end, int lineNum) {

            int oldColor = paint.getColor();
            paint.setColor(color);
            canvas.drawCircle((left + right) / 2f, bottom + radius, radius, paint);
            paint.setColor(oldColor);
        }
    }
}
