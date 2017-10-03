package android.letus179.com.givemefive.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.letus179.com.givemefive.MainActivity;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.edit.ClearEditText;
import android.letus179.com.givemefive.edit.IEditTextChangeListener;
import android.letus179.com.givemefive.edit.TextChangeListener;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

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

        SharedPreferences sp = getSharedPreferences("my_operate", MODE_PRIVATE);
        String loginName = sp.getString("loginName", "");
        String loginPwd = sp.getString("loginPwd", "");
        loginNameText.setText(loginName);
        loginPwdText.setText(loginPwd);

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
                // TODO: 2017/10/2
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                // 存储用户名密码
                SharedPreferences.Editor editor = getSharedPreferences("my_operate", MODE_PRIVATE).edit();
                editor.putString("loginName", loginNameEdit.toString());
                editor.putString("loginPwd", loginPwdEdit.toString());
                editor.apply();
                finish();
                break;
            case R.id.register_text:
                // 跳转到注册页面
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("title", "注册一下");
                startActivity(intent);
                finish();
                break;
            case R.id.forget_pwd:
                // 跳转到找回密码页面
                intent = new Intent(LoginActivity.this, GetBackPwdActivity.class);
                intent.putExtra("title", "密码找回");
                startActivity(intent);
                finish();
                break;
            case R.id.weiXin_login:
                // 微信登录
                finish();
                break;
            case R.id.qq_login:
                // qq登录
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 存储用户名密码
        SharedPreferences.Editor editor = getSharedPreferences("my_operate", MODE_PRIVATE).edit();
        editor.putString("loginName", loginNameText.getText() + "");
        editor.putString("loginPwd", loginPwdText.getText() + "");
        editor.apply();
    }
}
