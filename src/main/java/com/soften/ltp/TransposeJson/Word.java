package com.soften.ltp.TransposeJson;

import java.util.List;

/**
 * Created by mysgq1 on 15/3/4.
 */
public class Word {


    private String relate;
    private Integer id;
    private String ne;
    private String parent;
    private String cont;
    private String pos;
    private List<Arg> arg ;

    public String getRelate() {
        return relate;
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNe() {
        return ne;
    }

    public void setNe(String ne) {
        this.ne = ne;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public List<Arg> getArg() {
        return arg;
    }

    public void setArg(List<Arg> arg) {
        this.arg = arg;
    }
}