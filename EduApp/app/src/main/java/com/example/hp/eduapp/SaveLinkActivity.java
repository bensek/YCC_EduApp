package com.example.hp.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hp.eduapp.fragments.CoursesFragment;
import com.example.hp.eduapp.utils.UtilityConstants;
import com.example.hp.eduapp.utils.UtilityMethods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveLinkActivity extends AppCompatActivity {
    //TODO: make the class receive the url intent and handle it well

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_link);

        ListView saveToList = (ListView) findViewById(R.id.save_links_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_save_list, R.id
                .save_links_item_textview, CoursesFragment.courses);
        saveToList.setAdapter(adapter);

        //handle shared urls
        Intent i = getIntent();
        String sharedUrlText = i.getDataString();
        final String url = handleUrlText(sharedUrlText);

        saveToList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //LinksFragment.links.add(url);
                //LinksFragment.linksAdapter.notifyDataSetChanged();
            }
        });
    }

    private String handleUrlText(String url) {
        //TODO: make the method to handle urls well
        //enter the REGEx to use
        Pattern pattern = Pattern.compile(UtilityConstants.REGEX, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE |
                Pattern.DOTALL);

        //enter the text to test against the REGEX
        Matcher matcher = pattern.matcher(url);

        if(!matcher.find()){
            UtilityMethods.message(this, "No matches were found");
        } else {
            UtilityMethods.message(this, matcher.group());
        }
        return matcher.group();
    }
}
