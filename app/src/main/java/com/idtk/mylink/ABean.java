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

    public static class BBean implements Serializable{
        private CBean c;

        public CBean getC() {
            return c;
        }

        public void setC(CBean c) {
            this.c = c;
        }

        public static class CBean implements Serializable{
            private int number;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }


        }
    }
}
