package com.example.hp.eduapp.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hp.eduapp.CourseActivity;
import com.example.hp.eduapp.R;
import com.example.hp.eduapp.adapters.RemindersAdapter;
import com.example.hp.eduapp.utils.UtilityMethods;

public class RemindersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public RemindersAdapter adapter;
    private static final int REMINDERS_LOADER = 1;

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
    public static RemindersFragment newInstance(String param1, String param2) {
        RemindersFragment fragment = new RemindersFragment();
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

    public RemindersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(REMINDERS_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reminders, container, false);

        ListView remindersListView = (ListView) rootView.findViewById(R.id.reminders_listview);
        adapter = new RemindersAdapter(getContext(), null);
        remindersListView.setAdapter(adapter);

        remindersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: set editing action here
            }
        });
        remindersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: set reminder deleting action here
                return false;
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == REMINDERS_LOADER) {
            return new CursorLoader(
                    getContext(),
                    CalendarContract.Events.CONTENT_URI,
                    new String[]{
                            CalendarContract.Events._ID, // "_id" must always be there
                            CalendarContract.Events.TITLE,
                            CalendarContract.Events.DTSTART
                    },
                    CalendarContract.Events.DESCRIPTION + " = ?",
                    new String[]{UtilityMethods.getReminderTag(getContext(), CourseActivity.title)},
                    null
            );
        } else return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
