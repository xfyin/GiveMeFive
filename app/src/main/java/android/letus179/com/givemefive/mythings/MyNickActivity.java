package android.letus179.com.givemefive.mythings;

import android.content.Intent;
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
import android.widget.Toast;

public class MyNickActivity extends BasicActivity {

    private ClearEditText myNickModifyEdit;

    private Button myNickModifySaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_nick);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNickActivity.this, MyInfoActivity.class);
                intent.putExtra("title", "我的资料");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        // 需要修改的昵称
        myNickModifyEdit = (ClearEditText) findViewById(R.id.my_nick_modify);
        myNickModifyEdit.setText(getIntent().getStringExtra("nick"));

        // 修改按钮
        myNickModifySaveButton = (Button) findViewById(R.id.my_nick_modify_save);
        myNickModifySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNick = myNickModifyEdit.getText().toString();
                // 校验
                if (!ValidatorUtils.isNickName(newNick)) {
                    Toast.makeText(MyNickActivity.this, "昵称不合法", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO: 2017/10/3 保存入库

                // 回调
                Intent intent = new Intent();
                intent.putExtra("new_nick", newNick);
                intent.putExtra("title", "我的资料");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // 监听按钮
        TextChangeListener textChangeListener = new TextChangeListener(myNickModifySaveButton, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    myNickModifySaveButton.setEnabled(true);
                    myNickModifySaveButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    myNickModifySaveButton.setEnabled(false);
                    myNickModifySaveButton.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(myNickModifyEdit);
    }
}
