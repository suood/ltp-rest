package com.soften.ltp.TransposeJson;

import java.util.List;

/**
 * Created by mysgq1 on 15/3/4.
 */
public class Para{


    private Integer id;
    private List<Sent> sent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Sent> getSent() {
        return sent;
    }

    public void setSent(List<Sent> sent) {
        this.sent = sent;
    }
}