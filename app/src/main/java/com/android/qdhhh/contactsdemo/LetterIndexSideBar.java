package com.android.qdhhh.contactsdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by qdhhh on 2016/10/10.
 */

public class LetterIndexSideBar extends View {
    //当前手指滑动到的位置
    private int choosedPosition = -1;
    //画文字的画笔
    private Paint paint;
    //右边的所有文字
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    //页面正中央的TextView，用来显示手指当前滑动到的位置的文本
    private TextView textViewDialog;
    //接口变量，该接口主要用来实现当手指在右边的滑动控件上滑动时ListView能够跟着滚动
    private UpdateListView updateListView;

    public LetterIndexSideBar(Context context) {
        this(context, null);
    }

    public LetterIndexSideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(24);
    }


    /**
     * 设置滑动侧边栏的时候在屏幕中央显示的当前字母索引的提醒
     *
     * @param textViewDialog
     */
    public void setTextViewDialog(TextView textViewDialog) {
        this.textViewDialog = textViewDialog;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int perTextHeight = getHeight() / letters.length;
        for (int i = 0; i < letters.length; i++) {
            if (i == choosedPosition) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.BLACK);
            }
            canvas.drawText(letters[i], (getWidth() - paint.measureText(letters[i])) / 2, (i + 1) * perTextHeight, paint);
        }
    }


    /**
     * 设置手势在控件上滑动的时候的响应
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int perTextHeight = getHeight() / letters.length;
        float y = event.getY();
        int currentPosition = (int) (y / perTextHeight);

        /**
         * currentPosition不能为小于0的值
         */
        if (currentPosition < 0) {
            currentPosition = 0;
        } else if (currentPosition >= letters.length) {
            currentPosition = letters.length - 1;
        }

        String letter = letters[currentPosition];
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                if (textViewDialog != null) {
                    textViewDialog.setVisibility(View.GONE);
                }
                break;
            default:
                setBackgroundColor(Color.parseColor("#cccccc"));
                if (currentPosition > -1 && currentPosition < letters.length) {
                    if (textViewDialog != null) {
                        textViewDialog.setVisibility(View.VISIBLE);
                        textViewDialog.setText(letter);
                    }
                    if (updateListView != null) {
                        updateListView.updateListView(letter);
                    }
                    choosedPosition = currentPosition;
                }
                break;
        }
        invalidate();
        return true;
    }

    public void setUpdateListView(UpdateListView updateListView) {
        this.updateListView = updateListView;
    }


    /**
     * 一个接口 ，实现后用于在滑动这个自定义view的时候listview也会随之发生滑动
     */
    public interface UpdateListView {
        public void updateListView(String currentChar);
    }


    /**
     * 滑动listview的时候这个自定义的view中的字母索引之也会随之发生改变
     *
     * @param currentChar
     */
    public void updateLetterIndexView(int currentChar) {
        for (int i = 0; i < letters.length; i++) {
            if (currentChar == letters[i].charAt(0)) {
                choosedPosition = i;
                invalidate();
                break;
            }
        }
    }
}
