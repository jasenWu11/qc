package cn.com.qc.leeactivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.com.qc.R;
import cn.com.qc.bean.Answer;
import cn.com.qc.bean.Page;
import cn.com.qc.bean.Quesition;

public class Questionnaire extends Activity {
    private LinearLayout test_layout;
    private Page the_page;
    //答案列表
    private ArrayList<Answer> the_answer_list;
    //问题列表
    private ArrayList<Quesition> the_quesition_list;
    //问题所在的View
    private View que_view;
    //答案所在的View
    private View ans_view;
    private LayoutInflater xInflater;
    private Page page;
    //下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
    //存每个问题下的imageview
    private ArrayList<ArrayList<ImageView>> imglist=new ArrayList<ArrayList<ImageView>>();
    //存每个答案的imageview
    private ArrayList<ImageView> imglist2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire);
        xInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //假数据
        initDate();
        //提交按钮
        Button button=(Button)findViewById(R.id.submit);
        button.setOnClickListener(new submitOnClickListener(page));
    }
    private void initDate() {
        String result = "{\n" +
                "    \"status\": 0,\n" +
                "    \"msg\": \"OK\",\n" +
                "    \"data\": {\n" +
                "        \"records\": [\n" +
                "            {\n" +
                "                \"createTime\": \"2020-02-15 00:52:29\",\n" +
                "                \"id\": 29,\n" +
                "                \"item\": \"[{\\\"value\\\":\\\"q\\\",\\\"grade\\\":\\\"2\\\"}]\",\n" +
                "                \"grade\": null,\n" +
                "                \"multiple\": 0,\n" +
                "                \"title\": \"点击选项可展开选项\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"createTime\": \"2020-02-14 23:01:18\",\n" +
                "                \"id\": 26,\n" +
                "                \"item\": \"[{\\\"value\\\":\\\"aa\\\",\\\"grade\\\":\\\"2\\\"}]\",\n" +
                "                \"grade\": null,\n" +
                "                \"multiple\": 0,\n" +
                "                \"title\": \"修改 时新增\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"createTime\": \"2020-02-14 19:46:44\",\n" +
                "                \"id\": 25,\n" +
                "                \"item\": \"[{\\\"value\\\":\\\"qweqw\\\",\\\"grade\\\":\\\"2\\\"}]\",\n" +
                "                \"grade\": null,\n" +
                "                \"multiple\": 0,\n" +
                "                \"title\": \"woshiwenti 修改\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"createTime\": \"2020-02-14 19:44:33\",\n" +
                "                \"id\": 24,\n" +
                "                \"item\": \"[{\\\"value\\\":\\\"常用于主动操作后的反馈提示。与 Notification 的区别是后者更多用于系统级通知的被动提醒。常用于主动操作后的反馈提示。与 Notification 的区别是后者更多用于系统级通知的被动提醒。常用于主动操作后的反馈提示。与 Notification 的区别是后者更多用于系统级通知的被动提醒。常用于主动操作后的反馈提示。与 Notification 的区别是后者更多用于系统级通知的被动提醒。\\\",\\\"grade\\\":\\\"12\\\"}]\",\n" +
                "                \"grade\": null,\n" +
                "                \"multiple\": 0,\n" +
                "                \"title\": \"常用于主动操作后的反馈提示。223\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"createTime\": \"2020-02-14 19:31:37\",\n" +
                "                \"id\": 20,\n" +
                "                \"item\": \"[{\\\"value\\\":\\\"不要你觉得\\\",\\\"grade\\\":\\\"2\\\"},{\\\"value\\\":\\\"我要我觉得\\\",\\\"grade\\\":\\\"3\\\"},{\\\"value\\\":\\\"武汉加油\\\",\\\"grade\\\":\\\"9999\\\"}]\",\n" +
                "                \"grade\": null,\n" +
                "                \"multiple\": 0,\n" +
                "                \"title\": \"测试新增\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"createTime\": \"2020-02-14 18:51:04\",\n" +
                "                \"id\": 2,\n" +
                "                \"item\": \"[{\\\"value\\\":\\\"A科幻\\\",\\\"grade\\\":\\\"1\\\"},{\\\"value\\\":\\\"B剧情\\\",\\\"grade\\\":\\\"2\\\"},{\\\"value\\\":\\\"C动作\\\",\\\"grade\\\":\\\"3\\\"}]\",\n" +
                "                \"grade\": null,\n" +
                "                \"multiple\": 1,\n" +
                "                \"title\": \"你喜欢什么类型的电影 bianji 33\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"createTime\": \"2020-02-14 18:51:01\",\n" +
                "                \"id\": 1,\n" +
                "                \"item\": \"[{\\\"value\\\":\\\"A 徒步 tubu\\\",\\\"grade\\\":\\\"2\\\"},{\\\"value\\\":\\\"B:一览众山小 攀登\\\",\\\"grade\\\":\\\"3\\\"}]\",\n" +
                "                \"grade\": null,\n" +
                "                \"multiple\": 0,\n" +
                "                \"title\": \"你喜欢什么户外活动d d\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"total\": 7,\n" +
                "        \"size\": 10,\n" +
                "        \"current\": 1,\n" +
                "        \"searchCount\": true,\n" +
                "        \"pages\": 1\n" +
                "    },\n" +
                "    \"fieldErrors\": {}\n" +
                "}";
        //假数据
        // TODO Auto-generated method stub

        ArrayList<Quesition> quesitions=new ArrayList<Quesition>();

        try {
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONObject  data = jsonObject.getJSONObject("data");
            JSONArray records = data.getJSONArray("records");
            for (int i = 0;i < records.size();i++){
                JSONObject record = records.getJSONObject(i);
                Integer id = record.getInteger("id");
                String title = record.getString("title");
                Integer multiple = record.getInteger("multiple");
                String item = record.getString("item");
                JSONArray answers_list = JSON.parseArray(item);
                ArrayList<Answer> answers=new ArrayList<Answer>();
                for (int j = 0;j < answers_list.size();j++){
                    JSONObject answer = answers_list.getJSONObject(j);
                    String value = answer.getString("value");
                    Integer grade = Integer.parseInt(answer.getString("grade"));
                    Answer ans=new Answer();
                    ans.setAnswerId(j);
                    ans.setAnswer_content(value);
                    ans.setAns_state(0);
                    ans.setGrade(grade);
                    answers.add(ans);
                }
                Quesition quesition=new Quesition();
                quesition.setQuesitionId(id);
                quesition.setType(multiple);
                quesition.setContent(i+1+"、"+title+"：");
                quesition.setAnswers(answers);
                quesition.setQue_state(0);
                quesitions.add(quesition);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        page=new Page();
        page.setPageId("000");
        page.setStatus("0");
        page.setTitle("性格测试问卷");
        page.setQuesitions(quesitions);
        //加载布局
        initView(page);
    }
    private void initView(Page page) {
        // TODO Auto-generated method stub
        //这是要把问题的动态布局加入的布局
        test_layout=(LinearLayout)findViewById(R.id.lly_test);
        TextView page_txt=(TextView)findViewById(R.id.txt_title);
        page_txt.setText(page.getTitle());
        //获得问题即第二层的数据
        the_quesition_list=page.getQuesitions();
        //根据第二层问题的多少，来动态加载布局
        for(int i=0;i<the_quesition_list.size();i++){
            que_view=xInflater.inflate(R.layout.quesition_layout, null);
            TextView txt_que=(TextView)que_view.findViewById(R.id.txt_question_item);
            //这是第三层布局要加入的地方
            LinearLayout add_layout=(LinearLayout)que_view.findViewById(R.id.lly_answer);
            //判断单选-多选来实现后面是*号还是*多选，
            if(the_quesition_list.get(i).getType().equals(1)){
                set(txt_que,the_quesition_list.get(i).getContent(),1);
            }else{
                set(txt_que,the_quesition_list.get(i).getContent(),0);
            }
            //获得答案即第三层数据
            the_answer_list=the_quesition_list.get(i).getAnswers();
            imglist2=new ArrayList<ImageView>();
            for(int j=0;j<the_answer_list.size();j++){
                ans_view=xInflater.inflate(R.layout.answer_layout, null);
                TextView txt_ans=(TextView)ans_view.findViewById(R.id.txt_answer_item);
                ImageView image=(ImageView)ans_view.findViewById(R.id.image);
                View line_view=ans_view.findViewById(R.id.vw_line);
                if(j==the_answer_list.size()-1){
                    //最后一条答案下面不要线是指布局的问题
                    line_view.setVisibility(View.GONE);
                }
                //判断单选多选加载不同选项图片
                if(the_quesition_list.get(i).getType().equals(1)){
                    image.setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_false));
                }else{
                    image.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
                }
                Log.e("---", "------"+image);
                imglist2.add(image);
                txt_ans.setText(the_answer_list.get(j).getAnswer_content());
                LinearLayout lly_answer_size=(LinearLayout)ans_view.findViewById(R.id.lly_answer_size);
                lly_answer_size.setOnClickListener(new answerItemOnClickListener(i,j,the_answer_list,txt_ans));
                add_layout.addView(ans_view);
            }
			/*for(int r=0; r<imglist2.size();r++){
				Log.e("---", "imglist2--------"+imglist2.get(r));
			}*/

            imglist.add(imglist2);

            test_layout.addView(que_view);
        }
		/*for(int q=0;q<imglist.size();q++){
			for(int w=0;w<imglist.get(q).size();w++){
				Log.e("---", "共有------"+imglist.get(q).get(w));
			}
		}*/

    }
    private void set(TextView tv_test, String content, int type) {
        //为了加载问题后面的* 和*多选
        // TODO Auto-generated method stub
        String w;
        if(type==1){
            w = content+"*[多选题]";
        }else{
            w = content+"*";
        }

        int start = content.length();
        int end = w.length();
        Spannable word = new SpannableString(w);
        word.setSpan(new AbsoluteSizeSpan(25), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv_test.setText(word);
    }
    class answerItemOnClickListener implements View.OnClickListener {
        private int i;
        private int j;
        private TextView txt;
        private ArrayList<Answer> the_answer_lists;
        public answerItemOnClickListener(int i, int j, ArrayList<Answer> the_answer_list, TextView text){
            this.i=i;
            this.j=j;
            this.the_answer_lists=the_answer_list;
            this.txt=text;

        }
        //实现点击选项后改变选中状态以及对应图片
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断当前问题是单选还是多选
			/*Log.e("------", "选择了-----第"+i+"题");
			for(int q=0;q<imglist.size();q++){
				for(int w=0;w<imglist.get(q).size();w++){
//					Log.e("---", "共有------"+imglist.get(q).get(w));
				}
			}
			Log.e("----", "点击了---"+imglist.get(i).get(j));*/

            if(the_quesition_list.get(i).getType().equals(1)){
                //多选
                if(the_answer_lists.get(j).getAns_state()==0){
                    //如果未被选中
                    txt.setTextColor(Color.parseColor("#1296db"));
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_true));
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                }else{
                    txt.setTextColor(Color.parseColor("#595757"));
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_false));
                    the_answer_lists.get(j).setAns_state(0);
                    the_quesition_list.get(i).setQue_state(1);
                }
            }else{
                //单选

                for(int z=0;z<the_answer_lists.size();z++){
                    the_answer_lists.get(z).setAns_state(0);
                    imglist.get(i).get(z).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
                }
                if(the_answer_lists.get(j).getAns_state()==0){
                    //如果当前未被选中
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_true));
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                }else{
                    //如果当前已被选中
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                }

            }
            //判断当前选项是否选中
        }

    }
    class submitOnClickListener implements View.OnClickListener {
        private Page page;
        public submitOnClickListener(Page page){
            this.page=page;
        }
        @Override
        public void onClick(View arg0) {
            Integer red = 0;
            Integer blue = 0;
            Integer yellow = 0;
            Integer green = 0;
            // TODO Auto-generated method stub
            //判断是否答完题
            boolean isState=true;
            //最终要的json数组
            JSONArray jsonArray = new JSONArray();
            //点击提交的时候，先判断状态，如果有未答完的就提示，如果没有再把每条答案提交（包含问卷ID 问题ID 及答案ID）
            //注：不用管是否是一个问题的答案，就以答案的个数为准来提交上述格式的数据
            for(int i=0;i<the_quesition_list.size();i++){
                the_answer_list=the_quesition_list.get(i).getAnswers();
                //判断是否有题没答完
                if(the_quesition_list.get(i).getQue_state()==0){
                    Toast.makeText(getApplicationContext(), "您第"+(i+1)+"题没有答完", Toast.LENGTH_LONG).show();
                    jsonArray=null;
                    isState=false;
                    break;
                }else{
                    for(int j=0;j<the_answer_list.size();j++){
                        if(the_answer_list.get(j).getAns_state()==1){
//                            JSONObject json = new JSONObject();
//                            try {
//                                json.put("psychologicalId", page.getPageId());
//                                json.put("questionId", the_quesition_list.get(i).getQuesitionId());
//                                json.put("optionId", the_answer_list.get(j).getAnswerId());
//                                jsonArray.put(json);
//                            } catch (JSONException e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
                            Integer choseid = the_answer_list.get(j).getAnswerId();
                            Integer grade = the_answer_list.get(j).getGrade();
                            switch(choseid){
                                case 0 :
                                    red = red+grade;
                                    break; //可选
                                case 1 :
                                    blue=blue+grade;
                                    break; //可选
                                case 2 :
                                    yellow=yellow+grade;
                                    break; //可选
                                case 3 :
                                    green=green+grade;
                                    break; //可选
                                default : //可选
                            }
                        }
                    }
                }
            }
            JSONArray color_list = new JSONArray();
            JSONObject json_red = new JSONObject();
            JSONObject json_blue = new JSONObject();
            JSONObject json_yellow = new JSONObject();
            JSONObject json_green = new JSONObject();
            try {
                json_red.put("color", "red");
                json_red.put("grade", red);
                json_blue.put("color", "blue");
                json_blue.put("grade", blue);
                json_yellow.put("color", "yellow");
                json_yellow.put("grade", yellow);
                json_green.put("color", "green");
                json_green.put("grade", green);
                color_list.add(json_red);
                color_list.add(json_blue);
                color_list.add(json_yellow);
                color_list.add(json_green);
                System.out.println("颜色列表"+color_list);
                String jsonArraySort = jsonArraySort(JSON.toJSONString(color_list));
                System.out.println("排序后："+jsonArraySort);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * 按照JSONArray中的对象的某个字段进行排序(采用fastJson)
     *
     * @param jsonArrStr
     *            json数组字符串
     *
     */
    public static String jsonArraySort(String jsonArrStr) {
        JSONArray jsonArr = JSON.parseArray(jsonArrStr);
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArr.size(); i++) {
            jsonValues.add(jsonArr.getJSONObject(i));
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            // You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "grade";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();
                try {
                    // 这里是a、b需要处理的业务，需要根据你的规则进行修改。
                    valA = a.getString(KEY_NAME);
                    valB = b.getString(KEY_NAME);
                } catch (JSONException e) {
                    // do something
                }
                return -valA.compareTo(valB);
                // if you want to change the sort order, simply use the following:
                // return -valA.compareTo(valB);
            }
        });
        for (int i = 0; i < jsonArr.size(); i++) {
            sortedJsonArray.add(jsonValues.get(i));
        }
        return sortedJsonArray.toString();
    }
}