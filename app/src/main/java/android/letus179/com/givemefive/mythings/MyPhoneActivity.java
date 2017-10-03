package android.letus179.com.givemefive.mythings;

import android.content.DialogInterface;
import android.content.Intent;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.edit.IEditTextChangeListener;
import android.letus179.com.givemefive.edit.TextChangeListener;
import android.letus179.com.givemefive.utils.TimeCountUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyPhoneActivity extends BasicActivity implements View.OnClickListener{

    // 验证码
    private EditText myInfoPhoneCodeText;
    // 发送验证码
    private Button myInfoPhoneCodeSend;
    // 下一步
    private Button myInfoPhoneNextSend;
    // 提示信息
    private TextView myInfoPhoneTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_phone);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPhoneActivity.this, MyInfoActivity.class);
                intent.putExtra("title", "我的资料");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        myInfoPhoneCodeText = (EditText) findViewById(R.id.my_info_phone_code);
        myInfoPhoneTip = (TextView) findViewById(R.id.my_info_phone_tip);
        myInfoPhoneCodeSend = (Button) findViewById(R.id.my_info_phone_code_send);
        myInfoPhoneNextSend = (Button) findViewById(R.id.my_info_phone_next);
        myInfoPhoneCodeSend.setOnClickListener(this);
        myInfoPhoneNextSend.setOnClickListener(this);
        myInfoPhoneTip.setOnClickListener(this);

        // 监听按钮
        TextChangeListener textChangeListener = new TextChangeListener(myInfoPhoneNextSend, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    myInfoPhoneNextSend.setEnabled(true);
                    myInfoPhoneNextSend.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    myInfoPhoneNextSend.setEnabled(false);
                    myInfoPhoneNextSend.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(myInfoPhoneCodeText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_phone_code_send:
                // 发送验证码
                TimeCountUtils timeCountUtils = new TimeCountUtils(this, myInfoPhoneCodeSend, 60000, 1000);
                timeCountUtils.start();
                break;
            case R.id.my_info_phone_next:
                // TODO: 2017/10/3
                // 校验验证码

                Toast.makeText(MyPhoneActivity.this, "验证码正确", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyPhoneActivity.this, MyPhoneNewActivity.class);
                intent.putExtra("title","换绑手机号");
                startActivity(intent);

                break;
            case R.id.my_info_phone_tip:
                // 对话框
                dialog();
            default:
                break;
        }
    }

    private void dialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(MyPhoneActivity.this);
        dialog.setMessage("若您原手机号无法接收验证码，\n\n请致电客服400-800-8820修改");
        dialog.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:4008008820"));
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}
