package com.srinath.funswitchtask;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class TodayFragment extends Fragment {
    private ArrayList<Model> todaylist = new ArrayList<>();
    private ArrayList<Model> todaycompletedlist = new ArrayList<>();
    private ArrayList<Model> todaypendinglist = new ArrayList<>();
    RadioGroup rg;

    public TodayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            todaylist = bundle.getParcelableArrayList("todolist");

        }

        functionCall(view);
        return view;
    }

    private void functionCall(View view) {
        splilList();
        setPending(view);
        setCompleted(view);
    }

    private void setCompleted(View view) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout l1 = (LinearLayout) view.findViewById(R.id.completed_ll);
        l1.setOrientation(LinearLayout.VERTICAL);
        l1.removeAllViews();
        rg = (RadioGroup) getLayoutInflater().inflate(R.layout.radiogroup_layout, null);
        l1.setBackgroundColor(Color.parseColor("#ffffff"));

        for (int j = 0; j < todaycompletedlist.size(); j++) {
            View v = new View(getActivity().getApplicationContext());
            RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.completed_list_row, null);
            rb.setId(todaycompletedlist.get(j).getId());
            rb.setTag(todaycompletedlist.get(j));

            String date = getDateFormat(todaycompletedlist.get(j).getScheduledDate());
            rb.setText(todaycompletedlist.get(j).getDescription() + "  " + date);
            rb.setChecked(true);
            rb.setClickable(false);
            v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
            v.setBackgroundColor(Color.parseColor("#eeeeee"));
            rg.addView(rb);
            rg.addView(v);


        }
        l1.addView(rg);


    }

    private void setPending(final View view) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout l1 = (LinearLayout) view.findViewById(R.id.pending_ll);
        l1.setOrientation(LinearLayout.VERTICAL);
        l1.removeAllViews();
        rg = (RadioGroup) getLayoutInflater().inflate(R.layout.radiogroup_layout, null);

        l1.setBackgroundColor(Color.parseColor("#ffffff"));
        if (todaypendinglist.size() > 0) {

            for (int j = 0; j < todaypendinglist.size(); j++) {
                LinearLayout l3 = new LinearLayout(getActivity().getApplicationContext());
                LinearLayout.LayoutParams date_params = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                l3.setOrientation(LinearLayout.HORIZONTAL);

                View v = new View(getActivity().getApplicationContext());
                RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.pending_list_row, null);
                rb.setId(todaypendinglist.get(j).getId());
                rb.setTag(todaypendinglist.get(j));
                String date = getDateFormat(todaypendinglist.get(j).getScheduledDate());
                rb.setText(todaypendinglist.get(j).getDescription() + "  " + date);

                v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                v.setBackgroundColor(Color.parseColor("#eeeeee"));
                rg.addView(rb);
                rg.addView(v);

            }


            l1.addView(rg);


            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    for (int i = 0; i < todaylist.size(); i++) {
                        if (todaylist.get(i).getId() == checkedId) {
                            todaylist.get(i).setStatus("COMPLETED");
                            functionCall(view);
                            break;
                        }
                    }
                }
            });
        }
    }

    private void splilList() {
        todaycompletedlist.clear();
        todaypendinglist.clear();
        for (int i = 0; i < todaylist.size(); i++) {
            if (todaylist.get(i).getStatus().equalsIgnoreCase("COMPLETED"))
                todaycompletedlist.add(todaylist.get(i));
            else
                todaypendinglist.add(todaylist.get(i));

        }
    }

    private String getDateFormat(String scheduledDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(scheduledDate));
        return formatter.format(calendar.getTime());

    }
}
