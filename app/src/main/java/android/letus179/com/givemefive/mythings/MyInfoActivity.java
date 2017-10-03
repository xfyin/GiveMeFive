package android.letus179.com.givemefive.mythings;

import android.content.Intent;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyInfoActivity extends BasicActivity implements View.OnClickListener{


    private RelativeLayout myNikeLayout;
    private TextView nickName;

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

        myNikeLayout = (RelativeLayout) findViewById(R.id.my_info_nick);
        nickName = (TextView) findViewById(R.id.my_info_nick1);
        myNikeLayout.setOnClickListener(this);



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
}
