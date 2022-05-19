package com.caseview.caselog.mirror;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Temp extends AppCompatActivity {

    private UserLoginTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        mAuthTask = new UserLoginTask("saif", "saifID", "12345", "");
        mAuthTask.execute((Void)null);
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mUserID;
        private final String mPassword;
        private final String mAccess;

        UserLoginTask(String name, String userID, String password, String access) {
            mName = name;
            mUserID = userID;
            mPassword = password;
            mAccess = access;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulating network access.
                Log.d("doInBack", "Entered");

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                setResult(RESULT_OK);
                finish();
            }else {
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}
