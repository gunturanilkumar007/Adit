package com.anil.adit.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anil.adit.R;

import java.util.concurrent.TimeUnit;

public class BookActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private static CountDownTimer countDownTimer;
    private TextView countdownTimerText;
    Integer[] imageId = {R.drawable.eenadu_one, R.drawable.eenadu_two,R.drawable.eenadu_three};
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        getSupportActionBar().hide();
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        countdownTimerText = (TextView)findViewById(R.id.count_down);
        CustomAdapter customAdapter=new CustomAdapter(BookActivity.this,imageId);
        viewPager.setAdapter(customAdapter);
        startTimer(10000);
    }

    public class CustomAdapter extends PagerAdapter {

        private Activity activity;
        private Integer[] imagesArray;


        public CustomAdapter(Activity activity,Integer[] imagesArray){

            this.activity = activity;
            this.imagesArray = imagesArray;

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = ((Activity)activity).getLayoutInflater();

            View viewItem = inflater.inflate(R.layout.image_item_layout, container, false);
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
            imageView.setImageResource(imagesArray[position]);

            ((ViewPager)container).addView(viewItem);

            return viewItem;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imagesArray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == ((View)object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView((View) object);
        }
    }
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%2d",
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {
                count++;
                if(count<imageId.length) {
                    viewPager.setCurrentItem(count, true);
                    startTimer(5000);
                }else {
                    /*countdownTimerText.setText("CLOSE"); //On finish change timer text
                    countdownTimerText.setEnabled(true);*/
                    askQuation();
                    countDownTimer = null;//set CountDownTimer to null
                }
            }
        }.start();

    }
    public void askQuation() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.ask_quation_layout, null);
        TextView ok = view.findViewById(R.id.ok);


        final Dialog mBottomSheetDialog2 = new Dialog(BookActivity.this, R.style.MaterialDialogSheetTrans);
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

        final Dialog mBottomSheetDialog1 = new Dialog(BookActivity.this, R.style.MaterialDialogSheetTrans);
        mBottomSheetDialog1.setContentView(view);
        mBottomSheetDialog1.setCancelable(false);
        mBottomSheetDialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog1.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog1.cancel();
                finish();
            }
        });
    }
}
