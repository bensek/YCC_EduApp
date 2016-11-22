package com.example.hp.eduapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.eduapp.R;
import com.example.hp.eduapp.fragments.FilesFragment;

/**
 * Created by radman on 11/20/2016.
 */

public class FilesAdapter extends CursorAdapter {

    public FilesAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //make a new View
        View view = LayoutInflater.from(context).inflate(R.layout.item_files, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //set data to view
        ViewHolder holder = (ViewHolder) view.getTag();

        if (cursor != null) {
            String fileTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
            String fileMimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE));

            Log.e(FilesFragment.LOG_TAG, "file Mime type is: " + fileMimeType);

            holder.fileTitleTextView.setText(fileTitle);
            //TODO: check the file type and use it to set a corresponding icon
        }
    }

    private static class ViewHolder {
        public TextView fileTitleTextView;
        public ImageView fileIconImageView;

        public ViewHolder(View view) {
            fileTitleTextView = (TextView) view.findViewById(R.id.files_name_textView);
            fileIconImageView = (ImageView) view.findViewById(R.id.files_icon_imageView);
        }
    }
}
