package com.caseview.caselog.mirror;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.caseview.caselog.sail.CaseCount;
import com.caseview.caselog.sail.CaseRecord;
import com.caseview.caselog.sail.Client;
import com.caseview.caselog.sail.SqlBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {

    static boolean debug;
    public static void setDebug(boolean debugParam) {
        debug = debugParam;
    }
    void log(String method, String extra){
        if (debug){
            String def = "CaseLog:";
            String clazz = this.getClass().getSimpleName();

            String msg = method+":";
            if (extra != null)
                msg = msg+extra;

            Log.d(def+clazz, msg);
        }
    }
    void log(String method){
        this.log(method, null);
    }

    static Client c;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDebug(true);
        log("onCreate");

        c = new Client();
        Client.getMainContext(getApplicationContext());
        c.getCachedLoginInfo();
        if (!c.getLoggedin())
        {
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("Client", c);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        log("onBackPressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            c.onCloseApplication();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        log("onCreateOptiuonsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent intent = new Intent(MainActivity.this, ProfileShow.class);
            intent.putExtra("Client", c);
            startActivity(intent);

        } else if (id == R.id.payment) {
            Intent intent = new Intent(MainActivity.this, Payment.class);
            intent.putExtra("Client", c);
            startActivity(intent);

        } else if (id == R.id.caseSelect) {
            Intent intent = new Intent(MainActivity.this, CourtList.class);
            intent.putExtra("Client", c);
            startActivity(intent);

        } else if (id == R.id.selectedCases) {
            Intent intent = new Intent(MainActivity.this, SelectedCasesActivity.class);
            startActivity(intent);

        } else if (id == R.id.caseStatistics) {
            Intent intent = new Intent(MainActivity.this, Statistics.class);
            startActivity(intent);

        } else if (id == R.id.note) {


        } else if (id == R.id.tutorial) {

        } else if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            c.logout();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







    public static class HomeFragment extends Fragment {

        public HomeFragment() {}

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.home_fragment, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.date_description);

            return rootView;
        }
    }


    public static class CauseListFragment extends ListFragment{
        @Override
        public void onActivityCreated(Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);

            String[] caseItem = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, caseItem));
        }
    }


    public static class CaseListFragment extends ListFragment{
        @Override
        public void onActivityCreated(Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);

            ListView listView = getListView();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), Temp.class);
                    startActivity(intent);
                }
            });

            ArrayList<Object> caseList = getCaseArray(c.getCaseGroup());
            setListAdapter(new CaseArrayAdapter(getActivity(), R.layout.case_list_item, R.id.court_name, caseList));
        }

        private ArrayList<Object> getCaseArray(Map<Date,ArrayList<CaseRecord>> caseGroup) {
            ArrayList<Object> cg = new ArrayList<>();
            Set set = caseGroup.keySet();
            for (Object date : set){
                if (date instanceof Date){
                    Date d = (Date) date;
                    cg.add(d);
                    ArrayList<CaseRecord> list = caseGroup.get(d);
                    for (CaseRecord cr : list) {
                        cg.add(cr);
                    }
                }
            }
            return cg;
        }

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a HomeFragment (defined as a static inner class below).
            if (position == 0)
                return new HomeFragment();
            else if (position == 1)
                return new CaseListFragment();
            else
                return new CauseListFragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

}




class CaseArrayAdapter extends ArrayAdapter<Object>{
    Context context;
    List<Object> caseList;

    CaseArrayAdapter(Context context, int resource, int viewResource, ArrayList<Object> caseList){
        super(context, resource, viewResource, caseList);
        this.context = context;
        this.caseList = caseList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = super.getView(position,convertView,parent);
        TextView firstLabel = v.findViewById(R.id.court_name);
        TextView secondLabel = v.findViewById(R.id.case_number);
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        Object item = caseList.get(position);
        try {
            if (item instanceof Date){
                Date date = (Date)item;
                String dateText = dateFormat.format(date);
                firstLabel.setText(dateText);
                secondLabel.setText("");
            }else if (item instanceof CaseRecord){
                CaseRecord caseRecord = (CaseRecord) item;
                firstLabel.setText(caseRecord.getTempCourtName());
                secondLabel.setText(caseRecord.getTempCasetNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v;
    }

}




