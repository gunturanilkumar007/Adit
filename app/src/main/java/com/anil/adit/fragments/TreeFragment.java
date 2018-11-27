package com.anil.adit.fragments;

import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anil.adit.R;
import com.anil.adit.activities.MainActivity;
import com.anil.adit.utils.Constants;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;


public class TreeFragment extends Fragment {
    ImageView sun, cloud, tree, fertilizer, thermometer;
    FrameLayout treeLayout;

    Animation upAnim, fadeOut, fadeIn, backFadeIn;
    RelativeLayout layout, mountainLayout, lightMountain, yellowBackground;
    AnimationDrawable trans;
    MotionEvent event;
    private ParticleSystem ps, ps1;
    private int lastHeight;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View view = inflater.inflate(R.layout.fragment_tree, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_KEY, 0);
        editor = sharedPreferences.edit();
        lastHeight = sharedPreferences.getInt(Constants.TREE_HEIGHT, Constants.TREE_DURATION);

        sun = (ImageView) view.findViewById(R.id.sun_img);
        cloud = (ImageView) view.findViewById(R.id.cloud_img);
        tree = (ImageView) view.findViewById(R.id.tree_img);
        thermometer = (ImageView) view.findViewById(R.id.faren_img);
        fertilizer = (ImageView) view.findViewById(R.id.ferti_img);
        treeLayout = (FrameLayout) view.findViewById(R.id.tree_layout);

        expand(tree, 1000, lastHeight, lastHeight);
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ps.stopEmitting();
               // ps1.stopEmitting();
                sunKiranMethod(sun);

            }
        });

        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fallRainMethod(cloud);
                int pre = sharedPreferences.getInt(Constants.TREE_HEIGHT, 100);
                lastHeight = lastHeight + Constants.TREE_DURATION;
                expand(tree, 1000, lastHeight, pre);
                editor.putInt(Constants.TREE_HEIGHT, lastHeight).commit();

            }
        });

        thermometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temperature(tree);
                editor.putInt(Constants.TREE_HEIGHT, 0).clear().commit();
            }
        });


        fertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fertilizerFeed(tree);
            }
        });
        return view;
    }

    private void temperature(ImageView tree) {
        new ParticleSystem(getActivity(), 10, R.drawable.water, 3000)
                .setSpeedByComponentsRange(-0.1f, 0.1f, -0.1f, 0.02f)
                .setAcceleration(0.000003f, 90)
                .setInitialRotationRange(0, 360)
                .setRotationSpeed(120)
                .setFadeOut(2000)
                .addModifier(new ScaleModifier(0f, 1.5f, 0, 1500))
                .oneShot(tree, 10);
    }

    private void fertilizerFeed(ImageView tree) {
      /*  ParticleSystem ps = new ParticleSystem(getActivity(), 100, R.drawable.water, 800);
        ps.setScaleRange(0.7f, 1.3f);
        ps.setSpeedRange(0.05f, 0.1f);
        ps.setRotationSpeedRange(90, 180);
        ps.setFadeOut(200, new AccelerateInterpolator());
        ps.emit((int) tree.getX(), (int) tree.getY(), 40);*/

        ParticleSystem ps = new ParticleSystem(getActivity(), 100, R.drawable.water, 1000);
        ps.setScaleRange(0.7f, 1.3f);
        ps.setSpeedModuleAndAngleRange(0.07f, 0.16f, 0, 180);
        ps.setRotationSpeedRange(90, 180);
        ps.setAcceleration(0.00013f, 90);
        ps.setFadeOut(200, new AccelerateInterpolator());
        ps.emit(tree, 100, 2000);
    }

    private void sunKiranMethod(View v) {

        /*new ParticleSystem(getActivity(), 100, R.drawable.water, 5000)
                .setSpeedRange(0.1f, 0.25f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 360)
                .oneShot(v, 100);*/


       /* new ParticleSystem(getActivity(), 4, R.drawable.water, 3000)
                .setSpeedByComponentsRange(-0.025f, 0.025f, -0.06f, -0.08f)
                .setAcceleration(0.00001f, 30)
                .setInitialRotationRange(0, 360)
                .addModifier(new AlphaModifier(255, 0, 1000, 3000))
                .addModifier(new ScaleModifier(0.5f, 2f, 0, 1000))
                .oneShot(findViewById(R.id.tree_img), 4);*/

       /* new ParticleSystem(getActivity(), 50, R.drawable.water, 1000, R.id.tree_layout)
                .setSpeedRange(0.1f, 0.25f)
                .emit(tree, 100);*/

        fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        treeLayout.startAnimation(fadeOut);


        fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        treeLayout.startAnimation(fadeIn);


        upAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.up_anim);
        sun.startAnimation(upAnim);
        upAnim.setFillAfter(true);

        backFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.background_fadein);
        treeLayout.startAnimation(backFadeIn);

    }

    private void fallRainMethod(View v) {
       /* new ParticleSystem(getActivity(), 80, R.drawable.water, 10000)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setAcceleration(0.00005f, 120)
                .emitWithGravity(v, Gravity.TOP, 8);*/

      ps = new ParticleSystem(getActivity(), 80, R.drawable.dots, 10000);
        ps.setSpeedModuleAndAngleRange(0f, 0.1f, 140, 140);
        ps.setRotationSpeed(144);
        ps.setAcceleration(0.000017f, 90);
        ps.emit(v, 8);

        /*  ps1 = new ParticleSystem(getActivity(), 80, R.drawable.water, 10000);
        ps1.setSpeedModuleAndAngleRange(0f, 0.1f, 0, 0);
        ps1.setRotationSpeed(144);
        ps1.setAcceleration(0.000017f, 90);
        ps1.emit(v, 8);*/

    }

    public void expand(final View v, int duration, int targetHeight, int pre) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        v.getLayoutParams().height = pre;
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(pre, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }
}
