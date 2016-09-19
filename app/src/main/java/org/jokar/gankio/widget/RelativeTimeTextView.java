package org.jokar.gankio.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


import org.jokar.gankio.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 相对时间TextView
 * Created by JokAr on 16/3/14.
 */
public class RelativeTimeTextView extends TextView {

    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TIME_PREFIX = "time_prefix";
    private static final String INSTANCE_TIME_SUFFIX = "time_suffix";
    private static final String INSTANCE_REFRESHTIME = "refreshTime";

    private String time_prefix;
    private String time_suffix;
    private long refreshTime;
    private Handler mHandler = new Handler();
    private RefreshRunnable refreshRunnable;
    private boolean isRefresh;

    public RelativeTimeTextView(Context context) {
        this(context, null);
    }

    public RelativeTimeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RelativeTimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RelativeTimeTextView, defStyleAttr, 0);
        try {
            time_prefix = array.getString(R.styleable.RelativeTimeTextView_relative_time_prefix);
            time_suffix = array.getString(R.styleable.RelativeTimeTextView_relative_time_suffix);
            time_suffix = time_suffix == null ? "" : time_suffix;
            time_prefix = time_prefix == null ? "" : time_prefix;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }

    }

    public String getTime_prefix() {
        return time_prefix;
    }

    public void setTime_prefix(String time_prefix) {
        this.time_prefix = time_prefix;
    }

    public String getTime_suffix() {
        return time_suffix;
    }

    public void setTime_suffix(String time_suffix) {
        if (time_suffix.length() > 0)
            time_suffix = time_suffix.substring(time_suffix.indexOf(">") + 1, time_suffix.lastIndexOf("<"));
        this.time_suffix = time_suffix;

    }

    public long getRefresh_time() {
        return refreshTime;
    }

    /**
     * @param time
     */
    public void setRefreshTime(long time) {

        refreshTime = time;

        stopRefresh();

        refreshRunnable = new RefreshRunnable(refreshTime);

        startRefresh();

        updateTextDisplay();
    }


    public void setRefreshTime(String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        try {
            Date date = sdf1.parse(time);
            this.refreshTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            this.refreshTime = -1l;
        }

        stopRefresh();

        refreshRunnable = new RefreshRunnable(this.refreshTime);

        startRefresh();

        updateTextDisplay();
    }

    public void setRefreshTime(String time, String time_suffix) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        this.setTime_suffix(time_suffix);
        try {
            Date date = sdf1.parse(time);
            this.refreshTime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            this.refreshTime = -1l;
        }

        stopRefresh();

        refreshRunnable = new RefreshRunnable(this.refreshTime);

        startRefresh();

        updateTextDisplay();
    }

    private void updateTextDisplay() {
        if (refreshTime == -1l)
            return;
        setText(time_prefix + getRelativeTime() );
    }

    private CharSequence getRelativeTime() {
        long now = System.currentTimeMillis();
        long difference = now - refreshTime;
        if (difference >= 0 && difference < DateUtils.MINUTE_IN_MILLIS)
            return getResources().getString(R.string.just_now);
        else
            return DateUtils.getRelativeTimeSpanString(
                    refreshTime,
                    now,
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE);

    }

    private void stopRefresh() {
        if (isRefresh) {
            mHandler.removeCallbacks(refreshRunnable);
            isRefresh = false;
        }
    }

    private void startRefresh() {
        mHandler.post(refreshRunnable);
        isRefresh = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startRefresh();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRefresh();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopRefresh();
        } else {
            startRefresh();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putString(INSTANCE_TIME_PREFIX, time_prefix);
        bundle.putString(INSTANCE_TIME_SUFFIX, time_suffix);
        bundle.putLong(INSTANCE_REFRESHTIME, refreshTime);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            time_prefix = bundle.getString(INSTANCE_TIME_PREFIX);
            time_suffix = bundle.getString(INSTANCE_TIME_SUFFIX);
            setRefreshTime(bundle.getLong(INSTANCE_REFRESHTIME, -1l));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    private class RefreshRunnable implements Runnable {

        private long refreshStartTime;

        public RefreshRunnable(long refreshStartTime) {
            this.refreshStartTime = refreshStartTime;
        }

        @Override
        public void run() {
            long difference = Math.abs(System.currentTimeMillis() - refreshStartTime);
            long interval = DateUtils.MINUTE_IN_MILLIS;
            if (difference > DateUtils.HOUR_IN_MILLIS) {
                interval = DateUtils.HOUR_IN_MILLIS;
            } else if (difference > DateUtils.DAY_IN_MILLIS) {
                interval = DateUtils.DAY_IN_MILLIS;
            } else if (difference > DateUtils.WEEK_IN_MILLIS) {
                interval = DateUtils.WEEK_IN_MILLIS;
            }

            mHandler.postDelayed(this, interval);
        }
    }
}
