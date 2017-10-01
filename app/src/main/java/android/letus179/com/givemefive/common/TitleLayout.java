package android.letus179.com.givemefive.common;

import android.content.Context;
import android.letus179.com.givemefive.R;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 自定义标题栏控件
 *
 * Created by xfyin on 2017/9/26.
 */

public class TitleLayout extends LinearLayout {

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 1 加载布局文件的id  2.给加载好的布局再添加一个父布局
        LayoutInflater.from(context).inflate(R.layout.title, this);

        ImageView titleBack = (ImageView) findViewById(R.id.go_back);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
