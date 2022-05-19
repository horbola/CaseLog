package com.caseview.caselog.mirror;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.caseview.caselog.sail.Client;
import com.caseview.caselog.sail.PaymentRecord;
import com.caseview.caselog.sail.TempStore;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    Client c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        c = (Client) getIntent().getSerializableExtra("Client");
        ListView listView = findViewById(R.id.list);
        ArrayList<PaymentRecord> pay = c.getPaymentInThread();
        listView.setAdapter(new PaymentArrayAdapter(this, R.layout.payment_row, R.id.payment_month, pay));
    }

}


class PaymentArrayAdapter extends ArrayAdapter<PaymentRecord>{

    PaymentArrayAdapter(Context context, int rowDef, int viewToText, ArrayList<PaymentRecord> payment){
        super(context, rowDef, viewToText, payment);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        View v = super.getView(position,converView,parent);
        return v;
    }
}
