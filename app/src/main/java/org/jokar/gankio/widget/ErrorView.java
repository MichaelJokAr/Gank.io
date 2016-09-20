package org.jokar.gankio.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jokar.gankio.R;


/**
 * 错误信息展示view
 * @author JokAr
 *         <p>
 *         A custom view that displays an error image, a title, and a subtitle given an HTTP status
 *         code. It can be used for various other purposes like displaying other kinds of errors or
 *         just messages with images.
 *         <p>
 *         <p>
 */
public class ErrorView extends LinearLayout {
    public static final int ALIGNMENT_LEFT = 0;
    public static final int ALIGNMENT_CENTER = 1;
    public static final int ALIGNMENT_RIGHT = 2;

    private ImageView mErrorImageView;
    private TextView mTitleTextView;
    private TextView mSubtitleTextView;
    private TextView mRetryButton;
    private ProgressBar pb_loda;
    private RetryListener mListener;
    private LinearLayout ll_continear;
    private Context context;

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        super(context, attrs);
        this.context = context;
        init(attrs, defStyle, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyle, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyle, defStyleRes);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.error_view_layout, this, true);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        /* Set android:animateLayoutChanges="true" programmatically */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayoutTransition(new LayoutTransition());
        }

        mErrorImageView = (ImageView) findViewById(R.id.error_image);
        mTitleTextView = (TextView) findViewById(R.id.error_title);
        mSubtitleTextView = (TextView) findViewById(R.id.error_subtitle);
        mRetryButton = (TextView) findViewById(R.id.error_retry);
        pb_loda = (ProgressBar) findViewById(R.id.pb_loda);
        ll_continear = (LinearLayout) findViewById(R.id.ll_continear);
        int imageRes;

        String title;
        int titleColor;

        String subtitle;
        int subtitleColor;

        boolean showTitle;
        boolean showSubtitle;
        boolean showRetryButton;

        String retryButtonText;
        int retryButtonBackground;
        int retryButtonTextColor;

        try {
            imageRes = a.getResourceId(R.styleable.ErrorView_ev_errorImage, R.mipmap.error_view_cloud);
            title = a.getString(R.styleable.ErrorView_ev_title);
            titleColor = a.getColor(R.styleable.ErrorView_ev_titleColor,
                    ContextCompat.getColor(context,R.color.error_view_text));
            subtitle = a.getString(R.styleable.ErrorView_ev_subtitle);
            subtitleColor = a.getColor(R.styleable.ErrorView_ev_subtitleColor,
                    ContextCompat.getColor(context,R.color.error_view_text_light));
            showTitle = a.getBoolean(R.styleable.ErrorView_ev_showTitle, true);
            showSubtitle = a.getBoolean(R.styleable.ErrorView_ev_showSubtitle, true);
            showRetryButton = a.getBoolean(R.styleable.ErrorView_ev_showRetryButton, true);
            retryButtonText = a.getString(R.styleable.ErrorView_ev_retryButtonText);
            retryButtonBackground = a.getResourceId(R.styleable.ErrorView_ev_retryButtonBackground,
                    R.drawable.error_view_retry_button_background);
            retryButtonTextColor = a.getColor(R.styleable.ErrorView_ev_retryButtonTextColor,
                    ContextCompat.getColor(context,R.color.error_view_text_dark));
            int alignInt = a.getInt(R.styleable.ErrorView_ev_subtitleAlignment, 1);

            if (imageRes != 0) {
                setImage(imageRes);
            }

            if (title != null) {
                setTitle(title);
            }

            if (subtitle != null) {
                setSubtitle(subtitle);
            }

            if (retryButtonText != null) {
                mRetryButton.setText(retryButtonText);
            }

            if (!showTitle) {
                mTitleTextView.setVisibility(GONE);
            }

            if (!showSubtitle) {
                mSubtitleTextView.setVisibility(GONE);
            }

            if (!showRetryButton) {
                mRetryButton.setVisibility(GONE);
            }

            mTitleTextView.setTextColor(titleColor);
            mSubtitleTextView.setTextColor(subtitleColor);

            mRetryButton.setTextColor(retryButtonTextColor);
            mRetryButton.setBackgroundResource(retryButtonBackground);

            setSubtitleAlignment(alignInt);
        } finally {
            a.recycle();
        }

        mRetryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onRetry();
                }
            }
        });
    }

    /**
     * Attaches a listener that to the view that reports retry events.
     *
     * @param listener {@link RetryListener} to be notified when a retry
     *                 event occurs.
     */
    public void setOnRetryListener(RetryListener listener) {
        mListener = listener;
    }


    /**
     * Loads ErrorView configuration from a {@link Config} object.
     *
     * @param config configuration to load from
     */
    public void setConfig(Config config) {
        if (config.getImage() != null) {
            Object image = config.getImage();
            if (image instanceof Integer) {
                setImage((int) image);
            } else if (image instanceof Drawable) {
                setImage((Drawable) image);
            } else if (image instanceof Bitmap) {
                setImage((Bitmap) image);
            }
        }

        if (config.getTitle() != null) {
            setTitle(config.getTitle());
        }

        if (config.getTitleColor() != 0) {
            setTitleColor(config.getTitleColor());
        }

        if (config.getSubtitle() != null) {
            setSubtitle(config.getSubtitle());
        }

        if (config.getSubtitleColor() != 0) {
            setSubtitleColor(config.getSubtitleColor());
        }

        showRetryButton(config.shouldShowRetryButton());

        if (config.getRetryButtonText() != null) {
            setRetryButtonText(config.getRetryButtonText());
        }

        if (config.getRetryButtonTextColor() != 0) {
            setRetryButtonTextColor(config.getRetryButtonTextColor());
        }
    }

    /**
     * Returns the current ErrorView configuration.
     */
    public Config getConfig() {
        return Config.create()
                .image(getImage())
                .title(getTitle())
                .titleColor(getTitleColor())
                .subtitle(getSubtitle())
                .subtitleColor(getSubtitleColor())
                .retryVisible(isRetryButtonVisible())
                .retryText(getRetryButtonText())
                .retryTextColor(getRetryButtonTextColor())
                .build();
    }

    /**
     * Sets error image to a given drawable resource
     *
     * @param res drawable resource.
     * @deprecated Use {@link #setImage(int)} instead.
     */
    @Deprecated
    public void setImageResource(int res) {
        setImage(res);
    }

    /**
     * Sets the error image to a given {@link Drawable}.
     *
     * @param drawable {@link Drawable} to use as error image.
     * @deprecated Use {@link #setImage(Drawable)} instead.
     */
    @Deprecated
    public void setImageDrawable(Drawable drawable) {
        setImage(drawable);
    }

    /**
     * Sets the error image to a given {@link Bitmap}.
     *
     * @param bitmap {@link Bitmap} to use as error image.
     * @deprecated Use {@link #setImage(Bitmap)} instead.
     */
    @Deprecated
    public void setImageBitmap(Bitmap bitmap) {
        setImage(bitmap);
    }

    /**
     * Sets error image to a given drawable resource
     *
     * @param res drawable resource.
     */
    public void setImage(int res) {
        mErrorImageView.setImageResource(res);
    }

    /**
     * Sets the error image to a given {@link Drawable}.
     *
     * @param drawable {@link Drawable} to use as error image.
     */
    public void setImage(Drawable drawable) {
        mErrorImageView.setImageDrawable(drawable);
    }

    /**
     * Sets the error image to a given {@link Bitmap}.
     *
     * @param bitmap {@link Bitmap} to use as error image.
     */
    public void setImage(Bitmap bitmap) {
        mErrorImageView.setImageBitmap(bitmap);
    }

    /**
     * Returns the current error iamge
     */
    public Drawable getImage() {
        return mErrorImageView.getDrawable();
    }

    /**
     * Sets the error title to a given {@link String}.
     *
     * @param text {@link String} to use as error title.
     */
    public void setTitle(String text) {
        mTitleTextView.setText(text);
    }

    /**
     * Sets the error title to a given string resource.
     *
     * @param res string resource to use as error title.
     */
    public void setTitle(int res) {
        mTitleTextView.setText(res);
    }

    /**
     * Returns the current title string.
     */
    public String getTitle() {
        return mTitleTextView.getText().toString();
    }

    /**
     * Sets the error title text to a given color.
     *
     * @param res color resource to use for error title text.
     */
    public void setTitleColor(int res) {
        mTitleTextView.setTextColor(res);
    }

    /**
     * Returns the current title text color.
     */
    public int getTitleColor() {
        return mTitleTextView.getCurrentTextColor();
    }

    /**
     * Sets the error subtitle to a given {@link String}.
     *
     * @param exception {@link String} to use as error subtitle.
     */
    public void setSubtitle(String exception) {
        mSubtitleTextView.setText(exception);
    }

    /**
     * Sets the error subtitle to a given string resource.
     *
     * @param res string resource to use as error subtitle.
     */
    public void setSubtitle(int res) {
        mSubtitleTextView.setText(res);
    }

    /**
     * Returns the current subtitle.
     */
    public String getSubtitle() {
        return mSubtitleTextView.getText().toString();
    }

    /**
     * Sets the error subtitle text to a given color
     *
     * @param res color resource to use for error subtitle text.
     */
    public void setSubtitleColor(int res) {
        mSubtitleTextView.setTextColor(res);
    }

    /**
     * Returns the current subtitle text color.
     */
    public int getSubtitleColor() {
        return mSubtitleTextView.getCurrentTextColor();
    }

    /**
     * Sets the retry button's text to a given {@link String}.
     *
     * @param text {@link String} to use as retry button text.
     */
    public void setRetryButtonText(String text) {
        mRetryButton.setText(text);
    }

    /**
     * Sets the retry button's text to a given string resource.
     *
     * @param res string resource to be used as retry button text.
     */
    public void setRetryButtonText(int res) {
        mRetryButton.setText(res);
    }

    /**
     * Returns the current retry button text.
     */
    public String getRetryButtonText() {
        return mRetryButton.getText().toString();
    }

    /**
     * Sets the retry button's text color to a given color.
     *
     * @param color int color to be used as text color.
     */
    public void setRetryButtonTextColor(int color) {
        mRetryButton.setTextColor(color);
    }

    /**
     * Returns the current retry button text color.
     */
    public int getRetryButtonTextColor() {
        return mRetryButton.getCurrentTextColor();
    }

    /**
     * Shows or hides the error title
     */
    public void showTitle(boolean show) {
        mTitleTextView.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the title is currently visible.
     */
    public boolean isTitleVisible() {
        return mTitleTextView.getVisibility() == VISIBLE;
    }

    /**
     * Shows or hides error subtitle.
     */
    public void showSubtitle(boolean show) {
        mSubtitleTextView.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the subtitle is currently visible.
     */
    public boolean isSubtitleVisible() {
        return mSubtitleTextView.getVisibility() == VISIBLE;
    }

    /**
     * Shows or hides the retry button.
     */
    public void showRetryButton(boolean show) {
        mRetryButton.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the retry button is visible.
     */
    public boolean isRetryButtonVisible() {
        return mRetryButton.getVisibility() == VISIBLE;
    }

    /**
     *
     * @param value
     */
    public void setRetryButtonClickable(boolean value){
        mRetryButton.setClickable(value);
    }

    /**
     * display loading failed view
     */
    public void showError(String error) {
        pb_loda.setVisibility(GONE);
        ll_continear.setVisibility(VISIBLE);
        setSubtitle(error);
    }

    /**
     * display loading failed view
     */
    public void showError(String error,boolean clickable) {
        pb_loda.setVisibility(GONE);
        ll_continear.setVisibility(VISIBLE);
        setRetryButtonClickable(clickable);
        setSubtitle(error);
    }

    /**
     * display loading view
     */
    public void showLoad() {
        pb_loda.setVisibility(VISIBLE);
        ll_continear.setVisibility(GONE);
    }

    public boolean getErrorShowning() {
        return ll_continear.isShown();
    }

    /**
     * Sets the subtitle's alignment to a given value
     */
    public void setSubtitleAlignment(int alignment) {
        if (alignment == ALIGNMENT_LEFT) {
            mSubtitleTextView.setGravity(Gravity.LEFT);
        } else if (alignment == ALIGNMENT_CENTER) {
            mSubtitleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            mSubtitleTextView.setGravity(Gravity.RIGHT);
        }
    }

    /**
     * Returns the current subtitle allignment
     */
    public int getSubtitleAlignment() {
        int gravity = mSubtitleTextView.getGravity();
        if (gravity == Gravity.LEFT) {
            return ALIGNMENT_LEFT;
        } else if (gravity == Gravity.CENTER_HORIZONTAL) {
            return ALIGNMENT_CENTER;
        } else {
            return ALIGNMENT_RIGHT;
        }
    }

    public static class Config {
        private Object mImage;
        private String mTitle;
        private int mTitleColor;
        private String mSubtitle;
        private int mSubtitleColor;
        private boolean mShowRetryButton = true;
        private String mRetryButtonText;
        private int mRetryButtonTextColor;

        public static Builder create() {
            return new Builder();
        }

        private Config() {
            /* epmty */

        }

        public Object getImage() {
            return mImage;
        }

        public String getTitle() {
            return mTitle;
        }

        public int getTitleColor() {
            return mTitleColor;
        }

        public String getSubtitle() {
            return mSubtitle;
        }

        public int getSubtitleColor() {
            return mSubtitleColor;
        }

        public boolean shouldShowRetryButton() {
            return mShowRetryButton;
        }

        public String getRetryButtonText() {
            return mRetryButtonText;
        }

        public int getRetryButtonTextColor() {
            return mRetryButtonTextColor;
        }

        public static class Builder {
            private Config config;

            private Builder() {
                config = new Config();
            }

            public Builder image(int res) {
                config.mImage = res;
                return this;
            }

            public Builder image(Drawable drawable) {
                config.mImage = drawable;
                return this;
            }

            public Builder image(Bitmap bitmap) {
                config.mImage = bitmap;
                return this;
            }

            public Builder title(String title) {
                config.mTitle = title;
                return this;
            }

            public Builder titleColor(int color) {
                config.mTitleColor = color;
                return this;
            }

            public Builder subtitle(String subtitle) {
                config.mSubtitle = subtitle;
                return this;
            }

            public Builder subtitleColor(int color) {
                config.mSubtitleColor = color;
                return this;
            }

            public Builder retryVisible(boolean visible) {
                config.mShowRetryButton = visible;
                return this;
            }

            public Builder retryText(String text) {
                config.mRetryButtonText = text;
                return this;
            }

            public Builder retryTextColor(int color) {
                config.mRetryButtonTextColor = color;
                return this;
            }

            public Config build() {
                return config;
            }
        }
    }

    public interface RetryListener {
        void onRetry();
    }
}