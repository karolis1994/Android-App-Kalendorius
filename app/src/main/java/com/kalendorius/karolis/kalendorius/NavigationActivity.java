package com.kalendorius.karolis.kalendorius;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.graphics.Color.parseColor;

/**
 * Created by Karolis on 15/11/12.
 */
public class NavigationActivity extends TabActivity{

    private TabHost tabHost;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        title = (TextView) findViewById(R.id.navigationTitle);
        tabHost = getTabHost();

        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(this, HomeActivity.class);
        spec = tabHost.newTabSpec("a1")
                .setIndicator("Kalendorius")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent(this, NoteListActivity.class);
        spec = tabHost.newTabSpec("a2")
                .setIndicator("Užrašai")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent(this, EventListActivity.class);
        spec = tabHost.newTabSpec("a3")
                .setIndicator("Įvykiai")
                .setContent(intent);
        tabHost.addTab(spec);

        TextView x = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        x.setTextSize(10);
        x = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        x.setTextSize(10);
        x = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        x.setTextSize(10);

        tabHost.getTabWidget().getChildAt(0).getBackground().setColorFilter(parseColor("#CEFFC2"), PorterDuff.Mode.SRC);
        tabHost.getTabWidget().getChildAt(1).getBackground().setColorFilter(parseColor("#CEFFC2"), PorterDuff.Mode.SRC);
        tabHost.getTabWidget().getChildAt(2).getBackground().setColorFilter(parseColor("#CEFFC2"), PorterDuff.Mode.SRC);
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).getBackground().setColorFilter(parseColor("#CEFFC2"), PorterDuff.Mode.SRC);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("a1")) {
                    title.setText("Kalendorius");
                } else if (tabId.equals("a2")) {
                    title.setText("Užrašai");
                } else {
                    title.setText("Įvykiai");
                }
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).getBackground().setColorFilter(parseColor("#CEFFC2"), PorterDuff.Mode.SRC);
            }
        });
    }

}
