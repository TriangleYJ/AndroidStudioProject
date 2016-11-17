package com.yjprojects.blurry20;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.github.mmin18.widget.RealtimeBlurView;


public class RingBlurView extends RealtimeBlurView{
    Paint mPaint;
    RectF mRectF;
    int xPos, yPos;
    BitmapShader shader;

    private float mRingBlurStrokeWidth;
    private float mRingBlurObjectSize;
    private String mRingBlurText;
    private int mRingBlurObjectColor;
    private Drawable mRingBlurImageDrawable;
    private Bitmap mRingBlurImageBitmap;
    private boolean onPress = false;

    public RingBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mRectF = new RectF();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RingBlurView);
        mRingBlurStrokeWidth = a.getDimension(R.styleable.RingBlurView_ringBlurStrokeWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics()));
        mRingBlurText = a.getString(R.styleable.RingBlurView_ringBlurText);
        mRingBlurObjectColor = a.getColor(R.styleable.RingBlurView_ringBlurObjectColor, 0x00000000);
        /*mRingBlurImageDrawable = a.getDrawable(R.styleable.RingBlurView_ringBlurImg);*/
        mRingBlurObjectSize = a.getDimension(R.styleable.RingBlurView_ringBlurObjectSize, TypedValue.applyDimension(mRingBlurImageDrawable == null ? TypedValue.COMPLEX_UNIT_SP : TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics()));
        a.recycle();
    }

    /**
     * Custom ring shape
     */

    @Override
    protected void drawBlurredBitmap(Canvas canvas, Bitmap blurredBitmap, int overlayColor) {
        if (blurredBitmap != null) {
            if(!onPress) {
                mRectF.left = mRingBlurStrokeWidth / 2;
                mRectF.top = mRingBlurStrokeWidth / 2;
                mRectF.right = getWidth() - mRingBlurStrokeWidth / 2;
                mRectF.bottom = getHeight() - mRingBlurStrokeWidth / 2;

                shader = new BitmapShader(blurredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                matrix.postScale(getWidth() / blurredBitmap.getWidth(), getHeight() / blurredBitmap.getHeight());
                shader.setLocalMatrix(matrix);


                mPaint.reset();
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(mRingBlurStrokeWidth);
                mPaint.setShader(shader);
                canvas.drawOval(mRectF, mPaint);

                mPaint.reset();
                mPaint.setAntiAlias(true);
                mPaint.setColor(overlayColor);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(mRingBlurStrokeWidth);
                canvas.drawOval(mRectF, mPaint);
            } else {
                mRectF.left = 0;
                mRectF.top = 0;
                mRectF.right = getWidth();
                mRectF.bottom = getHeight();

                shader = new BitmapShader(blurredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                matrix.postScale(mRectF.width() / blurredBitmap.getWidth(), mRectF.height() / blurredBitmap.getHeight());
                shader.setLocalMatrix(matrix);

                mPaint.reset();
                mPaint.setAntiAlias(true);
                mPaint.setShader(shader);
                canvas.drawOval(mRectF, mPaint);

                mPaint.reset();
                mPaint.setAntiAlias(true);
                mPaint.setColor(overlayColor);
                canvas.drawOval(mRectF, mPaint);
            }

            if(mRingBlurText != null){
                if(!(Color.alpha(mRingBlurObjectColor) == 255)){
                    mPaint.reset();
                    mPaint.setAntiAlias(true);
                    mPaint.setTextSize(mRingBlurObjectSize);
                    mPaint.setShader(shader);
                    mPaint.setTextAlign(Paint.Align.CENTER);
                    xPos = (canvas.getWidth() / 2);
                    yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));
                    canvas.drawText(mRingBlurText, xPos, yPos, mPaint);
                }

                mPaint.reset();
                mPaint.setAntiAlias(true);
                mPaint.setTextSize(mRingBlurObjectSize);
                mPaint.setColor(mRingBlurObjectColor);
                mPaint.setTextAlign(Paint.Align.CENTER);
                xPos = (canvas.getWidth() / 2);
                yPos = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));
                canvas.drawText(mRingBlurText, xPos, yPos, mPaint);
            }
            /*else if(mRingBlurImageDrawable != null){
                if(mRingBlurImageBitmap == null) mRingBlurImageBitmap = Bitmap.createScaledBitmap(drawableToBitmap(mRingBlurImageDrawable), (int)mRingBlurObjectSize, (int)mRingBlurObjectSize, true);
                if(!(Color.alpha(mRingBlurObjectColor) == 255)){
                    mPaint.reset();
                    mPaint.setAntiAlias(true);
                    xPos = (canvas.getWidth() - mRingBlurImageBitmap.getWidth())/2;
                    yPos = (canvas.getHeight() - mRingBlurImageBitmap.getHeight())/2;
                    canvas.drawBitmap(mRingBlurImageBitmap, xPos, yPos, mPaint);
                }

                mPaint.reset();
                mPaint.setAntiAlias(true);
                //mPaint.setColor(mRingBlurObjectColor);
                ColorFilter filter = new LightingColorFilter(mRingBlurObjectColor, 0);
                mPaint.setColorFilter(filter);
                mPaint.setDither(true);
                xPos = (canvas.getWidth() - mRingBlurImageBitmap.getWidth())/2;
                yPos = (canvas.getHeight() - mRingBlurImageBitmap.getHeight())/2;
                canvas.drawBitmap(mRingBlurImageBitmap, xPos, yPos, mPaint);
            }*/
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN :
                onPress = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                onPress = false;
                invalidate();
                break;
        }
        return true;
    }

   /* public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }*/

}

