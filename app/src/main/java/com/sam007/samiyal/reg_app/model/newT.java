package com.sam007.samiyal.reg_app.model;

public class newT {
    String val,val2;

    public newT() {}

    public newT(newT n) {
        this.val = n.val;
    }

    public newT(String val, String val2) {
        this.val = val;
        this.val2 = val2;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }
}
