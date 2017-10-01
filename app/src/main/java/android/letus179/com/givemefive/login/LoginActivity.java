package android.letus179.com.givemefive.login;

import android.content.Intent;
import android.letus179.com.givemefive.BasicActivity;
import android.letus179.com.givemefive.ClearEditText;
import android.letus179.com.givemefive.IEditTextChangeListener;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.TextChangeListener;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends BasicActivity implements View.OnClickListener {

    // 登录用户名
    private ClearEditText loginNameText;
    // 登录密码
    private ClearEditText loginPwdText;
    // 登录按钮
    private Button login;
    // 注册跳转
    private TextView registerText;
    // 忘记密码跳转
    private TextView forgetPwd;
    // 微信登录
    private ImageView weiXinLogin;
    // qq登录
    private ImageView qqLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginNameText = (ClearEditText) findViewById(R.id.login_name);
        loginPwdText = (ClearEditText) findViewById(R.id.login_pwd);
        login = (Button) findViewById(R.id.login);
        registerText = (TextView) findViewById(R.id.register_text);
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        weiXinLogin = (ImageView) findViewById(R.id.weiXin_login);
        qqLogin = (ImageView) findViewById(R.id.qq_login);


        login.setOnClickListener(this);
        registerText.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);


        TextChangeListener textChangeListener = new TextChangeListener(login, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    login.setEnabled(true);
                    login.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    login.setEnabled(false);
                    login.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(loginNameText, loginPwdText);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login:
                // 校验用户名密码是否正确
                Editable loginNameEdit = loginNameText.getText();
                Editable loginPwdEdit = loginPwdText.getText();
                if (TextUtils.isEmpty(loginNameEdit + "") || TextUtils.isEmpty(loginPwdEdit + "")) {
                    return;
                }
                // 登录
                break;
            case R.id.register_text:
                // 跳转到注册页面
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_pwd:
                // 跳转到找回密码页面
                intent = new Intent(LoginActivity.this, GetBackPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.weiXin_login:
                // 微信登录
                break;
            case R.id.qq_login:
                // qq登录
                break;
            default:
                break;
        }
    }
}
