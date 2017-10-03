package android.letus179.com.givemefive.mythings;

import android.content.Intent;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.edit.ClearEditText;
import android.letus179.com.givemefive.edit.IEditTextChangeListener;
import android.letus179.com.givemefive.edit.TextChangeListener;
import android.letus179.com.givemefive.utils.ValidatorUtils;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.necer.ndialog.NDialog;

/**
 * 意见反馈
 */
public class MySuggestionActivity extends BasicActivity {

    // 订单问题还是其他问题
    private RadioGroup radioGroup;

    // 问题类型 默认
    private String suggestType = "订单问题";

    // 输入的反馈
    private ClearEditText inputSuggest;

    // 输入的字数
    private TextView countNum;
    private int maxMun = 350;

    // 手机号
    private ClearEditText mySugPhone;

    // 发送反馈
    private Button mySugSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suggestion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MySuggestionActivity.this, MyAllActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        countNum = (TextView) findViewById(R.id.my_sug_input_num_count);
        mySugSend = (Button) findViewById(R.id.my_sug_send);
        inputSuggest = (ClearEditText) findViewById(R.id.my_sug_input);
        mySugPhone = (ClearEditText) findViewById(R.id.my_sug_phone);

        // 选择的反馈问题类型
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkedRadioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) MySuggestionActivity.this.findViewById(checkedRadioButtonId);
                suggestType = radioButton.getText().toString();
            }
        });

        // 输入框字数限制
        countNum.setText("0/350");

        // 输入框监听
        inputSuggest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                countNum.setText((maxMun - s.length()) + "/" + maxMun);
            }
        });


        // 发送反馈按钮
        mySugSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 0.手机号
                String phone = mySugPhone.getText().toString();
                if (!ValidatorUtils.isMobile(phone)) {
                    Toast.makeText(MySuggestionActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
                    return;
                }
                // TODO: 2017/10/3 调接口
                // 1.订单类型  suggestType
                // 2.具体建议
                String suggestion = inputSuggest.getText().toString();
                Toast.makeText(MySuggestionActivity.this, "手机号:" + phone + ", \n反馈类型：" + suggestType + ",\n具体建议：" + suggestion, Toast.LENGTH_SHORT).show();

                // 对话框
                dialog();
            }
        });

        // 监听按钮
        TextChangeListener textChangeListener = new TextChangeListener(mySugSend, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    mySugSend.setEnabled(true);
                    mySugSend.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    mySugSend.setEnabled(false);
                    mySugSend.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(inputSuggest, mySugPhone);
    }

    /**
     * NDialog有三种形式：确认框、选择框、输入框
     */
    private void dialog() {
        final NDialog dialog = new NDialog(MySuggestionActivity.this);
        dialog.setMessage("反馈以送达，我们将尽快处理您的反馈，谢谢。").
                setMessageSize(12).
                setMessageCenter(true).
                setNegativeButtonText("").
                setButtonCenter(true).
                setOnConfirmListener(new NDialog.OnConfirmListener() {
                    @Override
                    public void onClick(int which) {
                        //which,0代表NegativeButton，1代表PositiveButton
                        Intent intent = new Intent(MySuggestionActivity.this, MyAllActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create(NDialog.CONFIRM).show();
    }
}
