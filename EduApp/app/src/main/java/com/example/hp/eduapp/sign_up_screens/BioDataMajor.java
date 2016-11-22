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
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.hp.eduapp.MainActivity;
import com.example.hp.eduapp.R;
import com.example.hp.eduapp.data_models.User;

public class BioDataMajor extends AppCompatActivity {

    private EditText majorEditText;
    private EditText univEditText;
    private EditText addrEditText;
    private Button nextBtn;
    private Spinner spinnerDeg;
    private Spinner spinnerYear;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_data_major);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        majorEditText = (EditText) findViewById(R.id.editText_major_name);
        univEditText = (EditText) findViewById(R.id.editText_institution);
        addrEditText = (EditText) findViewById(R.id.editText_address);
        nextBtn = (Button) findViewById(R.id.btn_screen2);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEducInfo();
            }
        });

        spinnerDeg = (Spinner) findViewById(R.id.spinner_degrees);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.degrees_items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerDeg.setAdapter(adapter);

        spinnerYear = (Spinner) findViewById(R.id.spinner_year);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.year_of_study, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerYear.setAdapter(adapter2);

        user = getIntent().getExtras().getParcelable("user");


    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioDay:
                if (checked)
                    // Day Student
                    user.setTime("DAY");
                    break;
            case R.id.radioEve:
                if (checked)
                    // Evening Student
                    user.setTime("EVENING");
                    break;
        }
    }

    private void saveEducInfo(){
        // Reset Errors
        majorEditText.setError(null);
        univEditText.setError(null);
        addrEditText.setError(null);

        // Store the entered values
        String major = majorEditText.getText().toString();
        String institution = univEditText.getText().toString();
        String address = addrEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for validity of all fields
        if(TextUtils.isEmpty(major)){
            majorEditText.setError(getString(R.string.error_field_required));
            focusView = majorEditText;
            cancel = true;
        }
        if(TextUtils.isEmpty(institution)){
            univEditText.setError(getString(R.string.error_field_required));
            focusView = univEditText;
            cancel = true;
        }
        if(TextUtils.isEmpty(address)){
            addrEditText.setError(getString(R.string.error_field_required));
            focusView = addrEditText;
            cancel = true;
        }

        if(cancel) {
            //There was an error focus the view on the first field
            focusView.requestFocus();
        }else{
            //Create a new User object and set the variables
            user.setProgramme(major);
            user.setInstitution(institution);
            user.setAddress(address);
            //Move to the next step
            Intent i = new Intent(BioDataMajor.this, MainActivity.class);
            i.putExtra("user",user);
            startActivity(i);

        }
    }

}
