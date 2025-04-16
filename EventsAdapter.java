package com.zybooks.cs360_project2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private List<Event> eventList;

    public EventsAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventNameTextView.setText(event.getName());
        holder.eventDateTextView.setText(event.getDate());
        holder.eventTimeTextView.setText(event.getTime());
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, EventViewActivity.class);
            intent.putExtra("event_id", event.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventNameTextView;
        public TextView eventDateTextView;
        public TextView eventTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.event_name);
            eventDateTextView = itemView.findViewById(R.id.event_date);
            eventTimeTextView = itemView.findViewById(R.id.event_time);
        }
    }
}