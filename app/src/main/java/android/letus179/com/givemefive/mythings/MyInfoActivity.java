package android.letus179.com.givemefive.mythings;

import android.app.Dialog;
import android.content.Intent;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyInfoActivity extends BasicActivity implements View.OnClickListener {


    // 昵称
    private RelativeLayout myNikeLayout;
    private TextView nickName;

    // 性别
    private RelativeLayout myInfoSexLayout;
    private TextView myInfoSex;
    private Dialog dialogSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, MyAllActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        // 昵称
        myNikeLayout = (RelativeLayout) findViewById(R.id.my_info_nick);
        nickName = (TextView) findViewById(R.id.my_info_nick1);
        myNikeLayout.setOnClickListener(this);

        // 性别
        myInfoSexLayout = (RelativeLayout) findViewById(R.id.my_info_sex);
        myInfoSex = (TextView) findViewById(R.id.my_info_sex1);
        myInfoSexLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.my_info_nick:
                intent = new Intent(MyInfoActivity.this, MyNickActivity.class);
                intent.putExtra("nick", nickName.getText() + "");
                intent.putExtra("title", "修改昵称");
                startActivityForResult(intent, 1);
                break;
            case R.id.my_info_sex:
                // 弹出性别选择对话框
                sexDialog();
                break;
            case R.id.my_sex_unknown:
                myInfoSex.setText("神秘");
                dialogSex.dismiss();
                // TODO: 2017/10/3 入库
                break;
            case R.id.my_sex_man:
                myInfoSex.setText("男士");
                // TODO: 2017/10/3 入库
                dialogSex.dismiss();
                break;
            case R.id.my_sex_woman:
                myInfoSex.setText("女士");
                // TODO: 2017/10/3 入库
                dialogSex.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = data.getExtras().getString("new_nick");//得到新Activity 关闭后返回的数据
        Log.i("onActivityResult", result);
        nickName.setText(result);
    }


    /**
     * 修改性别对话框
     *
     * @return
     */
    private Dialog sexDialog() {
        dialogSex = new Dialog(this, R.style.my_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.sex_control, null);
        root.findViewById(R.id.my_sex_unknown).setOnClickListener(this);
        root.findViewById(R.id.my_sex_man).setOnClickListener(this);
        root.findViewById(R.id.my_sex_woman).setOnClickListener(this);
        dialogSex.setContentView(root);
        Window dialogWindow = dialogSex.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogStyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 150; // 新位置Y坐标
        lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        dialogSex.show();
        return dialogSex;
    }
}
