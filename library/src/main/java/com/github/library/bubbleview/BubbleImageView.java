package com.github.library.bubbleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.github.bubbleview.R;


/**
 * Created by lgp on 2015/3/25.
 */
public class BubbleImageView extends ImageView {
    private BubbleDrawable bubbleDrawable;
    private Drawable sourceDrawable;
    private float mArrowWidth;
    private float mAngle;
    private float mArrowHeight;
    private float mArrowPosition;
    private Bitmap mBitmap;
    private BubbleDrawable.ArrowLocation mArrowLocation;
    private boolean mArrowCenter;
    public BubbleImageView(Context context) {
        super(context);
        initView(null);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    private void initView(AttributeSet attrs){
        if (attrs != null){
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);
            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth,
                    BubbleDrawable.Builder.DEFAULT_ARROW_WITH);
            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight,
                    BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
            mAngle = array.getDimension(R.styleable.BubbleView_angle,
                    BubbleDrawable.Builder.DEFAULT_ANGLE);
            mArrowPosition = array.getDimension(R.styleable.BubbleView_arrowPosition,
                    BubbleDrawable.Builder.DEFAULT_ARROW_POSITION);
            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
            mArrowLocation = BubbleDrawable.ArrowLocation.mapIntToValue(location);
            mArrowCenter = array.getBoolean(R.styleable.BubbleView_arrowCenter, false);
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width <= 0 && height > 0){
            setMeasuredDimension(height , height);
        }
        if (height <= 0 && width > 0){
            setMeasuredDimension(width , width);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0){
            setUp(w, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setUp();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.getSaveCount();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        if (bubbleDrawable != null)
            bubbleDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    private void setUp(int left, int right, int top, int bottom){
        if (right <= left || bottom <= top)
            return;

        RectF rectF = new RectF(left, top, right, bottom);
        if (sourceDrawable != null)
            mBitmap = getBitmapFromDrawable(sourceDrawable);
        bubbleDrawable = new BubbleDrawable.Builder()
                .rect(rectF)
                .arrowLocation(mArrowLocation)
                .angle(mAngle)
                .arrowHeight(mArrowHeight)
                .arrowWidth(mArrowWidth)
                .bubbleType(BubbleDrawable.BubbleType.BITMAP)
                .arrowPosition(mArrowPosition)
                .bubbleBitmap(mBitmap)
                .arrowCenter(mArrowCenter)
                .build();
    }

    private void setUp(int width, int height){
        setUp(getPaddingLeft(), width - getPaddingRight(),
                getPaddingTop(), height - getPaddingBottom());
    }

    private void setUp(){
        int width = getWidth();
        int height = getHeight();
        int scale;

        if (width > 0 && height <= 0 && sourceDrawable != null){
            if (sourceDrawable.getIntrinsicWidth() >= 0){
                scale = width / sourceDrawable.getIntrinsicWidth();
                height = scale * sourceDrawable.getIntrinsicHeight();
            }
        }

        if (height > 0 &&  width <= 0 && sourceDrawable != null){
            if (sourceDrawable.getIntrinsicHeight() >= 0){
                scale = height / sourceDrawable.getIntrinsicHeight();
                width = scale * sourceDrawable.getIntrinsicWidth();
            }
        }
        setUp(width, height);
    }

    @Override
    public void setImageBitmap(Bitmap mBitmap) {
        if (mBitmap == null)
            return;
        this.mBitmap = mBitmap;
        sourceDrawable = new BitmapDrawable(getResources(), mBitmap);
        setUp();
        super.setImageDrawable(bubbleDrawable);
    }

    @Override
    public void setImageDrawable(Drawable drawable){
        if (drawable == null )
            return;
        sourceDrawable = drawable;
        setUp();
        super.setImageDrawable(bubbleDrawable);
    }

    @Override
    public void setImageResource(int res){
        setImageDrawable(getDrawable(res));
    }

    private Drawable getDrawable(int res){
        if (res == 0){
            throw new IllegalArgumentException("getDrawable res can not be zero");
        }
        return getContext().getResources().getDrawable(res);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        return getBitmapFromDrawable(getContext(), drawable, getWidth(), getWidth(), 25);
    }

    public static Bitmap getBitmapFromDrawable(Context mContext, Drawable drawable, int width, int height, int defaultSize) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if (width > 0 && height > 0){
                bitmap = Bitmap.createBitmap(width,
                        height, Bitmap.Config.ARGB_8888);
            }else{
                bitmap = Bitmap.createBitmap(dp2px(mContext, defaultSize),
                        dp2px(mContext, defaultSize), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
