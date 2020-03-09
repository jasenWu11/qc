package cn.com.qc.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import com.lzy.okgo.model.Response;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hb.dialog.myDialog.MyAlertDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.qc.R;
import cn.com.qc.adapter.IntroAdapter;
import cn.com.qc.help.NetUrl;
import cn.com.qc.javabean.IntroInfo;
import cn.com.qc.utils.Tools;
import cn.com.qc.view.SwipeToLoadLayout;
import cn.com.qc.yinter.OnLoadMoreListener;
import cn.com.qc.yinter.OnRefreshListener;

public class IntroFileActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {
    ListView swipeTarget;
    SwipeToLoadLayout swipeToLoadLayout;
    private int pageNumber = 1;
    private IntroAdapter introAdapter;
    private List<IntroInfo> introInfoList;
    private Integer total_pages = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_file);
        swipeTarget = (ListView) findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        introInfoList = new ArrayList<>();
        initData(pageNumber);
        introAdapter = new IntroAdapter(introInfoList, this);
        swipeTarget.setAdapter(introAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }
    public void initData(int pageNumber) {
        OkGo.<String>post(NetUrl.DNS + NetUrl.Cvlist)
                .tag(this)
                //.params("WorkPlace", city)
                .params("pageIndex", pageNumber)
                .params("pageSize", 10)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            int infoCode = jsonObject.getInt("status");
                            if (infoCode == 0) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                total_pages = jsonObject1.getInt("pages");
                                JSONArray jsonArray = jsonObject1.getJSONArray("records");
                                System.out.println("返回的数据是"+jsonArray);
                                if (jsonArray.length() == 0) {

                                } else {
                                    introInfoList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        IntroInfo introInfo = new IntroInfo();
                                        introInfo.setIndex(i+1);
                                        introInfo.setCreateTime(jsonObject2.getString("createTime"));
                                        introInfo.setId(jsonObject2.getInt("id"));
                                        introInfo.setFileName(jsonObject2.getString("fileName"));
                                        introInfo.setFileId(jsonObject2.getString("fileId"));
                                        introInfo.setPaths(jsonObject2.getString("paths"));
                                        introInfoList.add(introInfo);
                                    }
                                    introAdapter.addData(introInfoList);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                introAdapter.clear();
                pageNumber = 1;
                initData(pageNumber);
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 100);
    }

    @Override
    public void onLoadMore() {
        if(pageNumber>=total_pages){
            Tools.toast(IntroFileActivity.this, "已全部加载完成!");
            swipeToLoadLayout.setLoadingMore(false);
        }else{
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pageNumber++;
                    initData(pageNumber);
                    swipeToLoadLayout.setLoadingMore(false);
                }
            }, 100);
        }
    }
    // 打开系统的文件选择器
    public void pickFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("image/*");//选择图片
        //intent.setType("audio/*"); //选择音频
        //intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType("video/*;image/*");//同时选择视频和图片
        intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    // 获取文件的真实路径
    String path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                path = uri.getPath();
                ShowDiglog(path);
                System.out.println("获得地址"+path);
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                System.out.println("获得地址"+path);
                ShowDiglog(path);
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                System.out.println("获得地址"+path);
                ShowDiglog(path);
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void uploadFile(String path){
        File file = new File(path);
        OkGo.<String>post(NetUrl.DNS + NetUrl.UploadCv)
                .tag(this)
                .params("file", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            System.out.println("输出结果"+jsonObject);
                            int infoCode = jsonObject.getInt("status");
                            if (infoCode == 0) {
                                Tools.toast(IntroFileActivity.this, "简历上传成功!");
                                introAdapter.clear();
                                pageNumber = 1;
                                initData(pageNumber);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void ShowDiglog(final String path){
        MyAlertDialog myAlertDialog = new MyAlertDialog(this).builder()
                .setTitle("上传简历？")
                .setMsg(path)
                .setPositiveButton("上传", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploadFile(path);
                        System.out.println("上传");
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("取消");
                    }
                });
        myAlertDialog.show();
    }
}

