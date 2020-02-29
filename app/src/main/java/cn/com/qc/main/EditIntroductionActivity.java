package cn.com.qc.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.qc.R;
import cn.com.qc.adapter.PopWindowsAdapter;
import cn.com.qc.bean.CityBean;
import cn.com.qc.bean.UserIntroInfo;
import cn.com.qc.help.Helps;
import cn.com.qc.help.NetUrl;
import cn.com.qc.view.GlideImageLoader;
import cn.com.qc.yinter.EditIntroDataListener;
import cn.com.qc.ypresenter.EditIntroductionPresenter;

/**
 * Created by lenovo on 2017/11/15.
 */

public class EditIntroductionActivity extends YBaseActivity<EditIntroductionPresenter, EditIntroductionActivity> implements EditIntroDataListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.outYear)
    EditText outYear;
    @BindView(R.id.maxEdu)
    EditText maxEdu;
    @BindView(R.id.workTime)
    EditText workTime;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.phoneText)
    TextView phoneText;
    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.city)
    TextView city;

    @BindView(R.id.school)
    EditText school;
    @BindView(R.id.major)
    EditText major;
    @BindView(R.id.beginDate)
    EditText beginDate;
    @BindView(R.id.graduateDate)
    EditText graduateDate;
    @BindView(R.id.maxEdu1)
    EditText maxEdu1;

    @BindView(R.id.company)
    EditText company;
    @BindView(R.id.position)
    EditText position;
    @BindView(R.id.enterDate)
    EditText enterDate;
    @BindView(R.id.endDate)
    EditText endDate;
    @BindView(R.id.workContent)
    EditText workContent;

    @BindView(R.id.wantPos)
    EditText wantPos;
    @BindView(R.id.workType)
    EditText workType;
    @BindView(R.id.wantCity)
    EditText wantCity;
    @BindView(R.id.wantMoney)
    EditText wantMoney;

    @BindView(R.id.introduct)
    EditText introduct;

    @Override
    public EditIntroductionPresenter getPresenter() {
        return new EditIntroductionPresenter();
    }

    @Override
    public int getResId() {
        return R.layout.activity_edit_self_info;
    }

    String studentId;

    @Override
    public void init() {
        title.setText("编辑简历");
        save.setVisibility(View.VISIBLE);
        initImagePicker();
        Bundle bundle = getIntent().getExtras();
        String phone = bundle.getString("phone");
        studentId = bundle.getString("studentId");
        UserIntroInfo userIntroInfo = (UserIntroInfo) bundle.getSerializable("object");
    //    studentId = "730cc0f9-20e4-46d3-940b-08840ba5f2dd";
        this.phoneText.setText(phone);
        initData(userIntroInfo);
    }

    private void initData(UserIntroInfo userIntroInfo){
        int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,58,getResources().getDisplayMetrics());
        p.intoCircle(this,userIntroInfo.getHeadImg(),icon, R.mipmap.head_img,w,w);
        setText(name,userIntroInfo.getName());
        setText(sex,userIntroInfo.getSex());
        setText(outYear,formatText(userIntroInfo.getBirthDate()));//
        setText(workTime,userIntroInfo.getOutYear());//
        setText(phoneText,userIntroInfo.getConnectPhone());
        setText(email,userIntroInfo.getConnectEmail());
        setText(city,userIntroInfo.getCity());
        setText(school,userIntroInfo.getSchoolName());
        setText(major,userIntroInfo.getMajor());
        setText(beginDate,formatText(userIntroInfo.getEduBeginTime()));//
        setText(graduateDate,formatText(userIntroInfo.getEduEndTime()));//
        setText(maxEdu1,userIntroInfo.getEdu());
        setText(company,userIntroInfo.getCompany());
        setText(position,userIntroInfo.getPosition());
        setText(enterDate,formatText(userIntroInfo.getBeginTime()));//
        setText(endDate,formatText(userIntroInfo.getEndTime()));//
        setText(workContent,userIntroInfo.getJobContent());
        setText(wantPos,userIntroInfo.getExceptPosition());
        setText(wantMoney,userIntroInfo.getExceptSalary());
        setText(introduct,userIntroInfo.getMyEvaluate());
        //studentEducationId,workId,hopeJobId
        studentEducationId = userIntroInfo.getStudentId();
        workId = userIntroInfo.getWorkId();
        hopeJobId = userIntroInfo.getHopeJobId();
    }

    String studentEducationId,workId,hopeJobId;

    private void setText(TextView editText,String s){
        if(s == null){
            return;
        }
        editText.setText(s);
    }

    private void setText(EditText editText,String s){
        if(s == null){
            return;
        }
        editText.setText(s);
    }

    private String formatText(String s){
        if(s == null){
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        s = format.format(new Date(Long.parseLong(s)));
        return s;
    }

    @OnClick({R.id.returns, R.id.save, R.id.iconLinear, R.id.city})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.returns:
                finish();
                break;
            case R.id.save:
                save();
                break;
            case R.id.iconLinear:
                openActivityForResult(ImageGridActivity.class, 100);
                break;
            case R.id.city:
                if (isLoadCompleted) {
                    showCity();
                } else {
                    p.getAllCity(NetUrl.AllCity);
                }
                break;
        }
    }

    private void save() {
        String name = getText(this.name);
        String sex = getText(this.sex);
        if(!sex.equals("")){
            if(sex.equals("男")){
                sex = "1";
            }else if(sex.equals("女")){
                sex = "2";
            }else{
                Helps.toast(this,"性别不合要求");
            }
        }
        String outYear = getText(this.outYear);
        if(!outYear.equals("")){
            if(checkTimeForm(outYear)){
                outYear = "" + format(outYear);
            }else{
                Helps.toast(this,"输入日期不符合格式要求");
                return;
            }
        }
        //    String maxEdu = getText(this.maxEdu);
        String workTime = getText(this.workTime);
        String phone = getText(this.phoneText);
        String email = getText(this.email);

        String city = getText(this.city);

        String school = getText(this.school);//major graduateDate maxEdu1
        String major = getText(this.major);
        String beginDate = getText(this.beginDate);
        if(!beginDate.equals("")){
            if(checkTimeForm(beginDate)){
                beginDate = "" + format(beginDate);
            }else{
                Helps.toast(this,"输入日期不符合格式要求");
                return;
            }
        }
        String graduateDate = getText(this.graduateDate);
        if(!graduateDate.equals("")){
            if(checkTimeForm(graduateDate)){
                graduateDate = "" + format(graduateDate);
            }else{
                Helps.toast(this,"输入日期不符合格式要求");
                return;
            }
        }
        String maxEdu1 = getText(this.maxEdu1);
        //company position enterDate endDate workContent
        String company = getText(this.company);
        String position = getText(this.position);
        String enterDate = getText(this.enterDate);
        if(!enterDate.equals("")){
            if(checkTimeForm(enterDate)){
                enterDate = "" + format(enterDate);
            }else{
                Helps.toast(this,"输入日期不符合格式要求");
                return;
            }
        }
        String endDate = getText(this.endDate);
        if(!endDate.equals("")){
            if(checkTimeForm(endDate)){
                endDate = "" + format(endDate);
            }else{
                Helps.toast(this,"输入日期不符合格式要求");
                return;
            }
        }
        String workContent = getText(this.workContent);
        //wantPos workType wantCity wantMoney
        String wantPos = getText(this.wantPos);
        String workType = getText(this.workType);
        String wantCity = getText(this.wantCity);
        String wantMoney = getText(this.wantMoney);
        //introduct
        String introduct = getText(this.introduct);

        p.save(NetUrl.DNS + NetUrl.UpdateInfo, name, sex, outYear, workTime, phone, email, city
                , school, major, beginDate, graduateDate, maxEdu1,
                company, position, enterDate, endDate, workContent,
                wantPos, workType, wantCity, wantMoney,
                introduct,
                studentId
                , path, fileName
                ,studentEducationId,workId,hopeJobId);
    }

    private String getText(EditText editText) {
        String content = editText.getText().toString().trim();
        return content;
    }

    private String getText(TextView editText) {
        String content = editText.getText().toString().trim();
        return content;
    }

    private boolean checkTimeForm(String time) {
        boolean b = time.matches("^[0-9]{4}\\.[0-9]{1,2}\\.[0-9]{1,2}$");
        return b;
    }

    private long format(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date;
        try {
            date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    boolean isLoadCompleted = false;
    List<String> citys = new ArrayList<>();

    private void showCity() {
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_city_item, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
        params.height = getResources().getDisplayMetrics().heightPixels * 1 / 2;
        listView.setLayoutParams(params);
        PopWindowsAdapter adapter = new PopWindowsAdapter(this, citys, R.layout.popwindow_text_layout1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                city.setText(citys.get(position));
            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        GlideImageLoader imageLoader = new GlideImageLoader();
        imagePicker.setImageLoader(imageLoader);   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setSaveRectangle(false); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
    }

    String path;

    String fileName;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                fileName = images.get(0).name;
                path = images.get(0).path;
                long size = images.get(0).size;
                int width = images.get(0).width;
                String mimeType = images.get(0).mimeType;
                long addTime = images.get(0).addTime;
                int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, getResources().getDisplayMetrics());
                p.intoLocalCicle(this, path, icon, R.mipmap.head_img, w);
            } else {
                Helps.toast(this, "没有数据");
            }
        }
    }

    @Override
    public void onCitySuccess(List<String> citys) {
        isLoadCompleted = true;
        this.citys.clear();
        if (citys != null) {
            this.citys.addAll(citys);
        }
        showCity();
    }

    @Override
    public void onCityError() {
        Helps.toast(this, "城市信息获取失败!");
    }

    @Override
    public void onUpImageError() {
        Helps.toast(this, "保存失败");
    }

    @Override
    public void updateIntroSuccess(String infoCode, String message) {
        Helps.toast(this, message);
    }

    @Override
    public void updateIntroError() {
        Helps.toast(this, "保存失败");
    }

}
