package com.kalendorius.karolis.kalendorius;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import static android.graphics.Color.parseColor;

/**
 * Created by Karolis on 15/10/25.
 */
public class ListFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        int[] colors = {parseColor("#CEFFC2"), parseColor("#3FA9F5") , parseColor("#CEFFC2")};
        ListView myList = (ListView) view.findViewById(R.id.listElements);
        myList.setDivider(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors));
        myList.setDividerHeight(3);
        return view;
    }

}
