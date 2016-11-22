package com.example.hp.eduapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.eduapp.CreateGroupActivity;
import com.example.hp.eduapp.GroupSettingsActivity;
import com.example.hp.eduapp.R;
import com.example.hp.eduapp.adapters.ChatMessageAdapter;
import com.example.hp.eduapp.data_models.ChatMessage;
import com.example.hp.eduapp.data_models.Group;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class GroupsFragment extends Fragment {

    private ListView mListView;
    private ImageButton mButtonSend;
    private TextView debugTextView;
    private EditText mEditTextMessage;
    private ImageView mImageView;
    private ChatMessageAdapter mAdapter;
    private ArrayList<ChatMessage> chatHistory;
    private Button createBtn;
    private Button joinBtn;
    private Firebase mFirebaseRef;
    private Firebase mGFirebaseRef;
    private String groupId;
    private Group group;

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
    public static GroupsFragment newInstance(String param1, String param2) {
        GroupsFragment fragment = new GroupsFragment();
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

    public GroupsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);
        setHasOptionsMenu(true);

        mFirebaseRef = new Firebase("https://educapplication-95d23.firebaseio.com/");
        mGFirebaseRef = new Firebase("https://educapplication-95d23.firebaseio.com/messages/");

        debugTextView = (TextView) rootView.findViewById(R.id.debugTextView);
        createBtn = (Button) rootView.findViewById(R.id.create_group_btn);
        joinBtn = (Button) rootView.findViewById(R.id.join_group_btn);

        initControls(rootView);
        readGroupData();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CreateGroupActivity.class));
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_groups, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings_groups){
            startActivity(new Intent(getContext(), GroupSettingsActivity.class));
        }
        return true;
    }

    private void initControls(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.msgListView);
        mEditTextMessage = (EditText) rootView.findViewById(R.id.messageEditText);
        mButtonSend = (ImageButton) rootView.findViewById(R.id.sendMessageButton);

        loadDummyHistory();

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = mEditTextMessage.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//Specifies the user who sent it
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);
                chatMessage.setAuthor("Ben"); // Get the username from the constants class
                //Sending the chat message to the Group Node
                group.setChatMsg(chatMessage);
                mGFirebaseRef.push().setValue(chatMessage);
                mEditTextMessage.setText("");
                displayMessage(chatMessage);
            }
        });
    }

    public void displayMessage(ChatMessage message) {
        mAdapter.add(message);
        mAdapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        mListView.setSelection(mListView.getCount() - 1);
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        mAdapter = new ChatMessageAdapter(getActivity(), new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);

        for(int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    public void readGroupData(){
        mFirebaseRef.child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists() ){
                    return;
                }

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    //Getting the data from snapshot
                    group = postSnapShot.getValue(Group.class);
                    groupId = postSnapShot.getKey();
                    group.setId(groupId);
                    String adminName = group.getAdminName();
                    group.setAdminName(adminName);
                    String groupName = group.getGroupName();
                    group.setGroupName(groupName);
                    group.setChatMsg(null);
                    // ChatMessage msg = group.getChatMsg();

                    debugTextView.setText("GROUP INFO \n" + adminName + "\n"+
                            groupName + "\n" + groupId);

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

}
