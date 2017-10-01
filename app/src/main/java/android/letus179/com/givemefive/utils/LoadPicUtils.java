package android.letus179.com.givemefive.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import java.io.InputStream;

/**
 * 将图片设置为背景图片时，需要经过处理压缩，避免内存泄漏
 *
 * Created by xfyin on 2017/9/30.
 */

public class LoadPicUtils {
    public static void loadPic(Context context, int res, View view) {
        InputStream in = context.getResources().openRawResource(res);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 10; // width height 设置为原来的1/10
        Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
        view.setBackground(new BitmapDrawable(context.getResources(), bitmap));
    }
}
