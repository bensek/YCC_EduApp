package com.example.hp.eduapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {// implements CallbackManager {
    //    private CallbackManager callbackManager;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInBtn;
    private TextView signUpClick;
    //   private LoginButton fbLoginBtn;
    private ProgressDialog progressBar;


    private FirebaseAuth mAuth; // An object reference to the firebase authentication
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        mEmailField = (EditText) findViewById(R.id.editText_username_in);
        mPasswordField = (EditText) findViewById(R.id.edittext_password_in);
        mSignInBtn = (Button) findViewById(R.id.button_signin);
        signUpClick = (TextView) findViewById(R.id.textView_signup_clickable);
        //fbLoginBtn = (LoginButton) findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    // User is signed in
                    //Go to the home page
                } else {
                    //User is signed out

                }
            }
        };

//        callbackManager = CallbackManager.Factory.create();
//        fbLoginBtn.setReadPermissions("email", "public_profile");
//        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d("TAG", "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d("TAG", "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d("TAG", "facebook:onError", error);
//                // ...
//            }
//        });


        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the signing in process
                //beginSignIn();
                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        signUpClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

//        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LoginManager.getInstance().logInWithReadPermissions(SigninActivity.this, Arrays.asList("public_profile"));
//
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    private void beginSignIn() {
        //This method starts the signing in process
        //Resetting errors
        mEmailField.setError(null);
        mPasswordField.setError(null);

        boolean cancel = false;
        View focusView = null;

        //Getting references to the user's email and password
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        //Checking for empty fields
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = mEmailField;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = mPasswordField;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            progressBar = new ProgressDialog(SigninActivity.this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Signing in ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progressBar.setProgress(0);
//                progressBar.setMax(100);
            progressBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        progressBar.dismiss();
                        Toast.makeText(SigninActivity.this, "Can not sign in", Toast.LENGTH_LONG).show();

                    } else {
                        progressBar.dismiss();
                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                        startActivity(intent);

                    }

                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // [START auth_with_facebook]
//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d("TAG", "handleFacebookAccessToken:" + token);
//        // [START_EXCLUDE silent]
//       // showProgressDialog();
//        // [END_EXCLUDE]
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.w("TAG", "signInWithCredential", task.getException());
//                            Toast.makeText(SigninActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // [START_EXCLUDE]
//                      //  hideProgressDialog();
//                        // [END_EXCLUDE]
//                    }
//                });
//    }
}
// [END auth_with_facebook]
