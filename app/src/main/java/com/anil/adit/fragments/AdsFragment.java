package com.anil.adit.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anil.adit.R;
import com.anil.adit.activities.BookActivity;
import com.anil.adit.activities.VideosListActivity;
import com.anil.adit.beans.GenericBean;
import com.anil.adit.puzzle.PuzzleActivity;

import java.util.ArrayList;
import java.util.List;


public class AdsFragment extends Fragment {

    private List<GenericBean> genericBeanList;
    private GenericAdapter genericAdapter;
    private RecyclerView genericRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ads, container, false);

        genericRecycler = (RecyclerView) view.findViewById(R.id.generic_recycler);
        getList();

        genericAdapter = new GenericAdapter(getContext(), genericBeanList);
        genericRecycler.setAdapter(genericAdapter);
        genericRecycler.setHasFixedSize(true);
        genericRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));


        return view;
    }

    private void getList() {
        genericBeanList = new ArrayList<>();

        GenericBean gb1 = new GenericBean();
        gb1.setType("videos");
        gb1.setValue("VIDEOS");
        gb1.setId("1");
        genericBeanList.add(gb1);

        GenericBean gb2 = new GenericBean();
        gb2.setType("pages");
        gb2.setValue("PAGES");
        gb2.setId("2");
        genericBeanList.add(gb2);

        GenericBean gb3 = new GenericBean();
        gb3.setType("games");
        gb3.setValue("GAMES");
        gb3.setId("3");
        genericBeanList.add(gb3);
    }

    public class GenericAdapter extends RecyclerView.Adapter<HourHolder> {
        Context context;
        List<GenericBean> genericBeanList1;
        LayoutInflater inflater;

        public GenericAdapter(Context context, List<GenericBean> list) {
            this.context = context;
            this.genericBeanList1 = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public HourHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.hou_slot_item, parent, false);
            HourHolder holder = new HourHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(HourHolder holder, final int position) {
            GenericBean gb1 = genericBeanList1.get(position);
            holder.genericName.setText(gb1.getValue());

            if(gb1.getType().equalsIgnoreCase("videos")){
                startActivity(new Intent(getActivity(),VideosListActivity.class));
            }else if(gb1.getType().equalsIgnoreCase("games")){
                startActivity(new Intent(getActivity(),PuzzleActivity.class));
            }else if(gb1.getType().equalsIgnoreCase("pages")){
                startActivity(new Intent(getActivity(),BookActivity.class));
            }

        }

        @Override
        public int getItemCount() {
            return genericBeanList1.size();
        }
    }

    public class HourHolder extends RecyclerView.ViewHolder {
        TextView genericName;
        RelativeLayout genericLayout;

        public HourHolder(View itemView) {
            super(itemView);
            genericName = (TextView) itemView.findViewById(R.id.generic_name);
            genericLayout = (RelativeLayout) itemView.findViewById(R.id.generic_layout);
        }
    }
}
