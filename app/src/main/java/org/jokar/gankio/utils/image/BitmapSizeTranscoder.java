package org.jokar.gankio.utils.image;

import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

import org.jokar.gankio.model.entities.ImageSize;


/**
 * Created by JokAr on 2016/11/1.
 */
public class BitmapSizeTranscoder implements ResourceTranscoder<Bitmap, ImageSize> {

    @Override
    public Resource<ImageSize> transcode(Resource<Bitmap> toTranscode) {
        Bitmap bitmap = toTranscode.get();
        ImageSize size = new ImageSize();
        size.setWidth(bitmap.getWidth());
        size.setHeight(bitmap.getHeight());
        bitmap = null;
        return new SimpleResource<>(size);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
