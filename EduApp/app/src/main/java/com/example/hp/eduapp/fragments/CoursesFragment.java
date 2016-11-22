package com.example.hp.eduapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.eduapp.CourseActivity;
import com.example.hp.eduapp.R;
import com.example.hp.eduapp.adapters.CoursesAdapter;
import com.example.hp.eduapp.listeners.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesFragment extends Fragment {

    public static List<String> courses = new ArrayList<>();
    public static CoursesAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match the fragment initialization parameters, e.g.
    // ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoursesFragment newInstance(String param1, String param2) {
        CoursesFragment fragment = new CoursesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public CoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_courses, container, false);

        //get the recyclerView
        RecyclerView coursesRecyclerView = (RecyclerView) rootView.findViewById(R.id.courses_recyclerview);

        //setup its layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        coursesRecyclerView.setLayoutManager(gridLayoutManager);

        //create an adapter for the recycler view
        adapter = new CoursesAdapter(getContext(), courses);

        //set this adapter to the recycler view
        coursesRecyclerView.setAdapter(adapter);

        //set a clickListener for it
        coursesRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(),
                coursesRecyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String courseName = courses.get(position);
                Intent intent = new Intent(getContext(), CourseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, courseName);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }
        ));

        return rootView;
    }

}
