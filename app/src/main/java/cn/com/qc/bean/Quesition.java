package cn.com.qc.bean;

import java.util.ArrayList;

public class Quesition {
    //题目id
    private Integer quesitionId;
    //单选多选标识
    private Integer type;
    //题目
    private String content;
    //选项
    private ArrayList<Answer> answers;
    //是否解答
    private int que_state;


    public int getQue_state() {
        return que_state;
    }
    public void setQue_state(int que_state) {
        this.que_state = que_state;
    }

    public Integer getQuesitionId() {
        return quesitionId;
    }
    public void setQuesitionId(Integer quesitionId) {
        this.quesitionId = quesitionId;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public ArrayList<Answer> getAnswers() {
        return answers;
    }
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

}
