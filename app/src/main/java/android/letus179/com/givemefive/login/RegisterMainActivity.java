package android.letus179.com.givemefive.login;

import android.content.Intent;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.edit.ClearEditText;
import android.letus179.com.givemefive.edit.IEditTextChangeListener;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.edit.TextChangeListener;
import android.letus179.com.givemefive.utils.TimeCountUtils;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.letus179.com.givemefive.R.id.agree_protocol;
import static android.letus179.com.givemefive.R.id.obtain_code;

public class RegisterMainActivity extends BasicActivity implements View.OnClickListener {

    private EditText registerCodeText;
    private Button obtainCodeButton;
    private ClearEditText pwdText;
    private ClearEditText pwdTextAgain;
    private Button registerCompleteButton;
    private CheckBox agreeProtocol;
    private TextView registerProtocol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        registerCodeText = (EditText) findViewById(R.id.code_text);
        obtainCodeButton = (Button) findViewById(obtain_code);
        pwdText = (ClearEditText) findViewById(R.id.pwd_text);
        pwdTextAgain = (ClearEditText) findViewById(R.id.pwd_text_again);
        registerCompleteButton = (Button) findViewById(R.id.register_complete);
        agreeProtocol = (CheckBox) findViewById(agree_protocol);
        registerProtocol = (TextView) findViewById(R.id.register_protocol);

        obtainCodeButton.setOnClickListener(this);
        registerCompleteButton.setOnClickListener(this);
        registerProtocol.setOnClickListener(this);

        TextChangeListener textChangeListener = new TextChangeListener(registerCompleteButton, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    registerCompleteButton.setEnabled(true);
                    registerCompleteButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    registerCompleteButton.setEnabled(false);
                    registerCompleteButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(registerCodeText, pwdText, pwdTextAgain);

        TimeCountUtils timeCountUtils = new TimeCountUtils(this, obtainCodeButton, 60000, 1000);
        timeCountUtils.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.obtain_code:
                // 发送验证码
                TimeCountUtils timeCountUtils = new TimeCountUtils(this, obtainCodeButton, 60000, 1000);
                timeCountUtils.start();
                break;
            case R.id.register_complete:
                if (!agreeProtocol.isChecked()) {
                    Toast.makeText(RegisterMainActivity.this, "请先同意用户《注册协议》", Toast.LENGTH_SHORT).show();
                }
                //注册
                break;
            case R.id.register_protocol:
                // 注册协议
                Intent intent = new Intent(RegisterMainActivity.this, RegisterProtocolActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
