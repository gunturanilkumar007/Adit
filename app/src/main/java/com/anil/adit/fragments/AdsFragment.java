package com.anil.adit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.anil.adit.R;
import com.anil.adit.activities.BookActivity;
import com.anil.adit.activities.VideosListActivity;
import com.anil.adit.puzzle.PuzzleActivity;


public class AdsFragment extends Fragment {
    private LinearLayout videosLayout,puzzleLayout,bookLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ads, container, false);
        videosLayout=view.findViewById(R.id.videos);
        puzzleLayout=view.findViewById(R.id.puzzle);
        bookLayout=view.findViewById(R.id.book_layout);
        videosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),VideosListActivity.class));
            }
        });
        puzzleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PuzzleActivity.class));
            }
        });
        bookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BookActivity.class));
            }
        });
        return view;
    }

}
