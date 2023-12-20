package com.sm.sdk.myapplication;

import java.util.ArrayList;

public class Model {
    String result_code;
    ArrayList<result_data> result_data;
    String result_status;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public ArrayList<Model.result_data> getResult_data() {
        return result_data;
    }

    public void setResult_data(ArrayList<Model.result_data> result_data) {
        this.result_data = result_data;
    }

    public String getResult_status() {
        return result_status;
    }

    public void setResult_status(String result_status) {
        this.result_status = result_status;
    }

    public class result_data{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

