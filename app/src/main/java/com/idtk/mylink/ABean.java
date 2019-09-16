package com.idtk.mylink;

import java.io.Serializable;
import java.util.List;

public class ABean implements Serializable{

    private String name;
    private List<BBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BBean> getList() {
        return list;
    }

    public void setList(List<BBean> list) {
        this.list = list;
    }

    public static class BBean{
        private String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
