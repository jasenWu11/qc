package cn.com.qc.leeactivity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.qc.R;
import cn.com.qc.utils.Tools;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;
public class WatchIntroActivity  extends AppCompatActivity implements  DownloadFile.Listener {

    private RelativeLayout pdf_root;
    private ProgressBar pb_bar;
    private TextView tv_title;
    private RemotePDFViewPager remotePDFViewPager;
    private String mUrl = "";
    private PDFPagerAdapter adapter;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_intro_activity);
        initView();
        initData();
        setDownloadListener();
    }

    protected void initView() {
        pdf_root = (RelativeLayout) findViewById(R.id.remote_pdf_root);
        pb_bar = (ProgressBar) findViewById(R.id.pb_bar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initData() {
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("fileName");
        mUrl = intent.getStringExtra("pdf_url");
        tv_title.setText(fileName);
    }

    /*设置监听*/
    protected void setDownloadListener() {
        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new RemotePDFViewPager(this, mUrl, listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);
    }

    /*加载成功调用*/
    @Override
    public void onSuccess(String url, String destinationPath) {
        pb_bar.setVisibility(View.GONE);
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
    }

    /*更新视图*/
    private void updateLayout() {
        pdf_root.removeAllViewsInLayout();
        pdf_root.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /*加载失败调用*/
    @Override
    public void onFailure(Exception e) {
        pb_bar.setVisibility(View.GONE);
        Tools.toast(WatchIntroActivity.this, "数据加载错误!");
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
    }
}
