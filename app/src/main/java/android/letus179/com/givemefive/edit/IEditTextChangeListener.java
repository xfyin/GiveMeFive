package android.letus179.com.givemefive.edit;

/**
 * Created by xfyin on 2017/9/24.
 */

public interface IEditTextChangeListener {

    /**
     * 监听editview内容变化
     *
     * @param isHasContent 是否所有的edittextview都有了内容
     */
    void textChange(boolean isHasContent);
}
