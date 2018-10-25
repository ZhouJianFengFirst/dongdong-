package com.xiangri.dongdong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class WaterView extends View {
    private Path pathTop;
    private Path pathButtom;
    private float φ;
    private Paint mPaintTop;
    private Paint mPaintButtom;
 
    public WaterView(Context context) {
        super(context);
        init(context);
    }
 
    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
 
    public WaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
 
    private void init(Context context) {
        //初始化画笔
        mPaintTop = new Paint();
        mPaintTop.setColor(Color.parseColor("#d43c3c"));
        mPaintTop.setAntiAlias(true);
 
        mPaintButtom = new Paint();
        mPaintButtom.setColor(Color.parseColor("#0000ff"));
        mPaintButtom.setAntiAlias(true);
        mPaintButtom.setAlpha(60);
 
        //初始化路径
        pathTop = new Path();
        pathButtom = new Path();
 
    }
 
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
 
        pathTop.reset();
        pathButtom.reset();
 
        pathTop.moveTo(getLeft(), getBottom() - 50);
        pathButtom.moveTo(getLeft(), getBottom() - 50);
 
        φ -= 0.1f;
        float xM = (float) (Math.PI * 2 / getWidth());
        for (int x = 0; x <= getWidth(); x += 20) {
 
            float y = (float) (10 * Math.cos(xM * x + φ) + 10);
            pathTop.lineTo(x, y);
            pathButtom.lineTo(x, (float) (10 * Math.sin(xM * x + φ)));
            lisener.getHeight((int) (10 * Math.sin(xM * x + φ)));
        }
 
 
        pathTop.lineTo(getRight(), getBottom() - 50);
        pathButtom.lineTo(getRight(), getBottom() - 50);
 
        canvas.drawPath(pathTop, mPaintTop);
        canvas.drawPath(pathButtom, mPaintButtom);
        postInvalidateDelayed(20);
    }
 
    private AnimalLisener lisener;
 
    public void setLisener(AnimalLisener lisener) {
        this.lisener = lisener;
    }
 
    public interface AnimalLisener {
        void getHeight(int y);
    }
}