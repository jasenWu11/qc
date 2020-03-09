package cn.com.qc.javabean;

public class IntroInfo {
    private Integer id;
    private String fileName;
    private String fileId;
    private String userName;
    private String createTime;
    private Integer userId;
    private Integer index;
    private String paths;
    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public Integer getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public String getUserName() {
        return userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
