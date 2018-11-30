package com.anil.adit.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anil.adit.R;
import com.anil.adit.activities.BookActivity;
import com.anil.adit.activities.VideosListActivity;
import com.anil.adit.beans.GenericBean;
import com.anil.adit.puzzle.PuzzleActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;


public class AdsFragment extends Fragment implements RewardedVideoAdListener {

    private List<GenericBean> genericBeanList;
    private GenericAdapter genericAdapter;
    private RecyclerView genericRecycler;
    private RewardedVideoAd rewardedVideoAd;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final String APP_ID = "ca-app-pub-3940256099942544~3347511713";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ads, container, false);
        MobileAds.initialize(getActivity(), APP_ID);
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
        genericRecycler = (RecyclerView) view.findViewById(R.id.generic_recycler);
        getList();

        genericAdapter = new GenericAdapter(getContext(), genericBeanList);
        genericRecycler.setAdapter(genericAdapter);
        genericRecycler.setHasFixedSize(true);
        genericRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));


        return view;
    }
    @Override
    public void onPause() {
        super.onPause();

        rewardedVideoAd.pause(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        rewardedVideoAd.resume(getActivity());
    }
    private void getList() {
        genericBeanList = new ArrayList<>();

        GenericBean gb1 = new GenericBean();
        gb1.setType("videos");
        gb1.setValue("Videos");
        gb1.setId("1");
        gb1.setImage(R.drawable.video);
        genericBeanList.add(gb1);

        GenericBean gb2 = new GenericBean();
        gb2.setType("pages");
        gb2.setValue("Pages");
        gb2.setId("2");
        gb2.setImage(R.drawable.book);
        genericBeanList.add(gb2);

        GenericBean gb3 = new GenericBean();
        gb3.setType("games");
        gb3.setValue("Puzzle");
        gb3.setId("3");
        gb3.setImage(R.drawable.game);
        genericBeanList.add(gb3);
        GenericBean gb4 = new GenericBean();
        gb4.setType("gAds");
        gb4.setValue("Google Ads");
        gb4.setId("3");
        gb4.setImage(R.drawable.ads);
        genericBeanList.add(gb4);
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
            holder.imageView.setImageResource(gb1.getImage());
            holder.genericLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GenericBean gb1 = genericBeanList1.get(position);
                    if (gb1.getType().equalsIgnoreCase("videos")) {
                        startActivity(new Intent(getActivity(), VideosListActivity.class));
                    } else if (gb1.getType().equalsIgnoreCase("games")) {
                        startActivity(new Intent(getActivity(), PuzzleActivity.class));
                    } else if (gb1.getType().equalsIgnoreCase("pages")) {
                        startActivity(new Intent(getActivity(), BookActivity.class));
                    } else if (gb1.getType().equalsIgnoreCase("gAds")) {
                        showRewardedVideo();
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return genericBeanList1.size();
        }
    }

    public class HourHolder extends RecyclerView.ViewHolder {
        TextView genericName;
        LinearLayout genericLayout;
        ImageView imageView;

        public HourHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.sub_image);
            genericName = (TextView) itemView.findViewById(R.id.generic_name);
            genericLayout =  itemView.findViewById(R.id.generic_layout);
        }
    }
    private void loadRewardedVideoAd() {
        if (!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }
    private void showRewardedVideo() {

        if (rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.show();
        }else {
            Toast.makeText(getActivity(), "Please wait some time..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        askQuation();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    public void askQuation() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.ask_quation_layout, null);
        TextView ok = view.findViewById(R.id.ok);


        final Dialog mBottomSheetDialog2 = new Dialog(getActivity(), R.style.MaterialDialogSheetTrans);
        mBottomSheetDialog2.setContentView(view);
        mBottomSheetDialog2.setCancelable(false);
        mBottomSheetDialog2.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog2.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog2.cancel();
                addMoney();
            }
        });
    }

    public void addMoney() {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_money_to_wallet_layout, null);
        TextView ok = view.findViewById(R.id.ok);

        final Dialog mBottomSheetDialog1 = new Dialog(getActivity(), R.style.MaterialDialogSheetTrans);
        mBottomSheetDialog1.setContentView(view);
        mBottomSheetDialog1.setCancelable(false);
        mBottomSheetDialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog1.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog1.cancel();

            }
        });
    }

}
