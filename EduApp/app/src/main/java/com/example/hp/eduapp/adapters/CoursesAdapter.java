package com.example.hp.eduapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.eduapp.R;

import java.util.List;

/**
 * Created by radman on 9/25/2016.
 */
public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private List<String> courses;
    private Context c;

    public CoursesAdapter(Context c, List<String> courses) {
        this.c = c;
        this.courses = courses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get a layout inflater from the context
        LayoutInflater inflater = LayoutInflater.from(c);

        //use it to inflate the row item layout
        View courseView = inflater.inflate(R.layout.item_courses, parent, false);

        //return a holder for the layout
        CoursesAdapter.ViewHolder holder = new ViewHolder(courseView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //set the data to the views
        holder.courseIcon.setImageResource(R.drawable.course_icon);
        holder.courseName.setText(courses.get(position));
    }

    @Override
    public int getItemCount() {
        //return the number of courses to display
        return courses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView courseIcon;
        public TextView courseName;

        public ViewHolder(View itemView) {
            super(itemView);
            courseIcon = (ImageView) itemView.findViewById(R.id.course_icon);
            courseName = (TextView) itemView.findViewById(R.id.course_name_textview);
        }
    }

}
