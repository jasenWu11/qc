package cn.com.qc.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/12/19.
 */

public class CityBean {

    List<District> districts;

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public class Province{
        String name;
        String level;
        List<City> districts;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<City> getDistricts() {
            return districts;
        }

        public void setDistricts(List<City> districts) {
            this.districts = districts;
        }
    }

    public class City{
        String name;
        String level;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }

    public class District{
        List<Province> districts;

        public List<Province> getDistricts() {
            return districts;
        }

        public void setDistricts(List<Province> districts) {
            this.districts = districts;
        }
    }
}
