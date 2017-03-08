package org.jokar.gankio.utils.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jokar.gankio.utils.JLog;


/**
 * Created by JokAr on 2016/11/1.
 */

public class Imageloader {

    public static void loadImageCenterCrop(Context context, String url,
                                           int defaultImage, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadImage(Context context, String url,
                                 int defaultImage, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadImage(Context context, String url,
                                 ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void clearCache(Context context) {
        JLog.e("Clear Image Cache");
//        Glide.get(context).clearMemory();
//        new Thread(() -> Glide.get(context).clearDiskCache()).start();
    }
}
