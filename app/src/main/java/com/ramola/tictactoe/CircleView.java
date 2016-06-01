package com.ramola.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class CircleView extends View {

    private int backgroundColor, textColor, width, minWidth, height, minHeight;
    private String text;
    private Paint paint;

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }


    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);
        backgroundColor = a.getColor(R.styleable.CircleView_backgroundColor, getResources().getColor(R.color.colorAccent));
        textColor = a.getColor(R.styleable.CircleView_textColor, getResources().getColor(R.color.colorPrimaryDark));
        text = a.getString(R.styleable.CircleView_text);
        a.recycle();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        minWidth = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        width = resolveSizeAndState(minWidth, widthMeasureSpec, 1);
        minHeight = MeasureSpec.getSize(width) + getPaddingTop() + getPaddingBottom();
        height = resolveSizeAndState(minHeight, heightMeasureSpec, 0);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textX =0, textY =0;
        int xCoordinate = getMeasuredWidth() / 2;
        int yCoordinate = getMeasuredHeight() / 2;
        int raidus = 0;
        if (xCoordinate < yCoordinate) {
            raidus = xCoordinate;
            textY = yCoordinate;
            textX=xCoordinate;
        } else {
            raidus = yCoordinate;
            textX = xCoordinate;
            textY=yCoordinate;
        }

      paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(xCoordinate-raidus,yCoordinate-raidus,xCoordinate+raidus,yCoordinate+raidus,paint);
        paint.setColor(backgroundColor);
        canvas.drawCircle(xCoordinate, yCoordinate, raidus, paint);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(50f);
        canvas.drawText(text,textX,textY,paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(xCoordinate-raidus,yCoordinate-raidus,xCoordinate+raidus,yCoordinate+raidus,paint);

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
