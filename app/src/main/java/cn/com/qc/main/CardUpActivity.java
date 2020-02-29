package cn.com.qc.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.util.ProviderUtil;
import com.lzy.imagepicker.util.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.qc.R;
import cn.com.qc.help.Helps;
import cn.com.qc.view.GlideImageLoader;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.lzy.imagepicker.ui.ImageGridActivity.EXTRAS_TAKE_PICKERS;

/**
 * Created by lenovo on 2017/11/30.
 */

public class CardUpActivity extends Activity {

    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        initSystemBarTint();
        unbinder = ButterKnife.bind(this);
        initImagePicker();
        init();
    }

    public int getResId() {
        return R.layout.activity_card_up;
    }

    public void init() {
        light();
    }

    public void light(){
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setAttributes(layoutParams);
    }

    @OnClick({R.id.shoot,R.id.photo,R.id.cancel})
    public void click(View view){
        switch (view.getId()){
            case R.id.shoot:
                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                startActivityForResult(intent, 100);
            //    shoot();
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.photo:
                Intent photoIntent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(photoIntent, 100);
                break;
        }
    }

    @BindView(R.id.shoot)
    TextView shoot;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = shoot.getY();
        float currentY = event.getY();
        if(currentY < y){
            finish();
        }
        return super.onTouchEvent(event);
    }

    protected void initSystemBarTint() {
        Window window = getWindow();
        if (true) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x01) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Helps.toast(this,"权限被禁止，无法选择本地图片");
            }
        } else if (requestCode == 0x02) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture(this,10);
                Log.i("TAG","授权成功");
            } else {
                Helps.toast(this,"权限被禁止，无法打开相机");
            }
        }
    }

    private void shoot(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0x02);
        } else {
            takePicture(this,10);
        }
    }

    private File takeImageFile;

    /** 拍照的方法 */
    public void takePicture(Activity activity, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            if (Utils.existSDCard())
                takeImageFile = new File(Environment.getExternalStorageDirectory(), "/DCIM/camera/");
            else takeImageFile = Environment.getDataDirectory();
            takeImageFile = createFile(takeImageFile, "IMG_", ".jpg");
            if (takeImageFile != null) {
                // 默认情况下，即不需要指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                // 照相机有自己默认的存储路径，拍摄的照片将返回一个缩略图。如果想访问原始图片，
                // 可以通过dat extra能够得到原始图片位置。即，如果指定了目标uri，data就没有数据，
                // 如果没有指定uri，则data就返回有数据！

                Uri uri;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
                    uri = Uri.fromFile(takeImageFile);
                }else{
                    /**
                     * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                     * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                     */
                    uri = FileProvider.getUriForFile(activity, ProviderUtil.getFileProviderName(activity), takeImageFile);
                    //加入uri权限 要不三星手机不能拍照
                    List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities
                            (takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        activity.grantUriPermission(packageName, uri, Intent
                                .FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }

                Log.i("TAG",ProviderUtil.getFileProviderName(activity));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            }
        }
        activity.startActivityForResult(takePictureIntent, requestCode);
    }

    /** 根据系统时间、前缀、后缀产生一个文件 */
    public static File createFile(File folder, String prefix, String suffix) {
        if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;
        return new File(folder, filename);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String fileName = images.get(0).name;
                String path = images.get(0).path;
                long size = images.get(0).size;
                int width = images.get(0).width;
                String mimeType = images.get(0).mimeType;
                long addTime = images.get(0).addTime;
                int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, getResources().getDisplayMetrics());
                Intent intent = new Intent();
                intent.putExtra("fileName",fileName);
                intent.putExtra("path",path);
                setResult(3,intent);
            } else {
                Helps.toast(this, "没有数据");
            }
        }
        finish();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        GlideImageLoader imageLoader = new GlideImageLoader();
        imagePicker.setImageLoader(imageLoader);   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setCrop(false);
    }

}
