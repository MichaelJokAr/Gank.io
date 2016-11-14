package org.jokar.gankio.app;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * GLide配置
 * Created by JokAr on 16/4/11.
 */
public class GlideModuleSetting implements GlideModule {
    private static final int memorySize = 1024 * 1024 * 10;
    private static final int diskCachesSize = 1024 * 1024 * 30;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setMemoryCache(new LruResourceCache(memorySize));
//        builder.setBitmapPool(new LruBitmapPool(diskCachesSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //配置okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();

        glide.register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(client));
        glide.setMemoryCategory(MemoryCategory.LOW);
    }
}
