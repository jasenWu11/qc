package cn.com.qc.main;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.app.App;
import cn.com.qc.bean.ImageMoreUpBean;
import cn.com.qc.help.Helps;
import cn.com.qc.help.NetUrl;
import cn.com.qc.view.CardImageView;
import cn.com.qc.view.CropRetangleTransformation;
import cn.com.qc.yinter.CardCheckDataListener;
import cn.com.qc.ypresenter.BasePresenter;
import cn.com.qc.ypresenter.CardCheckPresenter;
import cn.com.qc.ypresenter.HomePresenter;

/**
 * Created by lenovo on 2017/11/30.
 */

public class CardCheckActivity extends YBaseActivity<CardCheckPresenter,CardCheckActivity> implements CardCheckDataListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.scanFront)
    RelativeLayout scanFront;
    @BindView(R.id.scanBack)
    RelativeLayout scanBack;

    @BindView(R.id.scanFrontImage)
    CardImageView scanFrontImage;
    @BindView(R.id.scanBackImage)
    CardImageView scanBackImage;

    App app;

    @Override
    public CardCheckPresenter getPresenter() {
        return new CardCheckPresenter();
    }

    @Override
    public int getResId() {
        return R.layout.activity_card_check;
    }

    @Override
    public void init() {
        title.setText("身份认证");
        app = (App) getApplication();
    }

    String frontPath;

    String backPath;

    @OnClick({R.id.scanFront,R.id.scanBack,R.id.commit,R.id.returns,R.id.scanFrontImage,R.id.scanBackImage})
    public void click(View view){
        switch (view.getId()){
            case R.id.scanFrontImage:
                openActivityForResult(CardUpActivity.class,0);
                break;
            case R.id.scanBackImage:
                openActivityForResult(CardUpActivity.class,1);
                break;
            case R.id.scanFront:
                openActivityForResult(CardUpActivity.class,0);
                break;
            case R.id.scanBack:
                openActivityForResult(CardUpActivity.class,1);
                break;
            case R.id.commit:
                commit();
                break;
            case R.id.returns:
                finish();
                break;
        }
    }

    private void commit(){
        if(frontPath == null){
            Helps.toast(this,"身份证正面图片不存在");
            return;
        }
        if(backPath == null){
            Helps.toast(this,"身份证反面图片不存在");
            return;
        }
        p.upImageMore(NetUrl.DNS + NetUrl.upImageMore,frontPath,backPath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 3){
            String fileName = data.getStringExtra("fileName");
            String path = data.getStringExtra("path");
            if(requestCode == 0){
            //    p.intoLocalRetangle(scanFrontImage,path,scanFront.getWidth(),scanFront.getHeight());

                scanFrontImage.setBitmap(path,scanFront.getWidth(),scanFront.getHeight());
                frontPath = path;

                /*LinearLayout.LayoutParams scanParams = (LinearLayout.LayoutParams) scanFront.getLayoutParams();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scanFrontImage.getLayoutParams();
                params.width = scanParams.width;
                params.height = scanParams.height;
                params.leftMargin = scanParams.leftMargin;
                params.rightMargin = scanParams.rightMargin;
                params.topMargin = scanParams.topMargin;
                params.bottomMargin = scanParams.bottomMargin;
                scanFrontImage.setLayoutParams(params);*/
                scanFrontImage.setVisibility(View.VISIBLE);
            //    scanFront.setVisibility(View.GONE);
            }else{
            //    p.intoLocalRetangle(scanBackImage,path,scanBack.getWidth(),scanBack.getHeight());
                scanBackImage.setBitmap(path,scanBack.getWidth(),scanBack.getHeight());
                backPath = path;

                /*LinearLayout.LayoutParams scanParams = (LinearLayout.LayoutParams) scanBack.getLayoutParams();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scanBackImage.getLayoutParams();
                params.width = scanBack.getWidth();
                params.height = scanBack.getHeight();
                params.leftMargin = scanParams.leftMargin;
                params.rightMargin = scanParams.rightMargin;
                params.topMargin = scanParams.topMargin;
                params.bottomMargin = scanParams.bottomMargin;
                scanBackImage.setLayoutParams(params);*/

                scanBackImage.setVisibility(View.VISIBLE);
            //    scanBack.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onError() {
        Helps.toast(this,"提交审核失败!");
    }

    @Override
    public void onUpImageMoreSuccess(ImageMoreUpBean data) {
        if(data.getInfoCode() == 1){
            String[] imgs = data.getData();
            if(imgs.length > 0){
                try{
                    p.commit(NetUrl.DNS + NetUrl.CardUp,app.id,imgs[0],imgs[1]);
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }else{
            Helps.toast(this,data.getMessage());
        }
    }

    @Override
    public void onCommitSuccess(String infoCode, String message) {
        Helps.toast(this,message);
    }
}
