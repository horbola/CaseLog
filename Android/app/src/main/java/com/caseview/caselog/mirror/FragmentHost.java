package com.caseview.caselog.mirror;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.caseview.caselog.sail.CaseCount;

import java.util.Vector;

public class FragmentHost extends AppCompatActivity
implements ItemFragment.OnFragmentInteractionListener, DetailFragment.OnDetailFragmentInteractionListener {

    private CaseCount cc;
    private DetailFragment detailFragment;
    ArrayAdapter<String> detailArray;
    private ListView numberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);

        cc = (CaseCount) getIntent().getSerializableExtra("CaseCount");
        Vector<String> years = cc.getYears(cc.getCounter());

        ItemFragment itemFragment = (ItemFragment)getSupportFragmentManager().findFragmentById(R.id.item_fragment);
        ListView yearList = itemFragment.getView().findViewById(R.id.year_list);
        ArrayAdapter itemArray = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years);
        yearList.setAdapter(itemArray);

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        numberList = detailFragment.getView().findViewById(R.id.number_list);
        detailArray = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cc.getNumbers(cc.getMaxYear(cc.getCounter())));
        numberList.setAdapter(detailArray);
    }

    @Override
    public void onFragmentInteraction(int yearInt) {
        Vector<String> numbers = cc.getNumbers(yearInt);
        detailArray = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cc.getNumbers(yearInt));
        numberList.setAdapter(detailArray);
        Toast.makeText(this, ""+yearInt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetailFragmentInteraction(String selectedNumber){

        cc.addSelectedNumber(selectedNumber);
        Toast.makeText(this, selectedNumber, Toast.LENGTH_SHORT).show();
    }
}
