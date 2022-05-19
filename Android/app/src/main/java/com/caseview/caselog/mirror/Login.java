package com.caseview.caselog.mirror;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.caseview.caselog.sail.Client;

import java.io.Serializable;


/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity{

    Client c;
    private static final int REQUEST_SIGNUP = 0;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    private View mProgressView;
    private View mLoginFormView;
    private View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        c = (Client) getIntent().getSerializableExtra("Client");

        // Set up the login form.
        mEmailView = findViewById(R.id.email_profile);
        mPasswordView = findViewById(R.id.password);
        Button mEmailSignInButton = findViewById(R.id.account_sign_in_button);
        TextView link_signup = findViewById(R.id.link_signup);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        link_signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Subscribe.class);
                intent.putExtra("Client", c);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_SIGNUP){
            if (resultCode == RESULT_OK){
                this.finish();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, c);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        Client c;
        boolean setEmailError; // This field is set from doInBackGround(). The error will be set to ""
        boolean setPasswordError; // This field is set from doInBackGroun(). The error will be set to ""
        View focusViewLocal; // This is the focus to transfer if an error has been occured.

        private final String mEmail;
        private final String mPassword;


        UserLoginTask(String email, String password, Client client) {
            c = client;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected void onPreExecute() {
            mEmailView.setError(null);
            mPasswordView.setError(null);

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulating network access.
                boolean idExists = c.checkID(mEmail);

                if (idExists){
                    boolean passMatched = c.checkPass(mPassword);
                    if(passMatched){
                        return true;
                    }else {
                        setPasswordError = true;
                        focusViewLocal = mPasswordView;
                    }
                }else {
                    setEmailError = true;
                    focusViewLocal = mEmailView;
                    return false;
                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (setEmailError) {
                mEmailView.setError("Id doesn't exist");
                focusView = mEmailView;
            }

            if (setPasswordError) {
                mPasswordView.setError("Password didn't match");
                focusView = mPasswordView;
            }

            if (success) {
                c.login();
                finish();
            }
            else {
                focusView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }





    private boolean checkID(String id) {
        boolean idExists = false;
        idExists = bothEquals("bar@outlook", id);
        return idExists;
    }

    private boolean checkPass(String pass) {
        boolean passMatched = false;
        passMatched = bothEquals("67890", pass);
        return passMatched;
    }

    public boolean bothEquals(String s, String ss)
    {
        char[] cs = s.toCharArray();
        char[] css = ss.toCharArray();
        if (cs.length != css.length){return false;}
        for (int i = 0; i < cs.length; i++) {if (cs[i] != css[i]){return false;}}
        return true;
    }

    /**
     *Closes the application.
     */
    public void onBackPressed() {
        //Using moveTaskToBack shows a probleb in my api 5.1 . It causes the app to be white for a while.
        //On the other hand if i use only System.exit(0) then it doesn't close rather it gets for white
        //for some while and then reopen the lolgin acitity. Or If i use finish() and moveTaskToBack()
        // only then the app closes but next time opens with the home activity other than the login
        // activity if it is opened from OS's home stack, but it's ok from launcher.
        finish();
        moveTaskToBack(true);
        System.exit(0);
    }

}
