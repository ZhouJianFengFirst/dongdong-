package com.xiangri.dongdong.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangri.dongdong.R;

import java.util.ArrayList;
import java.util.List;

public class HorseView extends RelativeLayout {

    private Context mContext;
    private SimpleDraweeView horseImage;
    private TextView horseTile;
    private List<String> titles = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private int postion = 0;
    private AnimatorSet animatorSet;

    public void setData(List<String> titles,List<String> images) {
        this.titles = titles;
        this.images = images;
    }

    public HorseView(Context context) {
        super(context);
        init(context);
    }

    public HorseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_horse_view, null);
        horseImage = (SimpleDraweeView) view.findViewById(R.id.horse_image);
        horseTile = (TextView) view.findViewById(R.id.horse_title);
        setAnimation(view);
        addView(view);
    }

    private void setAnimation(final View view) {
        ObjectAnimator objectAnimatorTop = ObjectAnimator.ofFloat(view, "translationY", 0, -50);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, 0);
        ObjectAnimator objectAnimatorB = ObjectAnimator.ofFloat(view, "translationY", 50, 0);
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimator, objectAnimatorTop, objectAnimatorB);
        animatorSet.setDuration(1000);
        animatorSet.start();

        animatorSet.addListener(new BaseAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSet.setDuration(1000);
                animatorSet.start();
            }
        });

        objectAnimatorTop.addListener(new BaseAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                postion++;
                horseTile.setText(titles.get(postion % titles.size()));
                horseImage.setImageURI(images.get(postion % images.size()));
            }
        });
    }
}