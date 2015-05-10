package com.soften.ltp.TransposeJson;

/**
 * 对象属性评价 类
 * Created by mysgq1 on 15/3/20.
 */
public class ObjectPropertyEvaluate {
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    private String uuid;
    private String object;
    private String property;
    private String evaluate;
}
