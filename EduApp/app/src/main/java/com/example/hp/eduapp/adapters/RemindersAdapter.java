package com.example.hp.eduapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.eduapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by radman on 10/23/2016.
 */
public class RemindersAdapter extends CursorAdapter {

    private long startTime;

    public RemindersAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        //make a new View
        View view = LayoutInflater.from(context).inflate(R.layout.item_reminder, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //set data to view
        ViewHolder holder = (ViewHolder) view.getTag();

        if (cursor != null) {
            String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
            startTime = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));

            Date startDate = new Date(startTime);
            DateFormat df = new SimpleDateFormat("EEE, dd MMM, yyyy", Locale.getDefault()),
                    tf = new SimpleDateFormat("HH:mm", Locale.getDefault());

            holder.reminderTitleTextView.setText(title);
            holder.dueTimeTextView.setText(String.format("%s at %s hrs", df.format(startDate), tf.format(startDate)));

            //TODO: check if the reminder is due in an hour's time. if so, show accent color glowing below. if not,
            // show primary color below
            holder.reminderIndicatorImageView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }

    }

    private static class ViewHolder {
        public TextView reminderTitleTextView;
        public TextView dueTimeTextView;
        public ImageView reminderIndicatorImageView;

        public ViewHolder(View view) {
            reminderTitleTextView = (TextView) view.findViewById(R.id.reminder_title_textview);
            dueTimeTextView = (TextView) view.findViewById(R.id.start_time_textview);
            reminderIndicatorImageView = (ImageView) view.findViewById(R.id.pending_indicator_imageview);
        }
    }
}
