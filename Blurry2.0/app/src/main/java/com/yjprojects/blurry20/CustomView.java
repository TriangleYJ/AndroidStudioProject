package com.yjprojects.blurry20;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View implements View.OnTouchListener{
    Paint mPaint;
    RectF mRectF;

    public CustomView(Context context) {
        super(context);
        mPaint = new Paint();
        mRectF = new RectF();
    }

    public CustomView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaint = new Paint();
        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mRectF.right = getWidth();
        mRectF.bottom = getHeight();

        mPaint.reset();
        mPaint.setAntiAlias(true);
        /*
        BitmapShader shader = new BitmapShader(blurredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.postScale(mRectF.width() / blurredBitmap.getWidth(), mRectF.height() / blurredBitmap.getHeight());
        shader.setLocalMatrix(matrix);
        mPaint.setShader(shader);
        canvas.drawOval(mRectF, mPaint);
        */

        mRectF.left = dp(15);
        mRectF.top = dp(15);
        mRectF.right = getWidth() - dp(15);
        mRectF.bottom = getHeight() - dp(15);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#44FFFFFF"));
        canvas.drawOval(mRectF, mPaint);

        mRectF.left = dp(10);
        mRectF.top = dp(10);
        mRectF.right = getWidth() - dp(10);
        mRectF.bottom = getHeight() - dp(10);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#AAFFFFFF"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dp(10));
        canvas.drawOval(mRectF, mPaint);

        mRectF.left = dp(90);
        mRectF.top = dp(90);
        mRectF.right = getWidth() - dp(90);
        mRectF.bottom = getHeight() - dp(90);


        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setTextSize(dp(150));
        mPaint.setTextAlign(Paint.Align.CENTER);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));
        canvas.drawText("3", xPos, yPos, mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#BBFFFFFF"));
        mPaint.setTextSize(dp(150));
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("3", xPos, yPos, mPaint);

        //canvas.drawBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_add_box_white_48dp)).getBitmap(), null, mRectF, mPaint);

    }

    public static int dp(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density) / 5;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return false;
    }
}
