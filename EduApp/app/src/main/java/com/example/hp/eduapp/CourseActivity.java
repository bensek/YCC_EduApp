package com.example.hp.eduapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hp.eduapp.adapters.CourseActivityPageAdapter;
import com.example.hp.eduapp.data_models.Picture;
import com.example.hp.eduapp.fragments.AddLinkDialogBox;
import com.example.hp.eduapp.fragments.FilesFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class CourseActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private CourseActivityPageAdapter mCourseActivityPageAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final int FILES_FRAG = 1;
    private static final int GET_GALLERY_PIC_REQUEST_CODE = 2;
    public static String title = "";
    private Picture pic;
    private int[] icons = {
            R.drawable.ic_bookmark_border_black_24dp,
            R.drawable.ic_folder_open_black_24dp,
            R.drawable.ic_today_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mCourseActivityPageAdapter = new CourseActivityPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mCourseActivityPageAdapter);
        mViewPager.setCurrentItem(FILES_FRAG); //goes to the "files" tab by default

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }

        Intent intent = getIntent();
        title = intent.getStringExtra(Intent.EXTRA_TEXT);
        getSupportActionBar().setTitle(title);

        final FrameLayout fabLayout = (FrameLayout) findViewById(R.id.course_screen_fab_layout);
        fabLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.course_screen_fab_menu);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fabLayout.getBackground().setAlpha(240);
                fabLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        fabMenu.collapse();
                        return true;
                    }
                });

                FloatingActionButton newLinkFab = (FloatingActionButton) findViewById(R.id.new_link_fab);
                FloatingActionButton newPictureFab = (FloatingActionButton) findViewById(R.id.new_picture_fab);
                FloatingActionButton newFileFab = (FloatingActionButton) findViewById(R.id.new_file_fab);
                FloatingActionButton newRemindersFab = (FloatingActionButton) findViewById(R.id.new_reminders_fab);

                newLinkFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fabMenu.collapse();
                        AddLinkDialogBox db = new AddLinkDialogBox();
                        db.show(getFragmentManager(), "AddLink");
                    }
                });
                newPictureFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fabMenu.collapse();
                        pic = new Picture();
                        Intent getPic = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        getPic.setType("image/*");
                        if (getPic.resolveActivity(getPackageManager()) != null)
                            startActivityForResult(getPic, GET_GALLERY_PIC_REQUEST_CODE);
                    }
                });
                newFileFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Adding a File", Toast.LENGTH_SHORT).show();
                        fabMenu.collapse();
                    }
                });
                newRemindersFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fabMenu.collapse();
                        Intent intent1 = new Intent(CourseActivity.this, AddReminderActivity.class);
                        intent1.putExtra(Intent.EXTRA_TEXT, title);
                        startActivity(intent1);
                    }
                });

            }

            @Override
            public void onMenuCollapsed() {
                fabLayout.getBackground().setAlpha(0);
                fabLayout.setOnTouchListener(null);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_course_details) {
            Toast.makeText(this, "Open Course Details", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_PIC_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            Uri selectedImage = data.getData();
            String[] filePathCol = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathCol, null, null, null);
            if (c != null && c.moveToFirst()) {
                c.moveToFirst();
                int colIndex = c.getColumnIndex(filePathCol[0]);
                String filePath = c.getString(colIndex);
                pic.setPicFilePath(filePath); //TODO: fix NullPointer bug
                pic.setPicFileName(null);
                FilesFragment.pics.add(pic);
                //FilesFragment.picturesAdapter.notifyDataSetChanged();
                c.close();
            }
        }

    }


}
