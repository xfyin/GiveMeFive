package android.letus179.com.givemefive.mythings;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.letus179.com.givemefive.R;
import android.letus179.com.givemefive.common.BasicActivity;
import android.letus179.com.givemefive.common.Constants;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfoActivity extends BasicActivity implements View.OnClickListener {

    // 我的头像
    private CircleImageView myInfoPic;
    // 拍照
    private TextView myInfoPicTake;
    // 从相册选择
    private TextView myInfoPicChoose;
    private Uri imageUri;

    // 昵称
    private RelativeLayout myNikeLayout;
    private TextView nickName;

    // 性别
    private RelativeLayout myInfoSexLayout;
    private TextView myInfoSex;
    private Dialog dialog;

    //手机号
    private RelativeLayout myInfoPhoneLayout;
    private TextView myInfoPhone;

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

        // 头像选择
        myInfoPic = (CircleImageView) findViewById(R.id.my_info_pic);
        myInfoPic.setOnClickListener(this);

        // 昵称
        myNikeLayout = (RelativeLayout) findViewById(R.id.my_info_nick);
        nickName = (TextView) findViewById(R.id.my_info_nick1);
        myNikeLayout.setOnClickListener(this);

        // 性别
        myInfoSexLayout = (RelativeLayout) findViewById(R.id.my_info_sex);
        myInfoSex = (TextView) findViewById(R.id.my_info_sex1);
        myInfoSexLayout.setOnClickListener(this);

        //手机号
        myInfoPhoneLayout = (RelativeLayout) findViewById(R.id.my_info_phone);
        myInfoPhone = (TextView) findViewById(R.id.my_info_phone1);
        myInfoPhoneLayout.setOnClickListener(this);

        String newPhone = getIntent().getStringExtra("new_phone");
        if (!TextUtils.isEmpty(newPhone)) {
            myInfoPhone.setText(newPhone);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.my_info_pic:
                // 弹出对话框选择图片来源
                LinearLayout picControl = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.pic_control, null);
                picControl.findViewById(R.id.my_info_pic_take).setOnClickListener(this);
                picControl.findViewById(R.id.my_info_pic_choose).setOnClickListener(this);
                sexDialog(picControl);
                break;
            case R.id.my_info_pic_take:
                // 拍照
                // 创建File对象，用于存储拍照后的对象(应用关联缓存目录)
                File outputImage = new File(getExternalCacheDir(), "my_pic.png");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Android 7.0 开始，使用本地真是路径的Uri被认为不安全
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(MyInfoActivity.this, "com.givemefive.takephone.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                // 启动相机程序
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, Constants.TAKE_PHONE);
                break;
            case R.id.my_info_pic_choose:
                // 从相册选择
                if (ContextCompat.checkSelfPermission(MyInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MyInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
            case R.id.my_info_nick:
                //修改昵称
                intent = new Intent(MyInfoActivity.this, MyNickActivity.class);
                intent.putExtra("nick", nickName.getText() + "");
                intent.putExtra("title", "修改昵称");
                startActivityForResult(intent, Constants.MODIFY_NICK_NAME);
                break;
            case R.id.my_info_sex:
                // 弹出性别选择对话框
                LinearLayout sexControl = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.sex_control, null);
                sexControl.findViewById(R.id.my_sex_unknown).setOnClickListener(this);
                sexControl.findViewById(R.id.my_sex_man).setOnClickListener(this);
                sexControl.findViewById(R.id.my_sex_woman).setOnClickListener(this);
                sexDialog(sexControl);
                break;
            case R.id.my_sex_unknown:
                myInfoSex.setText("神秘");
                dialog.dismiss();
                // TODO: 2017/10/3 入库
                break;
            case R.id.my_sex_man:
                myInfoSex.setText("男士");
                // TODO: 2017/10/3 入库
                dialog.dismiss();
                break;
            case R.id.my_sex_woman:
                myInfoSex.setText("女士");
                // TODO: 2017/10/3 入库
                dialog.dismiss();
                break;
            case R.id.my_info_phone:
                // 换绑手机号
                intent = new Intent(MyInfoActivity.this, MyPhoneActivity.class);
                intent.putExtra("title", "换绑手机号");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.MODIFY_NICK_NAME:
                    String result = data.getExtras().getString("new_nick");
                    nickName.setText(result);
                    break;
                case Constants.TAKE_PHONE:
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        myInfoPic.setImageBitmap(bitmap);
                        dialog.dismiss();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.CHOOSE_PHONE:
                    if (Build.VERSION.SDK_INT >= 19) {
                        // android4.4及以上
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(MyInfoActivity.this, "打开相册失败！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 修改性别对话框
     *
     * @return
     */
    private Dialog sexDialog(View view) {
        dialog = new Dialog(this, R.style.my_dialog);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogStyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -40; // 新位置Y坐标
        lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
        view.measure(0, 0);
        lp.height = view.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, Constants.CHOOSE_PHONE);
    }

    /*
     * android4.4一下
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * android4.4及以上
     *
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                // 解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        // 根据图片的路径显示图片
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            myInfoPic.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取图片失败!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 通过Url和selection来获取真实的图片路径
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
