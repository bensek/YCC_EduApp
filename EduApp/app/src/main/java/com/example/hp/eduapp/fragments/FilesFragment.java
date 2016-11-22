package com.example.hp.eduapp.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.eduapp.CourseActivity;
import com.example.hp.eduapp.R;
import com.example.hp.eduapp.adapters.FilesAdapter;
import com.example.hp.eduapp.adapters.PicturesAdapter;
import com.example.hp.eduapp.data_models.Picture;
import com.example.hp.eduapp.listeners.RecyclerViewItemClickListener;
import com.example.hp.eduapp.utils.UtilityMethods;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FilesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    //TODO: handle the picture-taking intent

    public static final String LOG_TAG = FilesFragment.class.getSimpleName();
    private int REQUEST_IMAGE_CAPTURE = 1;
    public static ArrayList<Picture> pics;
    private Picture pic;
    private Uri picUri;
    private FilesAdapter filesAdapter;
    private PicturesAdapter picturesAdapter;
    private static int ALL_FILES_LOADER = 1; //for all files on disk
    private static int COURSE_FILES_LOADER = 2; //for files associated with this course

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
    public static FilesFragment newInstance(String param1, String param2) {
        FilesFragment fragment = new FilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ALL_FILES_LOADER, null, this);
        getLoaderManager().initLoader(COURSE_FILES_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_files, container, false);
        setHasOptionsMenu(true);

        //get the files list view and pics recycler view
        ListView filesListView = (ListView) rootView.findViewById(R.id.files_listview);
        RecyclerView picsRecyclerView = (RecyclerView) rootView.findViewById(R.id.pictures_recyclerview);

        //layout manager for pics
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        picsRecyclerView.setLayoutManager(manager);

        pics = new ArrayList<>();

        //make adapters for the views
        picturesAdapter = new PicturesAdapter(getContext(), pics);
        filesAdapter = new FilesAdapter(getContext(), null);

        //set adapters to views
        picsRecyclerView.setAdapter(picturesAdapter);
        filesListView.setAdapter(filesAdapter);

        picsRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(
                getContext(),
                picsRecyclerView,
                new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        //TODO: put pic viewing action here
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        //TODO: put pic deleting action here
                    }
                }
        ));
        filesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: make the receiving activity show the PDF file
                String fileName = ((TextView) view.findViewById(R.id.files_name_textView)).getText().toString();
                File file = findFile(Environment.getExternalStorageDirectory(), fileName + ".pdf");
                Intent viewFile = new Intent(Intent.ACTION_VIEW);
                viewFile.setData(file != null ? Uri.fromFile(file) : null);
                viewFile.setType(MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"));
                viewFile.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (viewFile.resolveActivity(getContext().getPackageManager()) != null) startActivity(viewFile);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_files, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_camera){
            takePicture();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == REQUEST_IMAGE_CAPTURE
        //        && resultCode == Activity.RESULT_OK) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor c = getActivity().getContentResolver().query(picUri, projection, null, null, null);
        Log.d(LOG_TAG + " cursor", "the cursor is: " + c);
        if (c != null && c.moveToFirst()) {
            c.moveToFirst();
            pic.setPicFilePath(c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            pics.add(pic);
            picturesAdapter.notifyDataSetChanged();
            c.close();
        }
//            pics.add(pic);
//            picturesAdapter.notifyDataSetChanged();
        //}
    }

    private void takePicture() {
        //create an intent to the camera app (if any) and take a pic
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
                && takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            pic = new Picture(); //create a pic
            //String dirPath = Environment.getExternalStorageDirectory() + "/EduApp/Pictures/" + CourseActivity.title;

            //File picFolder = new File(dirPath);
            //boolean isFolderCreated = false;

            //if (!picFolder.exists()) isFolderCreated = picFolder.mkdirs();

            //if (picFolder.exists() || isFolderCreated) {
            DateFormat df = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault());
            String timeStamp = df.format(new Date());
            String imageFileName = UtilityMethods.getPictureTag(getActivity(), CourseActivity.title) + "_"
                    + timeStamp + "_.jpg";
            //String imageFilePath = dirPath + "/" + imageFileName;

            //File photo = new File(imageFilePath);

            pic.setPicFileName(imageFileName); //set the file name for the pic
            //pic.setPicFilePath(imageFilePath);

            //picUri = Uri.fromFile(photo);
            ContentValues cv = new ContentValues();
            cv.put(MediaStore.Images.Media.TITLE, pic.getPicFileName());

            picUri = getContext().getContentResolver().insert(MediaStore
                    .Images.Media.EXTERNAL_CONTENT_URI, cv); //insert the file name to the media db

            if (picUri != null) {
                Log.d(LOG_TAG + "Uri", "picUriFromDb: " + picUri.toString());
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            //}

        }
    }

    private File findFile(File parentDir, String fileName) {
        File result = null;
        File[] dirList = parentDir.listFiles();

        for (File fileFound : dirList) {
            if(fileFound.isDirectory()) {
                result = findFile(fileFound, fileName);
                if (result != null) break;
            } else if (fileFound.getName().matches(fileName)) return fileFound;
        }
        return result;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //TODO: check if this course has files associated with it. If not, load all files as below. If so, load them.
        if (id == ALL_FILES_LOADER) {
            //this cursor is for all the files on disk
            return new CursorLoader(
                    getContext(),
                    MediaStore.Files.getContentUri("external"),
                    new String[]{
                            MediaStore.Files.FileColumns._ID, // "_id" must always be there
                            MediaStore.Files.FileColumns.TITLE,
                            MediaStore.Files.FileColumns.MIME_TYPE // for example .docx, .pdf, .txt
                    },
                    MediaStore.Files.FileColumns.MEDIA_TYPE + " = ? AND "
                            + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ",
                    new String[]{
                            Integer.toString(MediaStore.Files.FileColumns.MEDIA_TYPE_NONE),
                            MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
                    },
                    null
            );
        } else return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        filesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        filesAdapter.swapCursor(null);
    }
}
