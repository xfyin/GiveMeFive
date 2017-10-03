package android.letus179.com.givemefive.mythings;

import android.content.Intent;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.edit.ClearEditText;
import android.letus179.com.givemefive.edit.IEditTextChangeListener;
import android.letus179.com.givemefive.edit.TextChangeListener;
import android.letus179.com.givemefive.utils.TimeCountUtils;
import android.letus179.com.givemefive.utils.ValidatorUtils;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyNewPhoneActivity extends BasicActivity implements View.OnClickListener {

    // 新手机号
    private ClearEditText myInfoPhoneNewText;
    // 验证码
    private ClearEditText myInfoPhoneCode1;
    // 发送验证码
    private Button myInfoPhoneCodeSend1;
    // 换绑手机号
    private Button myInfoPhoneSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_new_phone);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNewPhoneActivity.this, MyPhoneActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        myInfoPhoneNewText = (ClearEditText) findViewById(R.id.my_info_phone_new);
        myInfoPhoneCode1 = (ClearEditText) findViewById(R.id.my_info_phone_code1);
        myInfoPhoneCodeSend1 = (Button) findViewById(R.id.my_info_phone_code_send1);
        myInfoPhoneSend = (Button) findViewById(R.id.my_info_phone_send);
        myInfoPhoneCodeSend1.setOnClickListener(this);
        myInfoPhoneSend.setOnClickListener(this);


        // 监听按钮
        TextChangeListener textChangeListener = new TextChangeListener(myInfoPhoneSend, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    myInfoPhoneSend.setEnabled(true);
                    myInfoPhoneSend.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    myInfoPhoneSend.setEnabled(false);
                    myInfoPhoneSend.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(myInfoPhoneNewText, myInfoPhoneCode1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_phone_code_send1:
                // 发送验证码
                TimeCountUtils timeCountUtils = new TimeCountUtils(this, myInfoPhoneCodeSend1, 60000, 1000);
                timeCountUtils.start();
                break;
            case R.id.my_info_phone_send:
                // TODO: 2017/10/3
                // 校验验证码
                //校验手机号
                String newPhone = myInfoPhoneNewText.getText().toString();
                if (!ValidatorUtils.isMobile(newPhone)) {
                    Toast.makeText(MyNewPhoneActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
                    return;
               }
                Intent intent = new Intent(MyNewPhoneActivity.this, MyInfoActivity.class);
                intent.putExtra("new_phone", newPhone.substring(0, 3) + "****" + newPhone.substring(7));
                intent.putExtra("title", "我的资料");
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
