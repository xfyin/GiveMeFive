package android.letus179.com.givemefive.navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * 顶部导航
 */
public class HTabDb {
    private static final List<HTab> SELECTED = new ArrayList<>();
    static{  
        SELECTED.add(new HTab("首页"));
        SELECTED.add(new HTab("新品"));
        SELECTED.add(new HTab("海外直邮"));
        SELECTED.add(new HTab("特卖行"));
        SELECTED.add(new HTab("香港购"));
        SELECTED.add(new HTab("台湾购"));
        SELECTED.add(new HTab("澳门购"));
    }
    /*** 
     * 获得头部tab的所有项 
     */  
    public static List<HTab> getSelected() {  
        return SELECTED;
    }  
  
}  