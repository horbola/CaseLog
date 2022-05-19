package com.caseview.caselog.mirror;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.caseview.caselog.sail.CaseCount;
import com.caseview.caselog.sail.Client;

import java.io.Serializable;
import java.util.Vector;

public class CourtList extends AppCompatActivity {

    Client c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_court_list);
        c = (Client) getIntent().getSerializableExtra("Client");
        final ListView list = findViewById(R.id.court_list_view);
        Vector caseCounts = c.getSyncronizedCaseNumbers();

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, caseCounts));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CaseCount cc = (CaseCount)list.getItemAtPosition(position);
                cc.setSelected(true);
                Log.d("CaseLog", "CourtList.onCreate(): "+cc.isSelected());
                Intent intent = new Intent(getApplicationContext(), FragmentHost.class);
                intent.putExtra("CaseCount", (Serializable) cc);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("CaseLog", "onBackPressed is called");
        c.onCaseSelectClose();
        super.onBackPressed();
    }
}
