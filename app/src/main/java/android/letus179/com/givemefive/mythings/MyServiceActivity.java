package android.letus179.com.givemefive.mythings;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.common.Constants;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.necer.ndialog.NDialog;

public class MyServiceActivity extends BasicActivity implements View.OnClickListener {

    // 服务热线，在线客服，微信服务，购买须知
    private View myServicePhoneView, myServiceOnlineView, myServiceWeixinView, myServiceReadView;

    // 剪切板管理工具类
    private ClipboardManager mClipboardManager ;

    // 剪切板Data对象
    private ClipData mClipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyServiceActivity.this, MyAllActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        myServicePhoneView = findViewById(R.id.my_service_phone);
        myServiceOnlineView = findViewById(R.id.my_service_online);
        myServiceWeixinView = findViewById(R.id.my_service_weixin);
        myServiceReadView = findViewById(R.id.my_service_read);
        myServicePhoneView.setOnClickListener(this);
        myServiceOnlineView.setOnClickListener(this);
        myServiceWeixinView.setOnClickListener(this);
        myServiceReadView.setOnClickListener(this);

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_service_phone:
                // 客服热线
                hotLineServiceDialog();
                break;
            case R.id.my_service_online:
                // 在线服务
                // TODO: 2017/10/5
                break;
            case R.id.my_service_weixin:
                // 微信服务
                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("simple test",Constants.MY_SERVICE_WEIXIN_SERVICE);
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);
                weiXinServiceDialog();
                break;
            case R.id.my_service_read:
                // TODO: 2017/10/5  
                break;
            default:
                break;
        }
    }

    /**
     * 创建 客服热线 选择对话框
     */
    private void hotLineServiceDialog() {
        new NDialog(this)
                .setTitle(Constants.MY_SERVICE_HOT_LINE)
                .setItems(new String[]{"拨打", "发信息", "加为联系人"})
                .setItemGravity(Gravity.LEFT)
                .setItemColor(Color.parseColor("#000000"))
                .setItemHeigh(50)
                .setItemSize(16)
                .setDividerHeigh(1)
                .setAdapter(null)
                .setDividerColor(Color.parseColor("#c1c1c1"))
                .setHasDivider(true)
                .setOnChoiceListener(new NDialog.OnChoiceListener() {
                    @Override
                    public void onClick(String item, int which) {
                        Intent intent = null;
                        if ("拨打".equals(item)) {
                            intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + Constants.MY_SERVICE_HOT_LINE));

                        } else if ("发信息".equals(item)) {
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("smsto:" + Constants.MY_SERVICE_HOT_LINE));

                        } else if ("加为联系人".equals(item)) {
                            intent = new Intent(Intent.ACTION_INSERT,Uri.withAppendedPath(Uri.parse("content://com.android.contacts"), "contacts"));
                            intent.setType("vnd.android.cursor.dir/person");
                            intent.setType("vnd.android.cursor.dir/contact");
                            intent.setType("vnd.android.cursor.dir/raw_contact");
                            intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE,  Constants.MY_SERVICE_HOT_LINE);
                        }
                        startActivity(intent);
                    }
                }).create(NDialog.CHOICE).show();
    }

    /**
     * 微信服务 选择框
     */
    private void weiXinServiceDialog() {
        new NDialog(this)
                .setMessageCenter(false)
                .setMessage("GMF微信" + Constants.MY_SERVICE_WEIXIN_SERVICE + "已复制到剪切板，\n去微信添加好吧^^")
                .setMessageSize(16)
                .setNegativeButtonText("就不去")
                .setPositiveButtonText("现在就去")
                .setButtonCenter(true)
                .setButtonSize(14)
                .setCancleable(true)
                .setOnConfirmListener(new NDialog.OnConfirmListener() {
                    @Override
                    public void onClick(int which) {
                        //which,0代表NegativeButton，1代表PositiveButton
                        if (which == 1) {
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_VIEW);
                            try{
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(MyServiceActivity.this, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                }).create(NDialog.CONFIRM).show();
    }
}
