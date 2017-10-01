package android.letus179.com.givemefive.navigation;

import android.letus179.com.givemefive.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class OneFm extends Fragment implements ViewPager.OnPageChangeListener {
  
    private View view;
    private RadioGroup rg_;
    private ViewPager vp_;
    private HorizontalScrollView hv_;
    private List<Fragment> newsList = new ArrayList<>();
    private OneFmAdapter adapter;  
  
    @Override  
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {  
            //初始化view  
            view = inflater.inflate(R.layout.fm_one, container,false);
            rg_ = (RadioGroup) view.findViewById(R.id.one_rg);  
            vp_ = (ViewPager) view.findViewById(R.id.one_view);  
            hv_ = (HorizontalScrollView) view.findViewById(R.id.one_hv);  
            //设置RadioGroup点击事件  
            rg_.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                  
                @Override  
                public void onCheckedChanged(RadioGroup group, int id) {  
                    vp_.setCurrentItem(id);  
                }  
            });  
            //初始化顶部导航栏  
            initTab(inflater);  
            //初始化viewpager  
            initView();  
        }  
        /* 
         * 底部导航栏切换后 由于没有销毁顶部设置导致如果没有重新设置view 
         * 导致底部切换后切回顶部页面数据会消失等bug 
         * 以下设置每次重新创建view即可 
        */  
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {  
            parent.removeView(view);  
        }  
        return view;  
    }  
    /*** 
     * 初始化viewpager 
     */  
    private void initView() {  
        List<HTab> hTabs = HTabDb.getSelected();  
        for (int i = 0; i < hTabs.size(); i++) {  
            OneFm1 fm1 = new OneFm1();  
            Bundle bundle = new Bundle();  
            bundle.putString("name", hTabs.get(i).getName());  
            fm1.setArguments(bundle);  
            newsList.add(fm1);  
        }  
        //设置viewpager适配器  
        adapter = new OneFmAdapter(getActivity().getSupportFragmentManager(),newsList);  
        vp_.setAdapter(adapter);  
        //两个viewpager切换不重新加载  
        vp_.setOffscreenPageLimit(2);  
        //设置默认  
        vp_.setCurrentItem(0);  
        //设置viewpager监听事件  
        vp_.addOnPageChangeListener(this);
    }  
    /*** 
     * 初始化头部导航栏 
     * @param inflater 
     */  
    private void initTab(LayoutInflater inflater) {  
        List<HTab> hTabs = HTabDb.getSelected();  
        for (int i = 0; i < hTabs.size(); i++) {  
            //设置头部项布局初始化数据  
            RadioButton rbButton  = (RadioButton) inflater.inflate(R.layout.tab_rb, null);
            rbButton.setId(i);  
            rbButton.setText(hTabs.get(i).getName());  
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            //加入RadioGroup  
            rg_.addView(rbButton,params);  
        }  
        //默认点击  
        rg_.check(0);  
    }  
    @Override  
    public void onPageScrollStateChanged(int arg0) {  
          
    }  
    @Override  
    public void onPageScrolled(int arg0, float arg1, int arg2) {  
          
    }  
    @Override  
    public void onPageSelected(int id) {  
        setTab(id);  
    }  
    /*** 
     * 页面跳转切换头部偏移设置 
     * @param id 
     */  
    private void setTab(int id) {  
        RadioButton rbButton = (RadioButton) rg_.getChildAt(id);  
        //设置标题被点击  
        rbButton.setChecked(true);  
        //偏移设置  
        int left = rbButton.getLeft();  
        int width = rbButton.getMeasuredWidth();  
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);  
        int screenWidth = metrics.widthPixels;  
        //移动距离= 左边的位置 + button宽度的一半 - 屏幕宽度的一半  
        int len = left + width / 2 - screenWidth / 2;  
        //移动  
        hv_.smoothScrollTo(len, 0);  
    }  
}  