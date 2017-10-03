package android.letus179.com.givemefive.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.edit.ClearEditText;
import android.letus179.com.givemefive.edit.IEditTextChangeListener;
import android.letus179.com.givemefive.edit.TextChangeListener;
import android.letus179.com.givemefive.utils.ValidatorUtils;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BasicActivity {

    private TextView loginText;
    private Button register;
    private ClearEditText registerPhoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("title", "登录");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        loginText = (TextView) findViewById(R.id.login_text);
        register = (Button) findViewById(R.id.register);
        registerPhoneText = (ClearEditText) findViewById(R.id.register_phone);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("title", "登录");
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先 校验手机号是否正确


                boolean mobile = ValidatorUtils.isMobile(registerPhoneText.getText() + "");
                if (mobile) {

                    // 图形验证码
                    // TODO: 2017/10/2

                    // 跳转
                    Intent intent = new Intent(RegisterActivity.this, RegisterMainActivity.class);
                    intent.putExtra("title", "注册");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "手机号码不正确", Toast.LENGTH_SHORT).show();
                }

                // 存储手机号
                SharedPreferences.Editor editor = getSharedPreferences("my_operate", MODE_PRIVATE).edit();
                editor.putString("mobile", registerPhoneText.getText() + "");
                editor.apply();
            }
        });

        TextChangeListener textChangeListener = new TextChangeListener(register, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    register.setEnabled(true);
                    register.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    register.setEnabled(false);
                    register.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(registerPhoneText);

        SharedPreferences sp = getSharedPreferences("my_operate", MODE_PRIVATE);
        String mobile = sp.getString("mobile", "");
        registerPhoneText.setText(mobile);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences("my_operate", MODE_PRIVATE).edit();
        editor.putString("mobile", registerPhoneText.getText() + "");
        editor.apply();
    }
}
