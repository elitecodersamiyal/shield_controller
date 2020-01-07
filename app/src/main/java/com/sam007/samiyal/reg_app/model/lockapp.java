package com.sam007.samiyal.reg_app.model;

public class lockapp {
    //private String appname,i;
    private String appname;

    lockapp() {}

    public lockapp(String appname) {
        this.appname = appname;
    }

    /*
        public lockapp(String i, String appname) {
            this.i = i;
            this.appname = appname;
        }

        public String getUid() {
            return i;
        }

        public void setUid(String i) {
            this.i = i;
        }
    */
    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }
}
