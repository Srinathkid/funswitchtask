package com.srinath.funswitchtask;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class LaterFragment extends Fragment {
    private ArrayList<Model> laterlist = new ArrayList<>();
    private ArrayList<Model> latercompletedlist = new ArrayList<>();
    private ArrayList<Model> laterpendinglist = new ArrayList<>();
    RadioGroup rg;

    public LaterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_later_fragment, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            laterlist = bundle.getParcelableArrayList("todolist");

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

        for (int j = 0; j < latercompletedlist.size(); j++) {
            View v = new View(getActivity().getApplicationContext());
            RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.completed_list_row, null);
            rb.setId(latercompletedlist.get(j).getId());
            rb.setTag(latercompletedlist.get(j));

            String date = getDateFormat(latercompletedlist.get(j).getScheduledDate());
            rb.setText(latercompletedlist.get(j).getDescription() + "  " + date);
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

        if (laterpendinglist.size() > 0) {
            for (int j = 0; j < laterpendinglist.size(); j++) {
                LinearLayout l3 = new LinearLayout(getActivity().getApplicationContext());
                LinearLayout.LayoutParams date_params = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams rb_params = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                l3.setOrientation(LinearLayout.HORIZONTAL);

                View v = new View(getActivity().getApplicationContext());
                RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.pending_list_row, null);
                rb.setId(laterpendinglist.get(j).getId());
                rb.setTag(laterpendinglist.get(j));
                String date = getDateFormat(laterpendinglist.get(j).getScheduledDate());
                rb.setText(laterpendinglist.get(j).getDescription() + "  " + date);

                v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                v.setBackgroundColor(Color.parseColor("#eeeeee"));
                rg.addView(rb);
                rg.addView(v);

            }


            l1.addView(rg);


            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    for (int i = 0; i < laterlist.size(); i++) {
                        if (laterlist.get(i).getId() == checkedId) {
                            laterlist.get(i).setStatus("COMPLETED");
                            functionCall(view);
                            break;
                        }
                    }
                }
            });
        }
    }

    private void splilList() {
        latercompletedlist.clear();
        laterpendinglist.clear();
        for (int i = 0; i < laterlist.size(); i++) {
            if (laterlist.get(i).getStatus().equalsIgnoreCase("COMPLETED"))
                latercompletedlist.add(laterlist.get(i));
            else
                laterpendinglist.add(laterlist.get(i));

        }
    }

    private String getDateFormat(String scheduledDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(scheduledDate));
        return formatter.format(calendar.getTime());

    }
}
