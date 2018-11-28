package com.anil.adit.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anil.adit.R;
import com.anil.adit.activities.OffersDetailsActivity;
import com.anil.adit.beans.MainOffersBean;
import com.anil.adit.beans.OffersBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class OffersFragment extends Fragment {
    private ViewPager mPager;
    private CircleIndicator indicator;
    private ArrayList<Integer> list;
    private int currentPage = 0;
    private RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_offers, container, false);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        recyclerView=view.findViewById(R.id.menu_listView);
        list=new ArrayList<>();
        list.add(R.drawable.wood);
        list.add(R.drawable.three);
        list.add(R.drawable.wood);
        list.add(R.drawable.three);
        mPager.setAdapter(new MyAdapter(getActivity(), (ArrayList<Integer>) list));
        indicator.setViewPager(mPager);

        recyclerView.setAdapter(new RecyclerAdapter(getActivity(),getOffers()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == list.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 5000);


        return view;
    }
    public class MyAdapter extends PagerAdapter {

        private ArrayList<Integer> images;
        private LayoutInflater inflater;
        private Context contextt;

        public MyAdapter(Context context, ArrayList<Integer> images) {
            this.contextt = context;
            this.images = images;
            inflater = LayoutInflater.from(contextt);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View myImageLayout = inflater.inflate(R.layout.slide, view, false);
            ImageView myImage = (ImageView) myImageLayout
                    .findViewById(R.id.image);
            myImage.setImageResource(images.get(position));
            view.addView(myImageLayout, 0);
            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

    public List<MainOffersBean> getOffers(){
        List<MainOffersBean> mainOffersBeans=new ArrayList<>();
        List<OffersBean> o11=new ArrayList<>();
        OffersBean o1=new OffersBean();
        o1.setCompanyName("Pantaloon");
        o1.setIcon(R.drawable.pantaloons);
        o1.setDesc("Earn 15% SuperCash @ Pantaloons outlets!");
        o11.add(o1);

        OffersBean o2=new OffersBean();
        o2.setCompanyName("BF");
        o2.setIcon(R.drawable.bf);
        o2.setDesc("Earn 25% SuperCash @ BF outlets!");
        o11.add(o2);

        OffersBean o3=new OffersBean();
        o3.setCompanyName("Central");
        o3.setIcon(R.drawable.central);
        o3.setDesc("Earn 15% SuperCash @ Central outlets!");
        o11.add(o3);
        o11.add(o1);
        o11.add(o2);
        o11.add(o3);
        MainOffersBean m=new MainOffersBean();
        m.setCtagory("Shopping Stores");
        m.setOffers(o11);
        MainOffersBean m1=new MainOffersBean();
        m1.setCtagory("Food");
        m1.setOffers(o11);
        MainOffersBean m2=new MainOffersBean();
        m2.setCtagory("Health");
        m2.setOffers(o11);
        MainOffersBean m3=new MainOffersBean();
        m3.setCtagory("Stores Near You");
        m3.setOffers(o11);
        MainOffersBean m4=new MainOffersBean();
        m4.setCtagory("New User offers");
        m4.setOffers(o11);

        mainOffersBeans.add(m);
        mainOffersBeans.add(m1);
        mainOffersBeans.add(m2);
        mainOffersBeans.add(m3);
        mainOffersBeans.add(m4);

        return mainOffersBeans;

    }
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerMainHolder>{
        Context context;
        List<MainOffersBean> mainOffersBeans;
        LayoutInflater inflater;

        public RecyclerAdapter(Context context1, List<MainOffersBean> mainOffersBeans) {
            this.context = context1;
            this.mainOffersBeans = mainOffersBeans;
            inflater=LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public RecyclerMainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new RecyclerMainHolder(inflater.inflate(R.layout.main_offers_layout,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerMainHolder recyclerMainHolder, int i) {
            MainOffersBean mainOffersBean=mainOffersBeans.get(i);
            recyclerMainHolder.catName.setText(mainOffersBean.getCtagory());
            recyclerMainHolder.subRecyclerView.setAdapter(new SubRecyclerAdapter(getActivity(),mainOffersBeans.get(i).getOffers()));
            recyclerMainHolder.subRecyclerView.setHasFixedSize(true);
            recyclerMainHolder.subRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        }

        @Override
        public int getItemCount() {
            return mainOffersBeans.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }
    public class RecyclerMainHolder extends RecyclerView.ViewHolder{
        TextView catName;
        RecyclerView subRecyclerView;
        public RecyclerMainHolder(@NonNull View itemView) {
            super(itemView);
            catName=itemView.findViewById(R.id.cat_id);
            subRecyclerView=itemView.findViewById(R.id.sub_recycler);
        }
    }
    public class SubRecyclerAdapter extends RecyclerView.Adapter<SubRecyclerMainHolder>{
        Context context;
        List<OffersBean> mainOffersBeans;
        LayoutInflater inflater;

        public SubRecyclerAdapter(Context context1, List<OffersBean> mainOffersBeans) {
            this.context = context1;
            this.mainOffersBeans = mainOffersBeans;
            inflater=LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public SubRecyclerMainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new SubRecyclerMainHolder(inflater.inflate(R.layout.sub_main_offers_layout,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull SubRecyclerMainHolder recyclerMainHolder, final int i) {
            OffersBean offersBean=mainOffersBeans.get(i);
            recyclerMainHolder.desc.setText(offersBean.getDesc());
            recyclerMainHolder.catName.setText(offersBean.getCompanyName());
            recyclerMainHolder.imageView.setImageResource(offersBean.getIcon());
            recyclerMainHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OffersBean offersBean=mainOffersBeans.get(i);
                    Intent intent=new Intent(getActivity(),OffersDetailsActivity.class);
                    intent.putExtra("cName",offersBean.getCompanyName());
                    intent.putExtra("desc",offersBean.getDesc());
                    intent.putExtra("img",offersBean.getIcon());
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mainOffersBeans.size();
        }
    }
    public class SubRecyclerMainHolder extends RecyclerView.ViewHolder{
        TextView catName,desc;
        ImageView imageView;
        public SubRecyclerMainHolder(@NonNull View itemView) {
            super(itemView);
            catName=itemView.findViewById(R.id.sub_cat_name);
            imageView=itemView.findViewById(R.id.sub_image);
            desc=itemView.findViewById(R.id.sub_desc);
        }
    }

}
