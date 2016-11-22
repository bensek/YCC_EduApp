package com.example.hp.eduapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.eduapp.data_models.Group;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CreateGroupActivity extends AppCompatActivity {

    private static final String FIREBASE_URL = "https://educapplication-95d23.firebaseio.com/";
    private Firebase mFirebaseRef;
    private EditText groupNameEditText;
    private EditText user1EditText;
    private EditText user2EditText;
    private Button saveGroupBtn;
    private String mUsername;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private static String TAG = "CreateGroupActivity";
    private Group grp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Make sure we have a mUsername, from SharedPrefs
        setupUsername();

        setTitle("Creating New Group");
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mFirebaseRef = new Firebase(FIREBASE_URL).child("groups");

        groupNameEditText = (EditText) findViewById(R.id.group_name);
        user1EditText = (EditText) findViewById(R.id.user1);
        user2EditText = (EditText) findViewById(R.id.user2);
        saveGroupBtn = (Button) findViewById(R.id.save_group_btn);

        saveGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_accept:
                Toast.makeText(this, "Group created", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.action_discard:
                finish();
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //Setup a user
    private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if (mUsername == null) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved.
            mUsername = "JavaUser" + r.nextInt(100000);
            prefs.edit().putString("username", mUsername).commit(); //consider using apply() instead of commit()
        }
    }

    public void createGroup(){
        String groupName = groupNameEditText.getText().toString();

        String user1 = user1EditText.getText().toString();
        String user2 = user2EditText.getText().toString();
//        List<String>  users = null;
//        users.add(user1);
//        users.add(user2);

      //  if(!groupName.equals("")){
            //Create our model, a Group Object

            final Group group = new Group();
            group.setGroupName(groupName);
            group.setAdminName(mUsername);
            group.setChatMsg(null);
//            group.setUserList(users);
            // Create a new, auto-generated child of that chat location, and save our chat data there
//            myRef.child("groups").setValue(group);

            mFirebaseRef.push().setValue(group);
            //At this point you can notify users that they have been added

            mFirebaseRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
                @Override
                public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                    grp = dataSnapshot.getValue(Group.class);
                    grp.setId(dataSnapshot.getKey()); // Set this group key onto the user to be accessed everywhere
                    grp.setGroupName(grp.getGroupName());
                    Log.e(TAG, String.format("Child Added:  %s, %s, %s", grp.getGroupName(), grp.getAdminName(), grp.getId()));
                }

                @Override
                public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
    //                    grp = dataSnapshot.getValue(Group.class);
    //                    Log.e(TAG, String.format("Child Changed:  %s, %s, %s", grp.getGroupName(), grp.getAdminName(), grp.getId()));
                }

                @Override
                public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {
                    grp = dataSnapshot.getValue(Group.class);
                    grp.setId(dataSnapshot.getKey());
                    Log.e(TAG, String.format("Child Removed:  %s, %s, %s", grp.getGroupName(), grp.getAdminName(), grp.getId()));

                }

                @Override
                public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                    grp = dataSnapshot.getValue(Group.class);
                    Log.e(TAG, String.format("Child Moved:  %s, %s, %s, Prev: %s", grp.getGroupName(), grp.getAdminName(), grp.getId()));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.e(TAG, "Failed to add Child Listener.", firebaseError.toException());
                }
            });
//
//            //Start the Group Activity
//            Intent i = new Intent(CreateGroupActivity.this, MainActivity.class);
//            startActivity(i);
        //}
        finish();


    }

}
