package com.example.hp.eduapp.sign_up_screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hp.eduapp.R;
import com.example.hp.eduapp.data_models.User;

public class BioData1 extends AppCompatActivity {

    private EditText fnameEditText;
    private EditText lnameEditText;
    private EditText unameEditText;
    private EditText numberEditText;
    private Button nextBtn;
    private Spinner spinnerCodes;
    User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_data_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fnameEditText = (EditText) findViewById(R.id.editText_first_name_up);
        lnameEditText = (EditText) findViewById(R.id.editText_last_name_up);
        unameEditText = (EditText) findViewById(R.id.editText_username_up);
        numberEditText = (EditText) findViewById(R.id.editTextMobile);
        nextBtn = (Button) findViewById(R.id.btn_screen1);
        spinnerCodes = (Spinner) findViewById(R.id.spinner_code);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_call_codes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCodes.setAdapter(adapter);






        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to the next screen for university information
                saveBioData();
            }
        });


    }
    private void saveBioData(){
        // Reset Errors
        fnameEditText.setError(null);
        lnameEditText.setError(null);
        unameEditText.setError(null);
        numberEditText.setError(null);

        // Store the entered values
        String fName = fnameEditText.getText().toString();
        String lName = lnameEditText.getText().toString();
        String userName = unameEditText.getText().toString();
        String number = numberEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for validity of all fields
        if(TextUtils.isEmpty(fName)){
            fnameEditText.setError(getString(R.string.error_field_required));
            focusView = fnameEditText;
            cancel = true;
        }
        if(TextUtils.isEmpty(lName)){
            lnameEditText.setError(getString(R.string.error_field_required));
            focusView = lnameEditText;
            cancel = true;
        }
        if(TextUtils.isEmpty(userName)){
            unameEditText.setError(getString(R.string.error_field_required));
            focusView = numberEditText;
            cancel = true;
        }
        if(TextUtils.isEmpty(number)){
            numberEditText.setError(getString(R.string.error_field_required));
            focusView = numberEditText;
            cancel = true;
        }

        if(cancel) {
            //There was an error focus the view on the first field
            focusView.requestFocus();
        }else{
            //Create a new User object and set the variables
            user.setFname(fName);
            user.setLname(lName);
            user.setUsername(userName);
            user.setPhonenumber(Integer.valueOf(number));

            //Move to the next step
            Intent i = new Intent(BioData1.this, BioDataMajor.class);
            i.putExtra("user", user);
            startActivity(i);
        }

    }
}
