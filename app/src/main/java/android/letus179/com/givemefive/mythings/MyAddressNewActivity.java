package android.letus179.com.givemefive.mythings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

public class MyAddressNewActivity extends BasicActivity {

    // 收货人
    private ClearEditText myAddressReceiver;
    // 联系方式
    private ClearEditText myAddressPhone;
    // 所在地址选择
    private View myAddressLocationChooseLayout;
    // 所在地址
    private ClearEditText myAddressLocation;
    // 详细地址
    private ClearEditText myAddressDetail;
    // 是否设置为默认
    private RadioButton myAddressSetDefault;
    // 新增地址按钮
    private Button myAddressSave;
    // 选择默认，没有
    private boolean isChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAddressNewActivity.this, MyAddressActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        myAddressReceiver = (ClearEditText) findViewById(R.id.my_address_receiver);
        myAddressPhone = (ClearEditText) findViewById(R.id.my_address_phone);
        myAddressLocationChooseLayout = (View) findViewById(R.id.my_address_location_choose);
        myAddressLocation = (ClearEditText) findViewById(R.id.my_address_location);
        myAddressDetail = (ClearEditText) findViewById(R.id.my_address_detail);
        myAddressSetDefault = (RadioButton) findViewById(R.id.my_address_set_default);
        myAddressSave = (Button) findViewById(R.id.my_address_save);

        myAddressLocation.setKeyListener(null);

        // 所在地区选择
        myAddressLocationChooseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseArea(v);
            }
        });

        // 设为默认地址
        myAddressSetDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    myAddressSetDefault.setChecked(false);
                    isChecked = false;
                } else {
                    myAddressSetDefault.setChecked(true);
                    isChecked = true;
                }
            }
        });

        // 新增地址
        myAddressSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = myAddressPhone.getText().toString();
                String receiver = myAddressReceiver.getText().toString();
                String detail = myAddressDetail.getText().toString();
                String location = myAddressLocation.getText().toString();

                if (!ValidatorUtils.isMobile(phone)) {
                    Toast.makeText(MyAddressNewActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (receiver.length() <= 1) {
                    Toast.makeText(MyAddressNewActivity.this, "姓名长度为2~12个字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (detail.length() < 5) {
                    Toast.makeText(MyAddressNewActivity.this, "详细地址长度不能小于5", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(MyAddressNewActivity.this, "收货人：" + receiver + ", \n手机号：" + phone +
                        ", \n所在地区：" + location + ", \n详细地址：" + detail + ", \n设置默认：" + isChecked, Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        // 监听按钮
        TextChangeListener textChangeListener = new TextChangeListener(myAddressSave, new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    myAddressSave.setEnabled(true);
                    myAddressSave.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_pressed, null));
                } else {
                    myAddressSave.setEnabled(false);
                    myAddressSave.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.clr_normal, null));
                }
            }
        });
        // 传入所有要监听的editText都添加进入
        textChangeListener.addAllEditText(myAddressReceiver, myAddressPhone, myAddressLocation, myAddressDetail);
    }

    // 选择所在区域
    public void chooseArea(View view) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress();//调用CityPicker选取区域

        }
    }

    // 选择区域
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(MyAddressNewActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("北京市")
                .city("北京市")
                .district("海淀区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                myAddressLocation.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }
}
