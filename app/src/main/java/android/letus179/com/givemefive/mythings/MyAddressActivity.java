package android.letus179.com.givemefive.mythings;

import android.content.Intent;
import android.graphics.Color;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.adapter.AddressAdapter;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.common.Constants;
import android.letus179.com.givemefive.entity.Address;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.necer.ndialog.NDialog;

import java.util.ArrayList;
import java.util.List;

public class MyAddressActivity extends BasicActivity {

    private Button myAddressAdd;

    private ListView myAddressList;

    private List<Address> addressList;

    private AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("title");
        setupBackAsUp(title, true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAddressActivity.this, MyAllActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_slide_in, R.anim.right_slide_out);
            }
        });
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);

        myAddressAdd = (Button) findViewById(R.id.my_address_add);
        myAddressAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAddressActivity.this, MyAddressNewActivity.class);
                intent.putExtra("title", "新增地址");
                startActivityForResult(intent, Constants.ADD_ADDRESS);
                finish();
            }
        });

        myAddressList = (ListView) findViewById(R.id.my_address_list);
        initAddresses();
        adapter = new AddressAdapter(MyAddressActivity.this, R.layout.address_item, addressList);
        myAddressList.setAdapter(adapter);
        myAddressList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //return的值决定是否在长按后再加一个短按动作 true为不加短按,false为加入短按
                deleteDialog(position);
                return true;
            }
        });
    }

    /**
     * 长按后，弹出删除收货地址条目的对话框
     */
    private void deleteDialog(final int position) {
        new NDialog(this)
                .setMessageCenter(true)
                .setMessageSize(16)
                .setMessage("确认删除该收货地址？")
                .setNegativeButtonText("取消")
                .setPositiveButtonText("确定")
                .setNegativeTextColor(Color.parseColor("#D2691E"))
                .setPositiveTextColor(Color.parseColor("#D2691E"))
                .setButtonCenter(true)
                .setButtonSize(14)
                .setCancleable(true)
                .setHasDivider(true)
                .setOnConfirmListener(new NDialog.OnConfirmListener() {
                    @Override
                    public void onClick(int which) {
                        //which,0代表NegativeButton，1代表PositiveButton
                        if (which == 1) {
                            // TODO: 2017/10/4  从DB中删除收货地址
                            addressList.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }).create(NDialog.CONFIRM).show();
    }

    // TODO: 2017/10/4
    private void initAddresses() {
        addressList = new ArrayList<>();
        Address ad = new Address();
        ad.setName("殷小飞");
        ad.setPhone("15201552704");
        ad.setLocation("北京市 北京市 海淀区");
        ad.setDetail("西二旗大街2号楼111111111111111111111401");
        addressList.add(ad);
        for (int i = 0; i < 18; i++) {
            ad = new Address();
            ad.setName(i + "殷学飞");
            ad.setPhone("15201552704");
            ad.setLocation("北京市 北京市 海淀区");
            ad.setDetail("西二旗大街2号楼1401");
            addressList.add(ad);
        }

    }
}
