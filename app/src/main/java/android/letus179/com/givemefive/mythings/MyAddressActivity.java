package android.letus179.com.givemefive.mythings;

import android.content.Intent;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.common.Constants;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MyAddressActivity extends BasicActivity {

    private Button myAddressAdd;

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
    }
}
