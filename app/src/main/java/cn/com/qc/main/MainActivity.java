package cn.com.qc.main;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tandong.sa.tools.AssistTool;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.fragment.FindFragment;
import cn.com.qc.fragment.HomeFragment;
import cn.com.qc.fragment.NewsFragment;
import cn.com.qc.fragment.PersonFragment;
import cn.com.qc.fragment.RecruitFragment;
import cn.com.qc.help.Helps;
import cn.com.qc.url.URLConst;
import cn.com.qc.utils.Tools;

public class MainActivity extends BaseActivity {
    @BindView(R.id.home)
    RadioButton home;
    @BindView(R.id.recruit)
    RadioButton recruit;
    @BindView(R.id.find)
    RadioButton find;
    @BindView(R.id.news)
    RadioButton news;
    @BindView(R.id.newscount)
    TextView newscount;
    @BindView(R.id.person)
    RadioButton person;
    @BindView(R.id.tab)
    RadioGroup tab;
    private Fragment[] fragments;
    private int mIndex;
    private static final String typeURL = "mobile/getBasePath.do";
    private AssistTool assistTool;
    // 双击退出应用
    private static boolean isExit = false;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tools.initSystemBarTint(this);
        ButterKnife.bind(this);
        assistTool = new AssistTool(this);
        HomeFragment homeFragment = new HomeFragment();
        RecruitFragment recruitFragment = new RecruitFragment();
        FindFragment findFragment = new FindFragment();
        NewsFragment newsFragment = new NewsFragment();
        PersonFragment personFragment = new PersonFragment();
        fragments = new Fragment[]{homeFragment, recruitFragment, findFragment, newsFragment, personFragment};
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentcontent, homeFragment).commit();
        setIndexSelected(0);
        home.setChecked(true);
        OkGo.<String>post(URLConst.IMGBASEURL(typeURL))
                .tag(this)
                .headers("device", "1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());
                            String imgbasepath = jsonObject.getString("basePath");
                            System.out.println("图片外网地址前缀：" + imgbasepath);
                            assistTool.savePreferenceString("imgbasepath", imgbasepath);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(fragments[mIndex]);
        if (!fragments[index].isAdded()) {
            ft.add(R.id.fragmentcontent, fragments[index]).show(fragments[index]);
        } else {
            ft.show(fragments[index]);
        }
        ft.commit();
        mIndex = index;
    }

    @OnClick({R.id.home, R.id.recruit, R.id.find, R.id.news, R.id.person})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home:
                setIndexSelected(0);
                news.setChecked(false);
                break;
            case R.id.recruit:
                setIndexSelected(1);
                news.setChecked(false);
                break;
            case R.id.find:
                setIndexSelected(2);
                news.setChecked(false);
                break;
            case R.id.news:
                setIndexSelected(3);
                home.setChecked(false);
                recruit.setChecked(false);
                find.setChecked(false);
                person.setChecked(false);
                newscount.setVisibility(View.INVISIBLE);
                break;
            case R.id.person:
                setIndexSelected(4);
                news.setChecked(false);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Tools.toast(this, "再按一次将退出应用");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
//            finish();
            System.exit(0);
        }
    }

}
