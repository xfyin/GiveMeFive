package android.letus179.com.givemefive.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by xfyin on 2017/9/24.
 */

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }

    public void setupBackAsUp(String title, boolean titleShow) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 为标题栏设置标题，
            if (titleShow) {
                actionBar.setTitle(title);
            } else {
                actionBar.setTitle("");
            }
            // 加一个返回图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 不显示当前程序的图标
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // 点击Home ICON finish 当前Activity
//            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
