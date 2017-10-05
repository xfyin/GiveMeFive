package android.letus179.com.givemefive.adapter;

import android.content.Context;
import android.content.Intent;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.entity.Address;
import android.letus179.com.givemefive.mythings.MyAddressNewActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 收货地址适配器
 * <p>
 * Created by xfyin on 2017/10/4.
 */

public class AddressAdapter extends ArrayAdapter<Address> {

    // Listview 布局项id
    private int resourceId;

    // 收货地址列表
    private List<Address> addressList;

    private Context context;

    public AddressAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List addressList) {
        super(context, resource, addressList);
        this.resourceId = resource;
        this.addressList = addressList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获取当前项的实例
        final Address address = getItem(position);
        View view;
        AddressViewHolder addressViewHolder;
        // convertView 用于将之前加载好的布局进行缓存，以便重用
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            addressViewHolder = new AddressViewHolder();
            addressViewHolder.itemName = (TextView) view.findViewById(R.id.my_address_item_name);
            addressViewHolder.itemPhone = (TextView) view.findViewById(R.id.my_address_item_phone);
            addressViewHolder.itemLocationDetail = (TextView) view.findViewById(R.id.my_address_item_address);
            // 将viewHolder存储在view中
            view.setTag(addressViewHolder);
        } else {
            view = convertView;
            addressViewHolder = (AddressViewHolder) view.getTag();
        }
        addressViewHolder.itemName.setText(address.getName());
        addressViewHolder.itemPhone.setText(address.getPhone());
        addressViewHolder.itemLocationDetail.setText(address.getLocation() + address.getDetail());

        // 编辑收货地址
        TextView myAddressEdit = (TextView) view.findViewById(R.id.my_address_edit);
        myAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyAddressNewActivity.class);
                intent.putExtra("title","编辑收货地址");
                intent.putExtra("edit_address",address);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Nullable
    @Override
    public Address getItem(int position) {
        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

class AddressViewHolder {
    // 姓名
    TextView itemName;
    // 手机号
    TextView itemPhone;
    // 所在区域 + 详细地址
    TextView itemLocationDetail;
}
