package cn.com.qc.javabean;

public class ProposerInfo {
    private String img;
    private String name;
    private String phone;
    private String score;
    private String time;

    public ProposerInfo(String name, String phone, String score, String time) {
        this.name = name;
        this.phone = phone;
        this.score = score;
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
