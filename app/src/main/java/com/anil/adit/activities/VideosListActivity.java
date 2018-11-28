package com.anil.adit.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.anil.adit.R;
import com.anil.adit.beans.VideosBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideosListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private static final int REQUEST_PERMISSIONS = 100;
    ArrayList<VideosBean> al_video = new ArrayList<>();
    private static CountDownTimer countDownTimer;
    private TextView countdownTimerText;
    private Dialog mBottomSheetDialog, mBottomSheetDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Videos");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        fn_checkpermission();
    }

    private void fn_checkpermission() {
        /*RUN TIME PERMISSIONS*/

        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(VideosListActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(VideosListActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(VideosListActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Log.e("Else", "Else");
            fn_video();
        }
    }


    public void fn_video() {

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_id, thum;

        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media._ID, MediaStore.Video.Thumbnails.DATA};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null,
                orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));
            Log.e("column_id", cursor.getString(column_id));
            Log.e("thum", cursor.getString(thum));

            VideosBean obj_model = new VideosBean();
            obj_model.setBooleanSelected(false);
            obj_model.setStrPath(absolutePathOfImage);
            obj_model.setStrThumb(cursor.getString(thum));

            al_video.add(obj_model);

        }


        VideosAdapter obj_adapter = new VideosAdapter(VideosListActivity.this, al_video);
        recyclerView.setAdapter(obj_adapter);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_video();
                    } else {
                        Toast.makeText(VideosListActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public class VideosAdapter extends RecyclerView.Adapter<VideosHolder> {
        Context context;
        List<VideosBean> videosBeans;
        LayoutInflater inflater;

        public VideosAdapter(Context context, List<VideosBean> videosBeans) {
            this.context = context;
            this.videosBeans = videosBeans;
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public VideosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VideosHolder(inflater.inflate(R.layout.videos_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VideosHolder holder, final int position) {
            Glide.with(context).load("file://" + videosBeans.get(position).getStrThumb())
                    .skipMemoryCache(false)
                    .into(holder.iv_image);
            holder.rl_select.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.rl_select.setAlpha(0);


            holder.rl_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* Intent intent_gallery = new Intent(context,VideoPlayerActivity.class);
                    intent_gallery.putExtra("video",videosBeans.get(position).getStrPath());
                    startActivity(intent_gallery);*/
                    videoView(videosBeans.get(position).getStrPath());

                }
            });

        }

        @Override
        public int getItemCount() {
            return videosBeans.size();
        }
    }

    public class VideosHolder extends RecyclerView.ViewHolder {
        public ImageView iv_image;
        RelativeLayout rl_select;

        public VideosHolder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            rl_select = (RelativeLayout) itemView.findViewById(R.id.rl_select);
        }

    }

    public void videoView(String str_video) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_video_player, null);
        VideoView vv_video = view.findViewById(R.id.vv_video);
        countdownTimerText = view.findViewById(R.id.count_down);
        vv_video.setVideoPath(str_video);
        vv_video.start();
        MediaPlayer mpl = MediaPlayer.create(this, Uri.parse(str_video));
        int si = mpl.getDuration();
        startTimer(si / 2);
        countdownTimerText.setEnabled(false);
        mBottomSheetDialog = new Dialog(VideosListActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                askQuation();
            }
        });
        countdownTimerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();

            }
        });

    }

    public void askQuation() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.ask_quation_layout, null);
        TextView ok = view.findViewById(R.id.ok);


        mBottomSheetDialog2 = new Dialog(VideosListActivity.this, R.style.MaterialDialogSheetTrans);
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

        final Dialog mBottomSheetDialog1 = new Dialog(VideosListActivity.this, R.style.MaterialDialogSheetTrans);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
                mBottomSheetDialog.setCancelable(true);
                countdownTimerText.setText("CLOSE"); //On finish change timer text
                countdownTimerText.setEnabled(true);
                countDownTimer = null;//set CountDownTimer to null
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        super.onBackPressed();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
