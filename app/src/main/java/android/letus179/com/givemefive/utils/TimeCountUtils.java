package android.letus179.com.givemefive.utils;

import android.app.Activity;
import android.graphics.Color;
import android.letus179.com.givemefive.R;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

/**
 * Created by xfyin on 2017/9/25.
 */

public class TimeCountUtils extends CountDownTimer {

    private Activity mActivity;

    private Button btn;

    public TimeCountUtils(Activity activity, Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mActivity = activity;
        this.btn = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 按钮不能点击
        btn.setClickable(false);
        // 设置倒计时间
        btn.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        // 设置按钮背景色为灰色
        btn.setBackgroundColor(ResourcesCompat.getColor(mActivity.getResources(), R.color.clr_normal, null));

        // 获取按钮的文字
        Spannable span = new SpannableString(btn.getText().toString());
        // 将倒计时时间显示为红色
        span.setSpan(new ForegroundColorSpan(Color.RED), 0 , 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        btn.setText(span);
    }

    @Override
    public void onFinish() {
        btn.setText("重新获取验证码");
        btn.setClickable(true);
        btn.setBackgroundColor(ResourcesCompat.getColor(mActivity.getResources(), R.color.colorAccent, null));
    }
}
