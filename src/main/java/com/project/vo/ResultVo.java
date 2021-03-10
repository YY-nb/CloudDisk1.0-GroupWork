package com.project.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于保存后端传给前端的一些信息   {"error":xxx , "message":xxx}
 */
public class ResultVo {

    private String message;
    private String error;
    private Map<String,Object> data=new HashMap<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
