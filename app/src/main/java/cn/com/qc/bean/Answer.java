package cn.com.qc.bean;

public class Answer {
    //答案选项
    private String color;
    //答案主体
    private String answer_content;
    //答案是否被解答
    private int ans_state;
    //答案分数
    private Integer grade;
    public int getAns_state() {
        return ans_state;
    }
    public void setAns_state(int ans_state) {
        this.ans_state = ans_state;
    }
    public String getAnswerColor() {
        return color;
    }
    public void setAnswerColor(String answerId) {
        this.color = answerId;
    }
    public String getAnswer_content() {
        return answer_content;
    }
    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }
    public Integer getGrade() {
        return grade;
    }
    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
