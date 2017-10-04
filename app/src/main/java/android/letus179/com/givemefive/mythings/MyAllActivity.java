package android.letus179.com.givemefive.mythings;

import android.content.Intent;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAllActivity extends BasicActivity implements View.OnClickListener {

    // 消息
    private ImageView myMessageView;

    // 设置
    private ImageView mySettingView;

    // 我的资料
    private CircleImageView myInfoView;

    // 昵称
    private TextView myNameView;

    // 会员级别
    private TextView myLevelView;

    // 全部订单,我的地址, 反馈意见
    private View myOrdersLayout, myAddressLayout, mySuggestionLayout;

    // 待付款
    private LinearLayout myPaymentWaitLayout;

    // 待收货
    private LinearLayout myReceiveWaitLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_all);

        myMessageView = (ImageView) findViewById(R.id.my_message);
        mySettingView = (ImageView) findViewById(R.id.my_setting);
        myInfoView = (CircleImageView) findViewById(R.id.my_info);
        myNameView = (TextView) findViewById(R.id.my_name);
        myLevelView = (TextView) findViewById(R.id.my_level);
        myOrdersLayout = findViewById(R.id.my_orders);
        myPaymentWaitLayout = (LinearLayout) findViewById(R.id.my_payment_wait);
        myReceiveWaitLayout = (LinearLayout) findViewById(R.id.my_receive_wait);
        myAddressLayout = findViewById(R.id.my_address);
        mySuggestionLayout = findViewById(R.id.my_suggestion);

        myMessageView.setOnClickListener(this);
        mySettingView.setOnClickListener(this);
        myInfoView.setOnClickListener(this);
        myNameView.setOnClickListener(this);
        myLevelView.setOnClickListener(this);
        myOrdersLayout.setOnClickListener(this);
        myPaymentWaitLayout.setOnClickListener(this);
        myReceiveWaitLayout.setOnClickListener(this);
        myAddressLayout.setOnClickListener(this);
        mySuggestionLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.my_message:
                finish();
                break;
            case R.id.my_setting:
                finish();
                break;
            case R.id.my_info:
            case R.id.my_name:
                intent = new Intent(MyAllActivity.this, MyInfoActivity.class);
                intent.putExtra("title", "我的资料");
                startActivity(intent);
                finish();
                break;
            case R.id.my_level:
                finish();
                break;
            case R.id.my_orders:
                finish();
                break;
            case R.id.my_payment_wait:
                finish();
                break;
            case R.id.my_receive_wait:
                finish();
                break;
            case R.id.my_address:
                intent = new Intent(MyAllActivity.this, MyAddressActivity.class);
                intent.putExtra("title", "收货地址管理");
                startActivity(intent);
                break;
            case R.id.my_suggestion:
                intent = new Intent(MyAllActivity.this, MySuggestionActivity.class);
                intent.putExtra("title", "意见反馈");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
