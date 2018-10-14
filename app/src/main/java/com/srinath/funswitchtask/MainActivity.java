package com.srinath.funswitchtask;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    RequestQueue queue;
    Gson gson;
    private ArrayList<Model> todolist = new ArrayList<>();
    private ArrayList<Model> todaylist = new ArrayList<>();
    private ArrayList<Model> laterlist = new ArrayList<>();
    Bundle today_bundle, later_bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        gson = new GsonBuilder().create();
        getJSONData();


    }

    private void getJSONData() {
        final String url = "http://www.mocky.io/v2/582695f5100000560464ca40";

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        System.out.println("JSON Response : " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Model model = gson.fromJson(object.toString(), Model.class);
                                todolist.add(model);
                                setTabValues();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        addViewPager(viewPager);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("JSON Error : " + error.getMessage());

                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
//        addViewPager(viewPager);
    }

    private void setTabValues() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        today_bundle = new Bundle();
        later_bundle = new Bundle();

        TodayFragment todayFragment = new TodayFragment();

        TodayFragment laterFragment = new TodayFragment();

        /*
         ** In JSON i am getting the value "201611121700" for all the "scheduledDate" so
         ** I commented that functionality and passing the same data to both the tabs.
         */
        todaylist.clear();
        laterlist.clear();
        for (int i = 0; i < todolist.size(); i++) {

            /*if (DateUtils.isToday(Long.parseLong(todolist.get(i).getScheduledDate())))
                todaylist.add(todolist.get(i));
            else
                laterlist.add(todolist.get(i));*/

            todaylist.add(todolist.get(i));
            laterlist.add(todolist.get(i));
        }
        today_bundle.putParcelableArrayList("todolist", todaylist);
        later_bundle.putParcelableArrayList("todolist", laterlist);

        todayFragment.setArguments(today_bundle);
        laterFragment.setArguments(later_bundle);
        adapter.addFragment(todayFragment, "TODAY");
        adapter.addFragment(laterFragment, "LATER");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
