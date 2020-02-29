package cn.com.qc.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/10/17.
 */

public class FindHotInfo {

    String hideMessage;

    String message;

    int infoCode;

    List<HotInfo> data;

    public String getHideMessage() {
        return hideMessage;
    }

    public void setHideMessage(String hideMessage) {
        this.hideMessage = hideMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(int infoCode) {
        this.infoCode = infoCode;
    }

    public List<HotInfo> getData() {
        return data;
    }

    public void setData(List<HotInfo> data) {
        this.data = data;
    }

    public class HotInfo{
        String title;
        String synopsis;
        String look;
        String imgUrl;
        String id;
        String context;
        List<CarouselImgs> carouselImgs;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getLook() {
            return look;
        }

        public void setLook(String look) {
            this.look = look;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public List<CarouselImgs> getCarouselImgs() {
            return carouselImgs;
        }

        public void setCarouselImgs(List<CarouselImgs> carouselImgs) {
            this.carouselImgs = carouselImgs;
        }
    }

    public class CarouselImgs{
        String id;
        String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
