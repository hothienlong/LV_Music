package com.example.lv_music;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class TextThumbSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    private int mThumbSize;
    private TextPaint mTextPaint;

    public TextThumbSeekBar(Context context) {
        this(context, null);
    }

    public TextThumbSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public TextThumbSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mThumbSize = getResources().getDimensionPixelSize(R.dimen.thumb_size);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.thumb_text_size));
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Thiết lập text hiển thị
//        String progressText = String.valueOf(getProgress());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("m:ss");
        String totalTimeText = simpleDateFormat.format(getMax());
        Toast.makeText(getContext(), totalTimeText, Toast.LENGTH_SHORT).show();
        String progressTimeText = simpleDateFormat.format(getProgress());
        String displayTime = progressTimeText + " / " + totalTimeText;

        Rect bounds = new Rect();
        mTextPaint.getTextBounds(displayTime, 0, displayTime.length(), bounds);

        // Thiết lập vị trí
        int leftPadding = getPaddingLeft() - getThumbOffset();
        int rightPadding = getPaddingRight() - getThumbOffset();
        int width = getWidth() - leftPadding - rightPadding;
        float progressRatio = (float) getProgress() / getMax();
        float thumbOffset = mThumbSize * (.5f - progressRatio);
        float thumbX = progressRatio * width + leftPadding + thumbOffset;
        float thumbY = getHeight() / 2f + bounds.height() / 2f;
        canvas.drawText(displayTime, thumbX, thumbY, mTextPaint);
    }
}