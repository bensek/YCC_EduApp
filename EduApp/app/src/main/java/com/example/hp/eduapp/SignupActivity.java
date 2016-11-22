package com.example.hp.eduapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.eduapp.sign_up_screens.BioData1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

   private TextView signInClick;
    private Button signUpBtn;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener; // Responds to changes in the user's sign in state
    private ProgressDialog progressBar;
    private TextView passwordTextView;
//    private int progressBarStatus = 0;
//    private Handler progressBarbHandler = new Handler();
//    private long fileSize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signInClick = (TextView) findViewById(R.id.textView_signin_clickable);
        signUpBtn = (Button) findViewById(R.id.button_sign_up);
        mEmailField = (EditText) findViewById(R.id.editText_email);
        mPasswordField = (EditText) findViewById(R.id.editText_password_up);
        mConfirmPassword = (EditText) findViewById(R.id.editText_confirm_password);
        passwordTextView = (TextView) findViewById(R.id.textView_password_dont_match);
        passwordTextView.setVisibility(View.GONE);


        //Get the shared instancee of the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //User is signed in
                }else{
                    //User is signed out
                }
            }
        };
        // Set Text Watcher Listener
        mConfirmPassword.addTextChangedListener(passwordWatcher);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set the errors on the fields
                mEmailField.setError(null);
                mPasswordField.setError(null);
                mConfirmPassword.setError(null);
                boolean cancel = false;
                View focusView = null;

                String email = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();
                String cPassword = mConfirmPassword.getText().toString();

                //checking for errors

                if(TextUtils.isEmpty(email)){
                    mEmailField.setError(getString(R.string.error_field_required));
                    focusView = mEmailField;
                    cancel = true;
                }
                if(TextUtils.isEmpty(password)){
                    mPasswordField.setError(getString(R.string.error_field_required));
                    focusView = mPasswordField;
                    cancel = true;
                }
                if(TextUtils.isEmpty(cPassword)){
                    mConfirmPassword.setError(getString(R.string.error_field_required));
                    focusView = mConfirmPassword;
                    cancel = true;
                }
                if(cancel){
                    focusView.requestFocus();
                }else {
                    progressBar = new ProgressDialog(SignupActivity.this);
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Creating account ...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    progressBar.show();

                    // Begin the sign-up using email and password
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "CreateUserWithEmail:onComplete:" + task.isSuccessful());

                                    if (!task.isSuccessful()) {
                                        progressBar.dismiss();
                                        Toast.makeText(SignupActivity.this, "Authentication failed",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        progressBar.dismiss();
                                        Intent intent = new Intent(SignupActivity.this, BioData1.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

        signInClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
            }
        });
    }
    //TextWatcher on the confirm password
    private final TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            passwordTextView.setVisibility(View.VISIBLE);
        }
        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length() == 0){
                passwordTextView.setVisibility(View.GONE);
            }else{
                if(editable.equals(mPasswordField.getText().toString())) {
                    passwordTextView.setVisibility(View.GONE);
                }
            }


        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void beginSignUp(){
//        String email = mEmailField.getText().toString();
//        String password = mPasswordField.getText().toString();
//       // String confirmPass = mConfirmPassword.getText().toString();
//
//
//
//
//        Log.d("TAG", "Create account:" + email);
////        if(!validateForm()){
////            return;
////        }
////        showProgressDialog();
//
//        // Begin the Signup using email and password
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d("TAG", "CreateUserWithEmail:onComplete:" + task.isSuccessful());
////
////                        try {
////                            Thread.sleep(258791);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
//                        //If signup fails display the toast.
//                        //else the auth state listener will be notified and logic to handle
//                        //the signedup user.
//                        if(!task.isSuccessful()){
//                            Toast.makeText(SignupActivity.this, "Authentication failed",
//                                    Toast.LENGTH_LONG).show();
//                        }else{
//                            Intent intent = new Intent(SignupActivity.this, ProfileActivity.class );
//                            startActivity(intent);
//                        }
////                       hideProgressDialog();
//
//                    }
//                });
    }

}
