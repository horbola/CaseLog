package com.caseview.caselog.mirror;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.caseview.caselog.sail.Client;


/**
 * A login screen that offers login via email/password.
 */
public class Subscribe extends AppCompatActivity{

    Client c;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private SubAsyncTask task = null;

    // UI references.
    private EditText name;
    private EditText userID;
    private EditText password;
    private EditText access;


    private View mProgressView;
    private View mSignupFormView;
    private View focusView = null;
    private int REQUEST_SUBSCRIBE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        c = (Client) getIntent().getSerializableExtra("Client");

        mProgressView = findViewById(R.id.sign_up_progress);
        mSignupFormView = findViewById(R.id.sign_up_form);

        name = findViewById(R.id.name_profile);
        userID = findViewById(R.id.user_id);
        password = findViewById(R.id.password);
        access = findViewById(R.id.access);

        Button subscribe = findViewById(R.id.subscribe_button);
        TextView linkLogin = findViewById(R.id.link_login);

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSubscribing();
            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_SUBSCRIBE){
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
    private void attemptSubscribing() {

        if (task != null) return;

        // Reset errors.
        name.setError(null);
        userID.setError(null);
        password.setError(null);
        access.setError(null);

        // Store values at the time of the login attempt.
        String nameText = name.getText().toString();
        String userIDText = userID.getText().toString();
        String passwordText = password.getText().toString();
        String accessText = access.getText().toString();

        boolean cancel = false;

        //Check for a Correct name combination.
        if (TextUtils.isEmpty(nameText)){
            name.setError("Name can't be empty");
            focusView = name;
            cancel = true;
        } else if (!isNameCorrect(nameText)) {
                name.setError("Name should be in two words");
                focusView = name;
                cancel = true;
            }


        // Check for a valid email address.
        if (TextUtils.isEmpty(userIDText)) {
            userID.setError("User ID is required");
            focusView = userID;
            cancel = true;
        } else if (!isEmailValid(userIDText)) {
            userID.setError("User ID is not valid");
            focusView = userID;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordText)){
            password.setError("You need your password");
            focusView = password;
            cancel = true;
        } else if (!isPasswordValid(passwordText)) {
                password.setError(getString(R.string.error_invalid_password));
                focusView = password;
                cancel = true;
            }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Log.d("signup", "entered");

            showProgress(true);
            SubAsyncTask task = new SubAsyncTask(nameText, userIDText, passwordText, accessText, c);
            task.execute();
        }
    }

    class SubAsyncTask extends AsyncTask<Void, Void, Boolean>{

        Client c;
        private final String nameText;
        private final String userIDText;
        private final String passwordText;
        private final String accessText;

        private boolean duplicateID;
        private boolean subscriptionCreated;

        public SubAsyncTask(String nameText, String userIDText, String passwordText, String accessText, Client client) {
            this.nameText = nameText;
            this.userIDText = userIDText;
            this.passwordText = passwordText;
            this.accessText = accessText;
            c = client;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if(c.checkDuplicateId(userIDText)) {
                duplicateID = true;
                return false;
            } else if (!createSubscription(userIDText, passwordText, nameText, c)){
                subscriptionCreated = false;
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            task = null;
            showProgress(false);

            if (success){

                c.login();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
            else if (duplicateID){
                userID.setError("This id is already taken");
                userID.requestFocus();
            }
            else if (!subscriptionCreated) {
                name.setError("This acount coudn't be crated, please review everything and try again");
                name.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            task = null;
            showProgress(false);
        }


        private boolean createSubscription(String userIDText, String passwordText, String nameText, Client c){
            c.setProfileWithCache(userIDText, passwordText, "", "", nameText, "", "", "", "", "", "", "", "", "", "", "");
            return true;
        }
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

            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignupFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    private boolean isNameCorrect(String nameText) {
        return nameText.contains(" ");
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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

    public boolean bothEquals(String s, String ss)
    {
        char[] cs = s.toCharArray();
        char[] css = ss.toCharArray();

        if (cs.length != css.length){return false;}

        for (int i = 0; i < cs.length; i++) {if (cs[i] != css[i]){return false;}}

        return true;
    }

}



