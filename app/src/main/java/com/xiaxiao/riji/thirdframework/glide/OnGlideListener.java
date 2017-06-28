package com.xiaxiao.riji.thirdframework.glide;

import android.graphics.Bitmap;

import com.bumptech.glide.request.target.Target;
import com.xiaxiao.riji.Listener.MyListener;

/**
 * Created by xiaxiao on 2017/1/12.
 */

public abstract class OnGlideListener implements MyListener {
    public abstract  void onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource);
    public abstract  void onFailed(Exception e, String model, Target<Bitmap> target,
                                   boolean isFirstResource);
}
