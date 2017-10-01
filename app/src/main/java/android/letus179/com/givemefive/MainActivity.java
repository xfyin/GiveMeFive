package android.letus179.com.givemefive;

import android.app.Activity;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.navigation.TabDb;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends BasicActivity implements TabHost.OnTabChangeListener {

    // 左滑动
    private DrawerLayout mDrawerLayout;

    // 底部导航
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("主页");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home);
        }
        setDrawerLeftEdgeSize(this, mDrawerLayout, 0.8f);

        //初始化FragmentTabHost
        initHost();
        //初始化底部导航栏
        initTab();
        //默认选中
        mTabHost.onTabChanged(TabDb.getTabsTxt()[0]);

    }

    @Override
    public void onTabChanged(String tabId) {
        //从分割线中获得多少个切换界面
        TabWidget tabWidget = mTabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            View v = tabWidget.getChildAt(i);
            TextView tv = (TextView) v.findViewById(R.id.foot_tv);
            ImageView iv = (ImageView) v.findViewById(R.id.foot_iv);
            //修改当前的界面按钮颜色图片
            if (i == mTabHost.getCurrentTab()) {
                tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.tab_light_color, null));
                iv.setImageResource(TabDb.getTabsImgLight()[i]);
            } else {
                tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.tab_color, null));
                iv.setImageResource(TabDb.getTabsImg()[i]);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTab() {
        String[] tabs = TabDb.getTabsTxt();
        for (int i = 0; i < tabs.length; i++) {
            //新建TabSpec
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(TabDb.getTabsTxt()[i]);
            //设置view
            View view = LayoutInflater.from(this).inflate(R.layout.tab_foot, null);
            ((TextView) view.findViewById(R.id.foot_tv)).setText(TabDb.getTabsTxt()[i]);
            ((ImageView) view.findViewById(R.id.foot_iv)).setImageResource(TabDb.getTabsImg()[i]);
            tabSpec.setIndicator(view);
            //加入TabSpec
            mTabHost.addTab(tabSpec, TabDb.getFramgent()[i], null);
        }
    }

    private void initHost() {
        mTabHost = (FragmentTabHost) findViewById(R.id.main_tab);
        //调用setup方法 设置view
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_view);
        //去除分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        //监听事件
        mTabHost.setOnTabChangedListener(this);
    }

    /**
     * 抽屉滑动范围控制
     *
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage 占全屏的份额0~1
     */
    private void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            // find ViewDragHelper and set it accessible
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            // find edgesize and set is accessible
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            // set new edgesize
            // Point displaySize = new Point();
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
            Log.e("NoSuchFieldException", e.getMessage().toString());
        } catch (IllegalArgumentException e) {
            Log.e("IllegalArException", e.getMessage().toString());
        } catch (IllegalAccessException e) {
            Log.e("IllegalAccessException", e.getMessage().toString());
        }
    }

}
