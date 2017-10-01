package android.letus179.com.givemefive.login;

import android.content.Intent;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.edit.ClearEditText;
import android.letus179.com.givemefive.edit.IEditTextChangeListener;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.edit.TextChangeListener;
import android.letus179.com.givemefive.utils.ValidatorUtils;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
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

        loginText = (TextView) findViewById(R.id.login_text);
        register = (Button) findViewById(R.id.register);
        registerPhoneText = (ClearEditText) findViewById(R.id.register_phone);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先 校验手机号是否正确


                boolean mobile = ValidatorUtils.isMobile(registerPhoneText.getText() + "");
                if (mobile) {

                    // 图形验证码


                    // 跳转
                    Intent intent = new Intent(RegisterActivity.this, RegisterMainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "手机号码不正确", Toast.LENGTH_SHORT).show();
                }


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
    }
}
