package com.ramola.tictactoe;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {
    private int rows, column, width, minWidth, height, minHeight,moves;
    private boolean hasTouch;
    private Paint paint;
    private Player player1, player2;
    private   String result;
    private  resultListener resultListener;

    private void init() {
        moves=0;
        result="";
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        player1 = new Player("Sahil", Color.RED);
        player2 = new Player("Mohit", Color.GREEN);
        player1.setHasPlayed(true);
        player2.setHasPlayed(false);
    }

    public BoardView(Context context) {
        super(context);
        init();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BoardView, 0, 0);
        rows = a.getInt(R.styleable.BoardView_rows, 3);
        column = a.getInt(R.styleable.BoardView_column, 3);
        a.recycle();
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
        if (hasTouch) {
            for (point p : player1.list)
                drawRect(canvas, p.x, p.y, player1.getColor());
            for (point p : player2.list)
                drawRect(canvas, p.x, p.y, player2.getColor());
            hasTouch = false;
        }
        paint.setColor(Color.BLACK);
        for (int i = getPaddingLeft(); i <= (getMeasuredWidth() - getPaddingRight() - getPaddingLeft()); i += ((getMeasuredWidth() - getPaddingRight() - getPaddingLeft()) / rows))
            canvas.drawLine(i, getPaddingTop(), i, (getMeasuredHeight() - getPaddingBottom() - getPaddingTop()), paint);
        for (int i = getPaddingTop(); i <= (getMeasuredHeight() - getPaddingBottom() - getPaddingTop()); i += ((getMeasuredHeight() - getPaddingBottom() - getPaddingTop()) / column))
            canvas.drawLine(getPaddingLeft(), i, (getMeasuredWidth() - getPaddingRight() - getPaddingLeft()), i, paint);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);
        checkCoordinate((int) (event.getX()), (int) (event.getY()));
        hasTouch = true;
        invalidate();
        return true;
    }

    private void checkCoordinate(int x, int y) {
        int factorX = (getMeasuredWidth() - getPaddingRight() - getPaddingLeft()) / rows;
        int factorY = (getMeasuredHeight() - getPaddingTop() - getPaddingRight()) / column;
        int selfx = x / factorX;
        int selfy = y / factorY;
        point p = new point(selfx, selfy);
        if (player1.isHasPlayed()) {
            
            if (!player2.list.contains(p) && !player1.list.contains(p)) {
                player1.setHasPlayed(false);
                player2.setHasPlayed(true);
                player1.list.add(p);
                moves++;
                if(player1.result()== Player.STATE.WIN){
                    player1.setHasPlayed(false);
                    player2.setHasPlayed(false);
                    result="Red has won";
                    resultListener.result(true);
                }
                else if(moves>=9){
                    result="Match has Draw";
                    resultListener.result(true);
                }
            }
        } else if (player2.isHasPlayed()) {
            if (!player1.list.contains(p) && !player2.list.contains(p)) {
                player1.setHasPlayed(true);
                player2.setHasPlayed(false);
                player2.list.add(p);
                moves++;
                if(player2.result()== Player.STATE.WIN){
                    player1.setHasPlayed(false);
                    player2.setHasPlayed(false);
                    result="Green has won";
                    resultListener.result(true);
                }
                else if(moves>=9){
                        result="Match has Draw";
                        resultListener.result(true);
                }
            }
        }


    }

    private void drawRect(Canvas canvas, int x, int y, int color) {
        int width = ((getMeasuredWidth() - getPaddingRight() - getPaddingLeft()) / rows);
        int height = ((getMeasuredHeight() - getPaddingBottom() - getPaddingTop()) / column);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawRect(getPaddingLeft() + width * x, getPaddingTop() + height * y, width + x * width, height + y * height, paint);
    }

    public void restart(){
        init();
        invalidate();
    }

    public String getResult() {
        return result;
    }



    public interface  resultListener{
        void result(boolean result);
    }
    public  void setResultListener(resultListener resultListener){
        this.resultListener=resultListener;
    }
}
