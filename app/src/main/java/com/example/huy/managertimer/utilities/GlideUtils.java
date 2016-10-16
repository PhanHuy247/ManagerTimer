package com.example.huy.managertimer.utilities;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.huy.managertimer.constant.Constant;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;

/**
 * Created by Rin.LV on 23/09/2016.
 */

public class GlideUtils {
    private static DrawableRequestBuilder init(Context ctx, String url, int placeholder, int error) {
        return Glide.with(ctx).load(url).placeholder(placeholder).error(error).dontAnimate().centerCrop();
    }

    public static void load(Context ctx, String url, int placeholder, int error, ImageView into) {
        init(ctx, url, placeholder, error).into(into);
    }

    public static void load(Context ctx, int drawable, int placeholder, int error, ImageView into) {
        Glide.with(ctx).load(drawable).placeholder(placeholder).error(error).into(into);
    }

    public static void loadRoundImage(Context ctx, String url, int placeholder, int error, ImageView into) {
        Glide.with(ctx).load(url).placeholder(placeholder).error(error).bitmapTransform(new CenterCrop(ctx), new RoundedCornersTransformation(ctx, Constant.IMAGE_ROUND_RADIUS, Constant.IMAGE_ROUND_MARGIN,
                RoundedCornersTransformation.CornerType.ALL)).into(into);
    }

    public static void loadRoundVignetteImage(Context ctx, String url, int placeholder, int error, ImageView into) {
        init(ctx, url, placeholder, error).bitmapTransform(new ContrastFilterTransformation(ctx, 1.5f), new RoundedCornersTransformation(ctx, Constant.IMAGE_ROUND_RADIUS, Constant.IMAGE_ROUND_MARGIN,
                RoundedCornersTransformation.CornerType.ALL))
                .into(into);
    }

    public static void loadCircleImage(Context ctx, String url, int placeholder, int error, ImageView into) {
        init(ctx, url, placeholder, error).bitmapTransform(new CropCircleTransformation(ctx)).into(into);
    }
}
