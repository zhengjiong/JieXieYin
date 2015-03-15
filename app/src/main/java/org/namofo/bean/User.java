package org.namofo.bean;

import com.google.gson.Gson;

/**
 * create by zhengjiong
 * Date: 2015-03-08
 * Time: 21:51
 */
public class User {
    /**
     *
     {
     "uid": "735",
     "maxSignDays": "1",
     "continueDays": "1",
         "userinfo": {
         "xuexin": "A",
         "tizhong": "",
         "shengao": "",
         "bingshi": "",
         "zhengzhuang": "",
         "sex": "",
         "age": "",
         "xueli": "",
         "aihao": "",
         "techang": "",
         "yy": "",
         "qq": "12345645"
         }
     }
     */
    private String uid;
    private String maxSignDays;//最大簽到天數
    private String continueDays;//當前簽到天數
    private UserInfo userinfo = new UserInfo();

    public static class UserInfo{
        private String username;            //用戶賬號
        private String xuexin;              //血型
        private String tizhong;             //體重
        private String shengao;             //身高
        private String bingshi;             //病史
        private String zhengzhuang;         //症狀
        private String sex;                 //性別
        private String age;                 //年齡
        private String xueli;               //學歷
        private String aihao;               //愛好
        private String techang;             //特長
        private String yy;                  //yy號碼
        private String qq;                  //qq號碼

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getXuexin() {
            return xuexin;
        }

        public void setXuexin(String xuexin) {
            this.xuexin = xuexin;
        }

        public String getTizhong() {
            return tizhong;
        }

        public void setTizhong(String tizhong) {
            this.tizhong = tizhong;
        }

        public String getShengao() {
            return shengao;
        }

        public void setShengao(String shengao) {
            this.shengao = shengao;
        }

        public String getBingshi() {
            return bingshi;
        }

        public void setBingshi(String bingshi) {
            this.bingshi = bingshi;
        }

        public String getZhengzhuang() {
            return zhengzhuang;
        }

        public void setZhengzhuang(String zhengzhuang) {
            this.zhengzhuang = zhengzhuang;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getXueli() {
            return xueli;
        }

        public void setXueli(String xueli) {
            this.xueli = xueli;
        }

        public String getAihao() {
            return aihao;
        }

        public void setAihao(String aihao) {
            this.aihao = aihao;
        }

        public String getTechang() {
            return techang;
        }

        public void setTechang(String techang) {
            this.techang = techang;
        }

        public String getYy() {
            return yy;
        }

        public void setYy(String yy) {
            this.yy = yy;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMaxSignDays() {
        return maxSignDays;
    }

    public void setMaxSignDays(String maxSignDays) {
        this.maxSignDays = maxSignDays;
    }

    public String getContinueDays() {
        return continueDays;
    }

    public void setContinueDays(String continueDays) {
        this.continueDays = continueDays;
    }

    public UserInfo getUserInfo() {
        return userinfo;
    }

    public void setUserInfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public static User json2Obj(String json) {
        return new Gson().fromJson(json, User.class);
    }
}
