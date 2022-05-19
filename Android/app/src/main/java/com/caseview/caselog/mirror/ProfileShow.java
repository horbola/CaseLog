package com.caseview.caselog.mirror;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.caseview.caselog.sail.Client;
import com.caseview.caselog.sail.TempStore;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ProfileShow extends AppCompatActivity {

    Client c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show);

        c = (Client) getIntent().getSerializableExtra("Client");

        TextView name = findViewById(R.id.name_profile);
        TextView userID = findViewById(R.id.user_id_profile);
        TextView phone = findViewById(R.id.phone_profile);
        TextView email = findViewById(R.id.email_profile);

        HashMap<String,String> profileCache = c.getProfileCache();

        name.setText(profileCache.get("name"));
        userID.setText(profileCache.get("id"));
        phone.setText(profileCache.get("phone1"));
        email.setText(profileCache.get("father"));
    }
}
