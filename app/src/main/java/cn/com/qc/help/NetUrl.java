package cn.com.qc.help;

/**
 * Created by lenovo on 2017/9/30.
 */

public class NetUrl {

    //获取图片头部
    public static final String ImgHeader = "http://192.168.163.195:8080/qicheng/mobile/getBasePath.do";

    public static final String DNS = "http://192.168.1.102:8092/PartTime";
    //<-- 首页URL -->
    //首页数据
    public  static final String HomeData = "home/getHomeData.do";
    //首页搜索框
    public static final String HomeSearch = "home/homeTopSearch.do";
    //首页中部搜索
    public static final String HomeCondition = "home/homeBodySearch.do";

    public static final String FindHotInfo = "home/getHomeIndustry.do";

    public static final String GetInfo = "student/getStudentResume.do";

    //获取问卷题目

    public static final String Getquestion = "/question/list";

    //完善个人简历
    public static final String UpdateInfo = "student/updateResume.do";
    //图片上传
    public static final String upImage = "img/fildUpload.do";
    //多张图片上传
    public static final String upImageMore = "img/fildUploads.do";

    //公司详情
    public static final String CompanyDetails = "mobile/getEnterpriseById.do";

    //获取招聘职位列表
    public static final String Joblist = "/job/list";

    //获取值为详细信息
    public static final String Jobinfo = "/job/info";

    //用户应聘职位
    public static final String applyJob = "/applyJob/add";

    //查看已申请的职位
    public static final String proposerlist= "/applyJob/user/list";

    //发送验证码
    public static final String SendCode = "student/sendMassage.do";
    //注册
    public static final String Register = "/user/add?UserName";
    //忘记密码
    public static final String Forget = "student/forgetPwd.do";
    //登录
    public static final String Login = "/user/login";
    //退出
    public static final String Logout = "/user/loginout";

    //身份证上传
    public static final String CardUp = "student/saveCardId.do";

    //发现
    public static final String Find = "mobile/findHot.do";

    //获取所有城市
    public static final String AllCity = "http://restapi.amap.com/v3/config/district";
}
