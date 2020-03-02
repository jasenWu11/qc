package cn.com.qc.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/12/13.
 */

public class SearchCompanyBean {

    SearchD data;

    public SearchD getData() {
        return data;
    }

    public void setData(SearchD data) {
        this.data = data;
    }

    String infoCode;

    String message;

    public class SearchD {
        List<SearchData> list;
        String pageCount;
        String pageNumber;
        String pageSize;
        String total;

        public List<SearchData> getList() {
            return list;
        }

        public void setList(List<SearchData> list) {
            this.list = list;
        }

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public String getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(String pageNumber) {
            this.pageNumber = pageNumber;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public class SearchData {
        private String id;
        private String name;
        private String enterpriseType;//公司类型名称
        private Integer min;//最小
        private Integer max;//公司规模

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        private String logo;//公司标识 url

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnterpriseType() {
            return enterpriseType;
        }

        public void setEnterpriseType(String enterpriseType) {
            this.enterpriseType = enterpriseType;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }
    }

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
