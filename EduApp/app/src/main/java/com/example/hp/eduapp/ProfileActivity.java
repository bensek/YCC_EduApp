package com.example.hp.eduapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private TextView namesTextView;
    private TextView instutionView;
    private ImageButton profileImgBtn;
    private TextView addPhoto;
    private int RESULT_LOAD_IMG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        namesTextView = (TextView) findViewById(R.id.textview_names);
        instutionView = (TextView) findViewById(R.id.textView_instution_with_address);
        profileImgBtn = (ImageButton) findViewById(R.id.user_profile_photo);
        addPhoto = (TextView) findViewById(R.id.add_photo);
//        User user = new User();
//        user = getIntent().getExtras().getParcelable("user");
//
//        namesTextView.setText(user.getFname() + " "+ user.getLname());
//        instutionView.setText(user.getInstitution());

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent for picking the image from the gallery
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            //When teh Image is picked
            if(requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null){
                //Get the image from data
                Uri selectedImage = data.getData();
                String [] filePathColumn = {MediaStore.Images.Media.DATA};
                //Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                //Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageButton imgView = (ImageButton) findViewById(R.id.user_profile_photo);
                //Set the Image in ImageButton after decoding the string
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            }else{
                Toast.makeText(ProfileActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_edit_menu:
                //Open Edit Activity
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
        }
        return super.onOptionsItemSelected(item);
    }
}
