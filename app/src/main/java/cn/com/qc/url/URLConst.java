package cn.com.qc.url;

/**
 * Created by Lee on 2017/11/29.
 */

public class URLConst {
    private static String baseURL;

    public final static String BASEURL(String typeURL) {
        // 测试
        baseURL = "http://192.168.1.102:8092/PartTime/" + typeURL;
        // 正式
//        baseURL = "http://59.110.241.176/qicheng/mobile/" + typeURL;
        return baseURL;
    }

    public final static String IMGBASEURL(String typeURL) {
        // 图片外网地址前缀
        baseURL = "http://39.106.8.72:8080/qicheng/" + typeURL;
        return baseURL;
    }
}
