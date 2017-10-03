package android.letus179.com.givemefive.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.edit.ClearEditText;
import android.letus179.com.givemefive.edit.IEditTextChangeListener;
import android.letus179.com.givemefive.edit.TextChangeListener;
import android.letus179.com.givemefive.utils.CodeUtils;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class GetBackPwdActivity extends BasicActivity implements View.OnClickListener{


    private ClearEditText phoneCodeText;

    private EditText inputCodeText;

    private ImageView productCodeImage;

    private Button sendGetBackPwdButton;

    private CodeUtils codeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_back_pwd);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetBackPwdActivity.this, LoginActivity.class);
                intent.putExtra("title", "登录");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        phoneCodeText = (ClearEditText) findViewById(R.id.phone_code);
        inputCodeText = (EditText) findViewById(R.id.input_code);
        productCodeImage = (ImageView) findViewById(R.id.product_code);
        sendGetBackPwdButton = (Button) findViewById(R.id.send_getBack_pwd);

        productCodeImage.setOnClickListener(this);
        sendGetBackPwdButton.setOnClickListener(this);

        TextChangeListener textChangeListener = new TextChangeListener(sendGetBackPwdButton, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    sendGetBackPwdButton.setEnabled(true);
                    sendGetBackPwdButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    sendGetBackPwdButton.setEnabled(false);
                    sendGetBackPwdButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(phoneCodeText, inputCodeText);

        // 打开该activity，产生验证码
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        productCodeImage.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_code:
                // 产生验证码
                Bitmap bitmap = codeUtils.createBitmap();
                productCodeImage.setImageBitmap(bitmap);
                break;

            case R.id.send_getBack_pwd:
                // 找回密码确认
                Editable inputCode = inputCodeText.getText();
                if (inputCode == null || TextUtils.isEmpty(inputCode.toString())) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = codeUtils.getCode();
                if (!code.equalsIgnoreCase(inputCode.toString().trim())) {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                String phone = phoneCodeText.getText().toString();
                // 判断该号码是否注册
                // TODO: 2017/10/4  


                break;

            default:
                break;
        }
    }
}
