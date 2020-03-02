package cn.com.qc.javabean;

public class MoreJob {
    private String job;
    private String date;
    private String location;
    private int money;
    private String size;

    public MoreJob(String job, String date, String location, int money, String size) {
        this.job = job;
        this.date = date;
        this.location = location;
        this.money = money;
        this.size = size;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
